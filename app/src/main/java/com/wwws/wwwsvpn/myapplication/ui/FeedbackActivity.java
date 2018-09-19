package com.wwws.wwwsvpn.myapplication.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.FeedModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.regex.Pattern;

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


public class FeedbackActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    private final static String REGEX_MOBILE = "^1\\d{10}$";
    private final static String REGEX_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";


    @BindView(R.id.tb_feedback)
    TopBar tbFeedback;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.bt_feedback)
    Button btFeedback;
    @BindView(R.id.ll_feedback_top)
    LinearLayout llFeedbackTop;
    @BindView(R.id.iv_state)
    ImageView ivState;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbFeedback.setBackClickListener(this);

    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(FeedbackActivity.this);
    }

    /**
     * 向服务器发送请求
     */
    @OnClick(R.id.bt_feedback)
    public void onViewClicked() {
        String content = etContent.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, getString(R.string.feedback_cannot_empty), Toast.LENGTH_SHORT).show();
            return;
        } else if (content.length() < 15) {
            Toast.makeText(this, getString(R.string.feedback_cannot_minlength), Toast.LENGTH_SHORT).show();
            return;
        } else if(!contact.isEmpty()) {
            if (!Pattern.matches(REGEX_MOBILE, contact) && !Pattern.matches(REGEX_EMAIL, contact)) {
                Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        loadingView.show();
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<FeedModel> feedCall = requestInterface.feed(authorization, content, contact);
        feedCall.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    ToastUtils.showShort(FeedbackActivity.this, response.body().getInfo());
                    AppManager.getAppManager().finishActivity(FeedbackActivity.this);
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(FeedbackActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                loadingView.dismiss();
                ToastUtils.showShort(FeedbackActivity.this, getString(R.string.send_failure) + t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbFeedback.setBackgroundResource(R.drawable.shape_topbar_wz);
            btFeedback.setBackgroundResource(R.mipmap.submit);
            llFeedbackTop.setBackgroundColor(Color.parseColor("#d8a04e"));
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
