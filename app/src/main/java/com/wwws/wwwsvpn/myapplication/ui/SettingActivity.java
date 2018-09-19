package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.shadowsocks.constant.State;
import com.github.shadowsocks.utils.SS_SDK;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    @BindView(R.id.ll_warm)
    LinearLayout llWarm;
    @BindView(R.id.ll_warm_line)
    View llWarmLine;
    @BindView(R.id.iv_password_line)
    View pwdLine;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private Boolean visible = false;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.bt_eye)
    Button btEye;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    private Handler mHandler = new Handler();
    @BindView(R.id.ll_change_pwd)
    LinearLayout llChangePWD;
    @BindView(R.id.ll_Binding)
    LinearLayout llBinding;
    @BindView(R.id.tb_back)
    TopBar tbBack;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.ll_opinion)
    LinearLayout llOpinion;
    @BindView(R.id.usedpc_line)
    View usedLine;
 /*   @BindView(R.id.ll_usedpc)
    LinearLayout usedInPC;*/


    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.appshare_btn)
    Button appShareBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbBack.setBackClickListener(this);
        btEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!visible) {
                    tvVip.setText(mSharedPreference.getString("password", ""));
                    btEye.setBackgroundResource(R.mipmap.visible);
                    visible = true;
                } else {
                    tvVip.setText("●●●●●●");
                    btEye.setBackgroundResource(R.mipmap.invisible);
                    visible = false;
                }
            }
        });

        if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
           // usedInPC.setVisibility(View.VISIBLE);
            usedLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        tvUserid.setText(mSharedPreference.getString("account", ""));
        tvVip.setText("●●●●●●");
        Log.d("56465", mSharedPreference.getBoolean("is_binded", false) + "");
        if (mSharedPreference.getBoolean("is_binded", false)) {
            llPassword.setVisibility(View.GONE);
            llWarm.setVisibility(View.GONE);
            llWarmLine.setVisibility(View.GONE);
            pwdLine.setVisibility(View.GONE);
        } else {
            if (mSharedPreference.getString("password","").isEmpty()){
                llPassword.setVisibility(View.GONE);
                pwdLine.setVisibility(View.GONE);
            } else {
                llPassword.setVisibility(View.VISIBLE);
                pwdLine.setVisibility(View.VISIBLE);
            }
            llWarm.setVisibility(View.VISIBLE);
            llWarmLine.setVisibility(View.VISIBLE);
        }
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbBack.setBackgroundResource(R.drawable.shape_topbar_wz);
            appShareBtn.setBackgroundResource(R.mipmap.submit);
            llSetting.setBackgroundColor(Color.parseColor("#d8a04e"));
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


    @OnClick({R.id.ll_Binding, R.id.ll_change_pwd, R.id.ll_opinion, R.id.ll_feedback, R.id.ll_help,/* R.id.ll_usedpc,*/ R.id.appshare_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_Binding:
                Intent intent4 = new Intent(SettingActivity.this, BindingActivity.class);
                startActivity(intent4);
                break;

            case R.id.ll_change_pwd:
                if (mSharedPreference.getBoolean("is_binded", false)) {
                    Intent intent3 = new Intent(SettingActivity.this, ModifyPWDActivity.class);
                    startActivity(intent3);
                } else {
                    ToastUtils.showShort(SettingActivity.this, getString(R.string.toast_bind_phone));
                }
                break;
            case R.id.ll_opinion:
                Intent accountIntent = new Intent(SettingActivity.this, ExchangeAccountActivity.class);
                startActivity(accountIntent);
       /*         ShareDialog shareDialog = new ShareDialog(SettingActivity.this);
                shareDialog.show();*/
                break;
            case R.id.ll_feedback:
                Intent intent2 = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_help:
                Intent helpIntent = new Intent(SettingActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;
        /*    case R.id.ll_usedpc:
                Intent usedPCIntent = new Intent(SettingActivity.this, ServerInfoActivity.class);
                startActivity(usedPCIntent);
                break;*/

            case R.id.appshare_btn:
                Intent shareIntent = new Intent(SettingActivity.this, AppShareActivity.class);
                startActivity(shareIntent);
                break;
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(SettingActivity.this);
    }

}
