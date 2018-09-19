package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.VerifyModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.SPUtils;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.LoadingDialog;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import org.bouncycastle.math.raw.Mod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */


public class ModifyPWDActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    private final static String REGEX_MOBILE = "^(13[0-9]|15[0-3,5-9]|17[0-9]|18[0-9])\\d{8}$";
    @BindView(R.id.ed_setPhonepsd)
    EditText edSetPhonepsd;
    @BindView(R.id.et_phonepsd_again)
    EditText etPhonepsdagain;
    @BindView(R.id.et_verifycode)
    EditText etVerifyCode;
    @BindView(R.id.ll_modifypsd)
    LinearLayout llModifypsd;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private String verify;

    @BindView(R.id.tb_modify)
    TopBar tbModify;
    @BindView(R.id.btn_vericode)
    Button btnGetVericode;
    @BindView(R.id.bt_register_next)
    Button btRegisterNext;

    /**
     * 60秒倒计时
     */
    private int countDown = 60;
    private LoginInfo loginInfo;
    private Handler mHandler = new Handler();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifypsd;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        loadingView = new LoadingDialog.Builder(this)
                .setMessage(getString(R.string.loading_captcha))
                .setCancelable(false)
                .create();
        tbModify.setBackClickListener(this);
        edSetPhonepsd.setTypeface(Typeface.DEFAULT);
        edSetPhonepsd.setTransformationMethod(new PasswordTransformationMethod());
        etPhonepsdagain.setTypeface(Typeface.DEFAULT);
        etPhonepsdagain.setTransformationMethod(new PasswordTransformationMethod());
        etVerifyCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edSetPhonepsd.setFocusable(true);
                    edSetPhonepsd.requestFocus();
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }


    @OnClick({R.id.btn_vericode, R.id.bt_register_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_vericode:
                getVerifyCode();
                break;
            case R.id.bt_register_next:
                submit();
                break;
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(ModifyPWDActivity.this);
    }

    private void submit() {
        String userd = mSharedPreference.getString("userid", "");
        Log.d("bind123", userd);
        String t = String.valueOf(System.currentTimeMillis());

        String verifyCode = etVerifyCode.getText().toString();
        final String password = edSetPhonepsd.getText().toString();
        String confirmpwd = etPhonepsdagain.getText().toString();

        if (verifyCode.isEmpty()) {
            Toast.makeText(this, getString(R.string.verifycode_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (verifyCode.length() != 6) {
            Toast.makeText(this, getString(R.string.verifycode_format_error), Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.pwd_cannot_empty), Toast.LENGTH_SHORT).show();
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


        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<UpdataModel> bindCall = requestInterface.changePassword(authorization, mSharedPreference.getString("account", ""), password, verify);
        bindCall.enqueue(new Callback<UpdataModel>() {
            @Override
            public void onResponse(Call<UpdataModel> call, Response<UpdataModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {

                    SharedPreferences.Editor editor = mSharedPreference.edit();
                   // editor.putString("password", edSetPhonepsd.getText().toString());
                    editor.commit();
                    editor.putBoolean("is_binded", true);
                    editor.commit();
                    ToastUtils.showShort(ModifyPWDActivity.this, getString(R.string.mobile_bind_success));


                    loginInfo = new LoginInfo(mSharedPreference.getString("account", ""),
                            password);
                    FileOutputStream fos = null;
                    ObjectOutputStream oos = null;
                    String state = Environment.getExternalStorageState();
                    if (!state.equals(Environment.MEDIA_MOUNTED)) {
                       // Toast.makeText(getBaseContext(), "请检查SD卡", Toast.LENGTH_SHORT).show();
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
                       // Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
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
                    SPUtils.remove(ModifyPWDActivity.this,"token");
                    AppManager.getAppManager().finishActivity(ModifyPWDActivity.this);
                    Intent intentApplogin = new Intent(ModifyPWDActivity.this,AppLoginActivity.class);
                    startActivity(intentApplogin);
                    finish();


                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ModifyPWDActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<UpdataModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
                ToastUtils.showShort(ModifyPWDActivity.this, t.getMessage());
            }
        });
    }

    /**
     * 获取手机验证码
     */
    private void getVerifyCode() {


        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        loadingView.show();
        Call<VerifyModel> verifyCall = requestInterface.changePasswordverify(authorization);
        verifyCall.enqueue(new Callback<VerifyModel>() {
            @Override
            public void onResponse(Call<VerifyModel> call, Response<VerifyModel> response) {

                if (response.body().getStatus() == REQ_SUCCESS) {
                    new Thread(new MyCountDownTimer()).start();//开始执行
                    loadingView.dismiss();
                    ToastUtils.showShort(ModifyPWDActivity.this, getString(R.string.send_success));
                } else {
                    loadingView.dismiss();
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ModifyPWDActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<VerifyModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
              //  ToastUtils.showShort(ModifyPWDActivity.this, t.getMessage());
            }
        });
    }



    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class   MyCountDownTimer implements Runnable {

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
            tbModify.setBackgroundResource(R.drawable.shape_topbar_wz);
            btRegisterNext.setBackgroundResource(R.mipmap.submit);
            llModifypsd.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
        }
        hideBottomUIMenu();
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
