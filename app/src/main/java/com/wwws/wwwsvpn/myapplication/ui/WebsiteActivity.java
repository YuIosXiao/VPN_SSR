package com.wwws.wwwsvpn.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.adapter.NavigationAdapter;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.WebsiteModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class WebsiteActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    @BindView(R.id.iv_state)
    ImageView ivState;
    private NavigationAdapter adapter;
    @BindView(R.id.lv_website)
    ListView lvWebsite;
    @BindView(R.id.tb_website)
    TopBar tbWebsite;
    List<WebsiteModel.DataBean> webSiteList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_website;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbWebsite.setBackClickListener(this);
    }

    @Override
    protected void initData() {
        loadingView.show();
        Call<WebsiteModel> websiteCall = requestInterface.website(language);
        websiteCall.enqueue(new Callback<WebsiteModel>() {
            @Override
            public void onResponse(Call<WebsiteModel> call, Response<WebsiteModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    webSiteList = response.body().getData();
                    adapter = new NavigationAdapter(webSiteList, WebsiteActivity.this);
                    lvWebsite.setAdapter(adapter);
                    lvWebsite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            WebsiteModel.DataBean webSite = webSiteList.get(position);
                            if (isPkgInstalled(WebsiteActivity.this, webSite.getAndroid_pkg())) {
                                Intent intent = getPackageManager().getLaunchIntentForPackage(webSite.getAndroid_pkg());
                                startActivity(intent);
                            } else {
                                Uri uri = Uri.parse(webSite.getUrl());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    });
                }else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(WebsiteActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<WebsiteModel> call, Throwable t) {
                Log.d("dadad", t.getMessage());
                loadingView.dismiss();
            }
        });
        AppManager.getAppManager().addActivity(this);
    }

    public  boolean isPkgInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        android.content.pm.ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(WebsiteActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbWebsite.setBackgroundResource(R.drawable.shape_topbar_wz);
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
