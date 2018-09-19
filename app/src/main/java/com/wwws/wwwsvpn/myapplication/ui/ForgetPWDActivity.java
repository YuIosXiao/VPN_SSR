package com.wwws.wwwsvpn.myapplication.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.FeedModel;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.VerifyModel;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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


public class ForgetPWDActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    private final static String REGEX_MOBILE = "^1\\d{10}$";
    private final static String REGEX_PWD = "^[\\da-zA-Z~!@#$%^&*]{6,18}$";
    @BindView(R.id.ed_setphonenum)
    EditText edSetPhoneNum;
    @BindView(R.id.ed_setpwd)
    EditText edSetPhonepsd;
    @BindView(R.id.et_pwd_again)
    EditText etPhonepsdagain;
    @BindView(R.id.et_verifycode)
    EditText etVerifyCode;
    @BindView(R.id.ll_forgetpsd)
    LinearLayout llForgetpsd;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private String verify;

    @BindView(R.id.tb_forgetpwd)
    TopBar tbForgetpwd;
    @BindView(R.id.btn_vericode)
    Button btnGetVericode;
    @BindView(R.id.bt_forgetpwd)
    Button btForget;

    /**
     * 60秒倒计时
     */
    private int countDown = 60;
    private LoginInfo loginInfo;
    private Handler mHandler = new Handler();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpsd;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        loadingView = new LoadingDialog.Builder(this)
                .setMessage(getString(R.string.loading_captcha))
                .setCancelable(false)
                .create();
        tbForgetpwd.setBackClickListener(this);
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


    @OnClick({R.id.btn_vericode, R.id.bt_forgetpwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_vericode:
                //etVerifyCode.requestFocus();
                getVerifyCode();
                break;
            case R.id.bt_forgetpwd:
                submit();
                break;
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(ForgetPWDActivity.this);
    }

    private void submit() {
        String userd = mSharedPreference.getString("userid", "");
        Log.d("bind123", userd);
        String t = String.valueOf(System.currentTimeMillis());

        String phoneNum = edSetPhoneNum.getText().toString();
        String verifyCode = etVerifyCode.getText().toString();
        final String password = edSetPhonepsd.getText().toString();
        String confirmpwd = etPhonepsdagain.getText().toString();

        if (!Pattern.matches(REGEX_MOBILE, phoneNum)) {
            Toast.makeText(this, getString(R.string.mobilenum_format_error), Toast.LENGTH_SHORT).show();
            return;
        }if (verifyCode.isEmpty()) {
            Toast.makeText(this, getString(R.string.verifycode_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (verifyCode.length() != 6) {
            Toast.makeText(this, getString(R.string.verifycode_format_error), Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.pwd_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        }else if (!Pattern.matches(REGEX_PWD, password)) {
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

        Call<FeedModel> forgetCall = requestInterface.forgetPassword(phoneNum, password, verify);
        forgetCall.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                  //  ToastUtils.showShort(ForgetPWDActivity.this, response.body().getInfo());
                    AppManager.getAppManager().finishActivity(ForgetPWDActivity.this);
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ForgetPWDActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
                ToastUtils.showShort(ForgetPWDActivity.this, t.getMessage());
            }
        });
    }

    /**
     * 忘记验证码
     */
    private void getVerifyCode() {

        String phoneNum = edSetPhoneNum.getText().toString();
        if (!Pattern.matches(REGEX_MOBILE, phoneNum)) {
            Toast.makeText(this, getString(R.string.mobilenum_format_error), Toast.LENGTH_SHORT).show();
            return;
        }

        String authorization = "Bearer " + mSharedPreference.getString("token", "");

        loadingView.show();
        Call<VerifyModel> verifyCall = requestInterface.forgetPasswordVerify(authorization,phoneNum);
        verifyCall.enqueue(new Callback<VerifyModel>() {
            @Override
            public void onResponse(Call<VerifyModel> call, Response<VerifyModel> response) {

                if (response.body().getStatus() == REQ_SUCCESS) {
                    new Thread(new MyCountDownTimer()).start();//开始执行
                    loadingView.dismiss();
                    ToastUtils.showShort(ForgetPWDActivity.this, getString(R.string.send_success));
                } else {
                    loadingView.dismiss();
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ForgetPWDActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<VerifyModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("mmp1", t.getMessage());
                ToastUtils.showShort(ForgetPWDActivity.this, t.getMessage());
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
            tbForgetpwd.setBackgroundResource(R.drawable.shape_topbar_wz);
            btForget.setBackgroundResource(R.mipmap.submit);
            llForgetpsd.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
        }

    }


}
