package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.shadowsocks.constant.State;
import com.github.shadowsocks.utils.SS_SDK;
import com.j256.ormlite.stmt.query.In;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.RegisterModel;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.UserInfoModel;
import com.wwws.wwwsvpn.myapplication.model.VerifyModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.utils.Unique;
import com.wwws.wwwsvpn.myapplication.utils.UtilsMD5;
import com.wwws.wwwsvpn.myapplication.view.LoadingDialog;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2018/5/22.
 */

public class RegisterAppActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    private final static String REGEX_MOBILE = "^1\\d{10}$";
    private final static String REGEX_PWD = "^[\\da-zA-Z~!@#$%^&*]{6,18}$";
    @BindView(R.id.ed_setphonenum)
    EditText edSetPhoneNum;
    @BindView(R.id.et_verifycode)
    EditText etVerifyCode;
    @BindView(R.id.btn_vericode)
    Button btnGetVericode;
    @BindView(R.id.ed_setpwd)
    EditText edSetPWD;
    @BindView(R.id.et_pwd_again)
    EditText etPWDagain;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private String verify;

    @BindView(R.id.tb_register)
    TopBar tbRegister;

    @BindView(R.id.bt_register)
    Button btRegister;

    /**
     * 60秒倒计时
     */
    private int countDown = 60;
    private LoginInfo loginInfo;
    private Handler mHandler = new Handler();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        loadingView = new LoadingDialog.Builder(this)
                .setMessage(getString(R.string.loading_captcha))
                .setCancelable(false)
                .create();
        tbRegister.setBackClickListener(this);
        edSetPWD.setTypeface(Typeface.DEFAULT);
        edSetPWD.setTransformationMethod(new PasswordTransformationMethod());
        etPWDagain.setTypeface(Typeface.DEFAULT);
        etPWDagain.setTransformationMethod(new PasswordTransformationMethod());
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }


    @OnClick({R.id.btn_vericode, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_vericode:
                getVerifyCode();
                break;
            case R.id.bt_register:
                submit();
                break;
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(RegisterAppActivity.this);
    }

    private void submit() {
        String t = String.valueOf(System.currentTimeMillis());

        final String phoneNum = edSetPhoneNum.getText().toString();
        String verifyCode = etVerifyCode.getText().toString();
        final String password = edSetPWD.getText().toString();
        String confirmpwd = etPWDagain.getText().toString();

        if (phoneNum.isEmpty()) {
            Toast.makeText(this, getString(R.string.mobilenum_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (!Pattern.matches(REGEX_MOBILE, phoneNum)) {
            Toast.makeText(this, getString(R.string.mobilenum_format_error), Toast.LENGTH_SHORT).show();
            return;
        }else if (verifyCode.isEmpty()) {
            Toast.makeText(this, getString(R.string.verifycode_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (verifyCode.length() != 6) {
            Toast.makeText(this, getString(R.string.verifycode_format_error), Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.pwd_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (!Pattern.matches(REGEX_PWD, password)) {
            Toast.makeText(this, getString(R.string.pwd_format_error), Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmpwd.isEmpty()) {
            Toast.makeText(this, getString(R.string.confirmpwd_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (!(password.equals(confirmpwd))) {
            Toast.makeText(this, getString(R.string.pwd_confirm_error), Toast.LENGTH_SHORT).show();
            return;
        }

        verify = verifyCode;

        loadingView.show();

        String imei = Unique.getid(RegisterAppActivity.this);
        String mac = Unique.getMacid(RegisterAppActivity.this);
        String unique = UtilsMD5.MD5(imei + mac );
        Call<RegisterModel> registerCall = requestInterface.register(phoneNum, confirmpwd, unique,verify);
        registerCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    RegisterModel.DataBean userInfo = response.body().getData();
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putString("account", phoneNum);
                    editor.putString("password", password);
                    editor.putInt("vip_expire_date", userInfo.getVip_expire_time());
                    editor.putString("uid", String.valueOf(userInfo.getUid()));
                    editor.putInt("vip_type", response.body().getData().getVip_type().equals("elite")?1:2);
                    editor.putBoolean("is_binded", userInfo.getIs_bind() == 1 ? true : false);
                    editor.putString("shareCode", userInfo.getShare_code());
                    editor.putString("refereeCode", userInfo.getReferee_code());
                    editor.putInt("diamond", userInfo.getDiamond());
                    editor.putString("token", userInfo.getToken());
                    if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                        editor.putBoolean("close_old_vpn", true);
                    }
                    editor.commit();

                    LoginInfo loginInfo = new LoginInfo(phoneNum, password);
                    Log.d("dqdffwefe", password);
                    FileOutputStream fos = null;
                    ObjectOutputStream oos = null;
                    String state = Environment.getExternalStorageState();
                    if (!state.equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(getBaseContext(), "请检查SD卡", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = Environment.getExternalStorageDirectory();
                    Log.d("ffwefewf", "11");
                    try {
                        Log.d("ffwefewf", "dd");
                        File myfile = new File(file.getCanonicalPath(), "/setting.txt");
                        fos = new FileOutputStream(myfile);
                        oos = new ObjectOutputStream(fos);
                        oos.writeObject(loginInfo);
                        //  Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
                        oos.flush();
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Intent intent = new Intent(RegisterAppActivity.this, MainActivity.class);
                    startActivity(intent);
                    AppManager.getAppManager().finishAllActivity();
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(RegisterAppActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
                ToastUtils.showShort(RegisterAppActivity.this, t.getMessage());
            }
        });
    }

    /**
     * 获取手机验证码
     */
    private void getVerifyCode() {




        String phoneNum = edSetPhoneNum.getText().toString();
        if (!Pattern.matches(REGEX_MOBILE, phoneNum)) {
            Toast.makeText(this, getString(R.string.mobilenum_format_error), Toast.LENGTH_SHORT).show();
            return;
        }

        loadingView.show();
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<VerifyModel> verifyCall = requestInterface.verifyNotHeader(authorization,phoneNum);
        verifyCall.enqueue(new Callback<VerifyModel>() {
            @Override
            public void onResponse(Call<VerifyModel> call, Response<VerifyModel> response) {

                if (response.body().getStatus() == REQ_SUCCESS) {
                    new Thread(new MyCountDownTimer()).start();//开始执行
                    loadingView.dismiss();
                    ToastUtils.showShort(RegisterAppActivity.this, getString(R.string.send_success));
                } else {
                    loadingView.dismiss();
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(RegisterAppActivity.this, map.get(error_code));
                }

            }

            @Override
            public void onFailure(Call<VerifyModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
                ToastUtils.showShort(RegisterAppActivity.this, t.getMessage());
            }
        });
    }



    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable {

        @Override
        public void run() {

            //倒计时开始，循环
            while (countDown > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnGetVericode.setClickable(false);
                        btnGetVericode.setText(getString(R.string.request_again) + countDown + ")");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDown--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    btnGetVericode.setClickable(true);
                    btnGetVericode.setText(getString(R.string.get_VerifyCode));
                }
            });
            countDown = 60; //最后再恢复倒计时时长
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbRegister.setBackgroundResource(R.drawable.shape_topbar_wz);
            btRegister.setBackgroundResource(R.mipmap.submit);
            llRegister.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
        }
    }
}
