package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.adapter.ServiceAdapter;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.ServiceModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.List;

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


public class ServiceActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    @BindView(R.id.lv_service)
    ListView lvService;
    @BindView(R.id.tb_service)
    TopBar tb_Service;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.tv_diamond)
    TextView tvDiamond;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private List<ServiceModel.DataBean> list;
    private ServiceAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tb_Service.setBackClickListener(this);
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING) == VIP_TYPE_WANGZHE) {
            tb_Service.setBackgroundResource(R.drawable.shape_topbar_wz);
            tvBuy.setBackgroundResource(R.drawable.shape_service_wz);
            tvBuy.setTextColor(Color.parseColor("#d8a04e"));
            llService.setBackgroundColor(Color.parseColor("#d8a04e"));
            tvDiamond.setBackgroundResource(R.mipmap.king_user_diamondbg);
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
        }
        tvDiamond.setText(mSharedPreference.getInt("diamond", 0) + "");
        hideBottomUIMenu();

        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<ServiceModel> serviceCall = requestInterface.service(authorization,language);
        loadingView.show();
        serviceCall.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    // ToastUtils.showShort(ServiceActivity.this, response.body().getInfo());
                    list = response.body().getData();
                    adapter = new ServiceAdapter(list, ServiceActivity.this);
                    lvService.setAdapter(adapter);

                    adapter.setVpnTypeListener(new ServiceAdapter.VpnTypeListener() {
                        @Override
                        public void OntopicClickListener(String vpnType) {
                            Intent i = new Intent();
                            i.putExtra("type", vpnType);
                            setResult(2, i);
                            AppManager.getAppManager().finishActivity(ServiceActivity.this);

                        }
                    });


                } else {
                    loadingView.dismiss();
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(ServiceActivity.this, map.get(error_code));
                }
                //   ToastUtils.showShort(ServiceActivity.this,response.body().getData().get(0).getPrice());

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                loadingView.dismiss();
                //    ToastUtils.showShort(ServiceActivity.this, "无法连接服务器");
            }
        });
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

    @OnClick(R.id.tv_buy)
    public void onViewClicked() {
        Intent intent = new Intent(ServiceActivity.this, RechargeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(ServiceActivity.this);
    }

}
