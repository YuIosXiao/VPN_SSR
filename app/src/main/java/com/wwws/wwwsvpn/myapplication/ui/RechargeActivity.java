package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.adapter.TaocanListAdapter;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.TaocanModel;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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


public class RechargeActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    @BindView(R.id.tb_vip)
    TopBar tbVip;
    @BindView(R.id.lv_vip_monthly_list)
    ListView lvVipMonthlyList;
    @BindView(R.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R.id.ll_warm)
    LinearLayout llWarm;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private List<TaocanModel.DataBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbVip.setBackClickListener(this);
        tbVip.setRightItem(getString(R.string.a54), new View.OnClickListener() {
            public void onClick(View v) {
                Intent myOrderIntent = new Intent(RechargeActivity.this, UserOrderActivity.class);
                startActivity(myOrderIntent);
            }
        });
    }

    @Override
    protected void initData() {
        String authorization = "Bearer " +  mSharedPreference.getString("token", "");
        Log.d("wsnbb", authorization);
        loadingView.show();

        Log.d("dsdada",language);
         Call<TaocanModel> taocanCall = requestInterface.taocan(authorization,language);
         taocanCall.enqueue(new Callback<TaocanModel>() {
            @Override
            public void onResponse(Call<TaocanModel> call, Response<TaocanModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    loadingView.dismiss();
                    list = response.body().getData();
                    lvVipMonthlyList.setAdapter(new TaocanListAdapter(list, RechargeActivity.this,language));

                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(RechargeActivity.this, map.get(error_code));
                }

            }

            @Override
            public void onFailure(Call<TaocanModel> call, Throwable t) {
                loadingView.dismiss();
                //   ToastUtils.showShort(RechargeActivity.this, getString(R.string.loading_failure) + t.getMessage());
            }
        });
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING) == VIP_TYPE_WANGZHE) {
            tbVip.setBackgroundResource(R.drawable.shape_topbar_wz);
            llRecharge.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
        }
        if (mSharedPreference.getBoolean("is_binded", false)) {
            llWarm.setVisibility(View.GONE);
        }
        hideBottomUIMenu();
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void backUpActivity() {
        AppManager.getAppManager().finishActivity(RechargeActivity.this);
    }

}
