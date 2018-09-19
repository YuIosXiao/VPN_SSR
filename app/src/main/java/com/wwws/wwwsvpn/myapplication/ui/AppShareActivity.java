package com.wwws.wwwsvpn.myapplication.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.UserInfoModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.DensityUtils;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.utils.Unique;
import com.wwws.wwwsvpn.myapplication.utils.UtilsMD5;
import com.wwws.wwwsvpn.myapplication.view.LoadingDialog;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

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
 * Created by admin on 2018/5/28.
 */

public class AppShareActivity extends BaseActivity {
    private final static int REQ_SUCCESS = 1;
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    LoadingDialog loadingView;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.ll_appshare)
    LinearLayout llAppShare;
    @BindView(R.id.tb_appshare)
    TopBar tbAppShare;
    @BindView(R.id.share_info)
    LinearLayout llShareInfo;
    @BindView(R.id.user_share_code)
    TextView tvUserShareCode;
    @BindView(R.id.bind_share_code)
    TextView tvBindShareCode;
    @BindView(R.id.edit_share_code)
    EditText editShareCode;
    @BindView(R.id.share_copy)
    Button copyShareCodeBtn;
    @BindView(R.id.share_bind)
    Button bindShareCodeBtn;
    @BindView(R.id.share_wechat)
    Button shareToWeChat;
    @BindView(R.id.share_pyq)
    Button shareToWeChatPYQ;
    @BindView(R.id.share_weibo)
    Button shareToWeibo;
    @BindView(R.id.share_qq)
    Button shareToQQ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_appshare;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbAppShare.setBackClickListener(this);
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
        tvUserShareCode.setText(mSharedPreference.getString("shareCode", ""));
        if (mSharedPreference.getString("refereeCode", "").isEmpty()) {
            editShareCode.setVisibility(View.VISIBLE);
            tvBindShareCode.setVisibility(View.GONE);
        } else {
            editShareCode.setVisibility(View.GONE);
            bindShareCodeBtn.setVisibility(View.GONE);
            tvBindShareCode.setVisibility(View.VISIBLE);
            tvBindShareCode.setText(mSharedPreference.getString("refereeCode", ""));
        }
        int defaultValue = DensityUtils.dip2px(AppShareActivity.this,60);
        Drawable wcDrawable = getResources().getDrawable(R.mipmap.wechat_icon);
        wcDrawable.setBounds(0,0, defaultValue, defaultValue);
        shareToWeChat.setCompoundDrawables(null,wcDrawable,null,null);

        Drawable pyqDrawable = getResources().getDrawable(R.mipmap.wechat_pyq_icon);
        pyqDrawable.setBounds(0,0, defaultValue, defaultValue);
        shareToWeChatPYQ.setCompoundDrawables(null,pyqDrawable,null,null);

        Drawable weiboDrawable = getResources().getDrawable(R.mipmap.sina_weibo_icon);
        weiboDrawable.setBounds(0,0, defaultValue, defaultValue);
        shareToWeibo.setCompoundDrawables(null,weiboDrawable,null,null);

        Drawable qqDrawable = getResources().getDrawable(R.mipmap.qq_icon);
        qqDrawable.setBounds(0,0, defaultValue, defaultValue);
        shareToQQ.setCompoundDrawables(null,qqDrawable,null,null);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(AppShareActivity.this);
    }

    /**
     * 切换账号
     */
    private void bindRefereeCode() {
        final String refereeCode = editShareCode.getText().toString();
        if (refereeCode.isEmpty()) {
            Toast.makeText(this, getString(R.string.refereecode_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        loadingView = new LoadingDialog.Builder(AppShareActivity.this)
                .setMessage(getString(R.string.loading_text))
                .setCancelable(false)
                .create();
        loadingView.show();
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<UpdataModel> loginCall = requestInterface.bindShareCode(authorization,  refereeCode);
        loginCall.enqueue(new Callback<UpdataModel>() {
            @Override
            public void onResponse(Call<UpdataModel> call, Response<UpdataModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    editShareCode.setVisibility(View.GONE);
                    tvBindShareCode.setVisibility(View.VISIBLE);
                    bindShareCodeBtn.setVisibility(View.GONE);
                    tvBindShareCode.setText(refereeCode);
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putString("refereeCode", refereeCode);
                    editor.commit();
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(AppShareActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<UpdataModel> call, Throwable t) {
                loadingView.dismiss();
                ToastUtils.showShort(AppShareActivity.this, getString(R.string.referee_bind_share));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbAppShare.setBackgroundResource(R.drawable.shape_topbar_wz);
            copyShareCodeBtn.setBackgroundResource(R.mipmap.king_share_btnbg);
            bindShareCodeBtn.setBackgroundResource(R.mipmap.king_share_btnbg);
            llShareInfo.setBackgroundResource(R.mipmap.king_share_body);
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

    @OnClick({R.id.share_bind,R.id.share_copy,R.id.share_wechat,R.id.share_pyq,R.id.share_weibo,R.id.share_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_bind:
                bindRefereeCode();
                break;
            case R.id.share_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvUserShareCode.getText());
                Toast.makeText(this, "复制成功!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_wechat:
                new ShareAction(AppShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case R.id.share_pyq:
                new ShareAction(AppShareActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case R.id.share_weibo:
                new ShareAction(AppShareActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
            case R.id.share_qq:
                new ShareAction(AppShareActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(AppShareActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AppShareActivity.this,"失 败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AppShareActivity.this,"取消了",Toast.LENGTH_LONG).show();
        }
    };
}
