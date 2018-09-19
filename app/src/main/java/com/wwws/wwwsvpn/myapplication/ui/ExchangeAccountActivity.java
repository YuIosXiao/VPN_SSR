package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shadowsocks.constant.State;
import com.github.shadowsocks.utils.SS_SDK;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.Info;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.UserInfoModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.utils.Unique;
import com.wwws.wwwsvpn.myapplication.utils.UtilsMD5;
import com.wwws.wwwsvpn.myapplication.view.LoadingDialog;

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


public class ExchangeAccountActivity extends BaseActivity {
    private final static int REQ_SUCCESS = 1;
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    LoadingDialog loadingView;
    @BindView(R.id.ed_user)
    EditText edUserAccount;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.bt_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.top_image)
    ImageView ivTopImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(ExchangeAccountActivity.this);
    }

    /**
     * 切换账号
     */
    private void dologin() {
        String account = edUserAccount.getText().toString();
        final String password = edPassword.getText().toString();
        if (account.isEmpty()) {
            Toast.makeText(this, getString(R.string.mobilenum_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.verifycode_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        loadingView = new LoadingDialog.Builder(ExchangeAccountActivity.this)
                .setMessage(getString(R.string.loading_text))
                .setCancelable(false)
                .create();
        loadingView.show();
        String ssr = mSharedPreference.getString("ssr", "");
        String imei = Unique.getid(ExchangeAccountActivity.this);
        String mac = Unique.getMacid(ExchangeAccountActivity.this);
        String unique = UtilsMD5.MD5(imei + mac);
        Call<UserInfoModel> loginCall = requestInterface.login(account, password, unique);
        loginCall.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                loadingView.dismiss();
                SharedPreferences.Editor editor = mSharedPreference.edit();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    UserInfoModel.DataBean userInfo = response.body().getData();
                    editor.putString("account", userInfo.getAccount());
                    editor.putString("password", password);
                    editor.putInt("used_status", userInfo.getUsed_status());
                    editor.putInt("vip_expire_date", userInfo.getVip_expire_time());
                    editor.putInt("uid", userInfo.getUid());
                    editor.putInt("vip_type", userInfo.getVip_type().equals("elite")?1:2);
                    editor.putBoolean("is_binded", userInfo.getIs_bind()==1?true:false);
                    editor.putInt("diamond", userInfo.getDiamond());
                    editor.putInt("has_tested", userInfo.getIs_tested());
                    editor.putString("shareCode", userInfo.getShare_code());
                    editor.putString("refereeCode", userInfo.getReferee_code());
                    editor.putString("token", userInfo.getToken());
                    editor.putBoolean("mIsFirstIn", false);
                    if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                        editor.putBoolean("close_old_vpn", true);
                    }
                    editor.commit();

                    LoginInfo loginInfo = new LoginInfo(response.body().getData().getAccount(),
                            password);
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
                    ToastUtils.showShort(ExchangeAccountActivity.this, getString(R.string.loading_success));
                    Intent mainIntent = new Intent(ExchangeAccountActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    AppManager.getAppManager().finishAllActivity();
                    //finish();
                } else {
                    Log.d("'a1a1a1a1",response.body().getStatus()+"");
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ExchangeAccountActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("'a1a1a1a1", t.getMessage());
                ToastUtils.showShort(ExchangeAccountActivity.this, getString(R.string.loading_failure));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            llLogin.setBackgroundResource(R.mipmap.login_bg2);
            ivTopImage.setBackgroundResource(R.mipmap.login_icon_wz);
            btnLogin.setBackgroundResource(R.drawable.shape_login_wz);
            tvForget.setTextColor(Color.parseColor("#C37806"));
            tvRegister.setTextColor(Color.parseColor("#C37806"));
            ivLine.setBackgroundColor(Color.parseColor("#C37806"));
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

    @OnClick({R.id.ll_back, R.id.bt_login,R.id.tv_register,R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                AppManager.getAppManager().finishActivity(ExchangeAccountActivity.this);
                break;
            case R.id.bt_login:
                dologin();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(ExchangeAccountActivity.this, RegisterAppActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget:
                Intent forgetIntent = new Intent(ExchangeAccountActivity.this, ForgetPWDActivity.class);
                startActivity(forgetIntent);
                break;
        }
    }

}
