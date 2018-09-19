package com.wwws.wwwsvpn.myapplication.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.shadowsocks.constant.State;
import com.github.shadowsocks.utils.SS_SDK;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.FirstLoginModel;
import com.wwws.wwwsvpn.myapplication.model.Info;
import com.wwws.wwwsvpn.myapplication.model.RouteTable;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.utils.Unique;
import com.wwws.wwwsvpn.myapplication.utils.UtilsMD5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2018/5/31.
 */

public class AppSplashActivity extends BaseActivity {
    private final static int FIRST_CREATE = 3;
    private final static int BING_PHONE = 1;
    private final static int REQ_SUCCESS = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"};
    @Override
    protected int getLayoutId() {
        return R.layout.app_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = new Intent(AppSplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void backUpActivity() {
        AppManager.getAppManager().finishActivity(AppSplashActivity.this);
    }


}
