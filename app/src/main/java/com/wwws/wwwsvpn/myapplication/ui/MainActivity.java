package com.wwws.wwwsvpn.myapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shadowsocks.constant.State;
import com.github.shadowsocks.utils.SS_SDK;
import com.github.shadowsocks.utils.VpnCallback;
import com.google.gson.Gson;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.AppInfoModel;
import com.wwws.wwwsvpn.myapplication.model.Disconnect;
import com.wwws.wwwsvpn.myapplication.model.FirstLoginModel;
import com.wwws.wwwsvpn.myapplication.model.Info;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.RouteTable;
import com.wwws.wwwsvpn.myapplication.model.SSRModel;
import com.wwws.wwwsvpn.myapplication.model.ServerModel;
import com.wwws.wwwsvpn.myapplication.utils.ASCIIUtils;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.SPUtils;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.utils.Unique;
import com.wwws.wwwsvpn.myapplication.utils.UtilsMD5;
import com.wwws.wwwsvpn.myapplication.view.AppNoticeDialog;
import com.wwws.wwwsvpn.myapplication.view.AppUpdateDialog;
import com.wwws.wwwsvpn.myapplication.view.SignDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class MainActivity extends BaseActivity implements VpnCallback {
    /**
     * 接口请求状态
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"};
    private final static int FIRST_CREATE = 3;
    private final static int BING_PHONE = 1;
    private final static int REQ_CODE = 1;
    private final static int REQ_SUCCESS = 1;
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private static final String TAG = "MainActivity123";
    @BindView(R.id.tv_main_data)
    TextView tvMainData;
    @BindView(R.id.iv_personal)
    ImageView ivPersonal;
    @BindView(R.id.bt_connect)
    Button btConnect;
    @BindView(R.id.ll_menu_service)
    LinearLayout llMenuService;
    @BindView(R.id.ll_menu_recharge)
    LinearLayout llMenuRecharge;
    @BindView(R.id.tv_main_diamond)
    TextView tvMainDiamond;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.ll_navigation)
    LinearLayout llNavigation;
    @BindView(R.id.ll_Recharge)
    LinearLayout llRecharge;
    @BindView(R.id.ll_main)
    RelativeLayout llMain;
    @BindView(R.id.iv_main_xuanze)
    ImageView ivMainXuanze;
    @BindView(R.id.ll_mian_tab)
    TextView llMianTab;
    @BindView(R.id.connecting_anim)
    ImageView connectingAnim;
    @BindView(R.id.fl_btn)
    FrameLayout flBtn;
    @BindView(R.id.iv_state)
    ImageView ivState;

    private Boolean connect = false;
    private int vpnState = -1;
    private LoginInfo loginInfo;
    //本地的socket是否连接（连接按钮是否点击）
    private Boolean socket = false;
    //App更新弹窗
    private AppUpdateDialog updateDialog;

    //通知弹窗
    private AppNoticeDialog noticeDialog;

    //签到弹窗
    private SignDialog signDialog;

    private boolean isFirstInMain = true;
    public boolean isLogined = false;


    @Override
    protected int getLayoutId() {
        return R.layout.layout_menu;

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SS_SDK.getInstance().setStateCallback(this);
        //saveSetting();
        String imei = Unique.getid(MainActivity.this);
        String mac = Unique.getMacid(MainActivity.this);
        String unique = UtilsMD5.MD5(imei + mac);
        Log.d("edqwdwqdqw", unique);
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Log.d("wsnbb", authorization);
        llMain.setVisibility(View.GONE);

      /*  DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Log.d("dadfadawda", screenWidth + "_" + screenHeight);*/

    }

    /**
     * 切换连接服务器
     */
    private void initSSR() {
        btConnect.setClickable(false);
        Log.d("vip_type", mSharedPreference.getInt("vip_type", 0) + "");
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            ivState.setBackgroundResource(R.mipmap.huang_conneting);
            if (language.equals("en")) {
                ivState.setBackgroundResource(R.mipmap.en_king_linking);
            }
        } else {
            ivState.setBackgroundResource(R.mipmap.lan_connecting);
            if (language.equals("en")) {
                ivState.setBackgroundResource(R.mipmap.en_elites_linking);
            }
        }
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.anim_connect);
        if (rotate != null) {
            LinearInterpolator lir = new LinearInterpolator();
            rotate.setInterpolator(lir);
            connectingAnim.startAnimation(rotate);
        } else {
            connectingAnim.setAnimation(rotate);
            connectingAnim.startAnimation(rotate);
        }
        connectingAnim.setVisibility(View.VISIBLE);
        btConnect.setVisibility(View.GONE);
        String service_type = "elite";
        if (mSharedPreference.getInt("service_type", 1) == 1) {
            service_type = "elite";

        } else {
            service_type = "king";
        }
        Log.d("vip_type", service_type);
        Call<ServerModel> serverModelCall = requestInterface.ssr(authorization, service_type);
        serverModelCall.enqueue(new Callback<ServerModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ServerModel> call, Response<ServerModel> response) {
                if (response.body().getStatus() == REQ_SUCCESS) {
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putInt("vip_expire_date", response.body().getData().getVip_expire_time());
                    editor.putInt("vip_type", response.body().getData().getVip_type().equals("elite") ? 1 : 2);
                    editor.putString("vpn_server_info", response.body().getData().getServer_info());
                    editor.commit();
                    if (mSharedPreference.getInt("vip_expire_date", 0) == 0) {
                        tvMainData.setVisibility(View.INVISIBLE);
                    } else {
                        long timeStamp = Long.valueOf(mSharedPreference.getInt("vip_expire_date", 1));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String sd = sdf.format(new Date(timeStamp * 1000L));
                        tvMainData.setVisibility(View.VISIBLE);
                        tvMainData.setText(getString(R.string.Deadline) + sd);

                    }
                    connectVpnService(response.body().getData().getServer_info());
                    btConnect.setClickable(true);
                    //更换首页背景
                    Log.d("vip_type", response.body().getData().getVip_type().equals("elite") ? "1" : "2");
                    changeActivitySkin(response.body().getData().getVip_type().equals("elite") ? 1 : 2);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            btConnect.setVisibility(View.VISIBLE);
                            if (!connect) {
                                if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
                                    ivState.setBackgroundResource(R.mipmap.huang_connet);
                                    if (language.equals("en")) {
                                        ivState.setBackgroundResource(R.mipmap.en_king_start_link);
                                    }
                                    btConnect.setBackgroundResource(R.mipmap.button_wz);
                                } else {
                                    btConnect.setBackgroundResource(R.mipmap.btn_icon1);
                                    ivState.setBackgroundResource(R.mipmap.lan_connet);
                                    if (language.equals("en")) {
                                        ivState.setBackgroundResource(R.mipmap.en_elites_start_link);
                                    }
                                }
                                connectingAnim.clearAnimation();
                                connectingAnim.setVisibility(View.GONE);
                            }
                        }
                    }, 10000);
                } else {

                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code + "");
                    ToastUtils.showShort(MainActivity.this, map.get(error_code));
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.commit();
                    btConnect.setClickable(true);
                    Log.d("debug123", String.valueOf(response.body().getInfo()));
                    btConnect.setVisibility(View.VISIBLE);
                    if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {

                            ivState.setBackgroundResource(R.mipmap.huang_break);
                            if (language.equals("en")) {
                                ivState.setBackgroundResource(R.mipmap.en_king_end_link);
                            }
                            btConnect.setBackgroundResource(R.mipmap.button_on_wz);
                        } else {
                            btConnect.setBackgroundResource(R.mipmap.button_on);

                            ivState.setBackgroundResource(R.mipmap.lan_break);
                            if (language.equals("en")) {
                                ivState.setBackgroundResource(R.mipmap.en_elites_end_link);
                            }
                        }
                    } else {
                        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {

                            ivState.setBackgroundResource(R.mipmap.huang_connet);
                            if (language.equals("en")) {
                                ivState.setBackgroundResource(R.mipmap.en_king_start_link);
                            }
                            btConnect.setBackgroundResource(R.mipmap.button_wz);
                        } else {

                            btConnect.setBackgroundResource(R.mipmap.btn_icon1);
                            ivState.setBackgroundResource(R.mipmap.lan_connet);
                            if (language.equals("en")) {
                                ivState.setBackgroundResource(R.mipmap.en_elites_start_link);
                            }
                        }
                    }
                    connectingAnim.clearAnimation();
                    connectingAnim.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ServerModel> call, Throwable t) {
                ToastUtils.showShort(MainActivity.this, getString(R.string.connect_failure));
                btConnect.setClickable(true);
                if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                    if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {

                        ivState.setBackgroundResource(R.mipmap.huang_break);
                        if (language.equals("en")) {
                            ivState.setBackgroundResource(R.mipmap.en_king_end_link);
                        }
                        btConnect.setBackgroundResource(R.mipmap.button_on_wz);
                    } else {
                        btConnect.setBackgroundResource(R.mipmap.button_on);

                        ivState.setBackgroundResource(R.mipmap.lan_break);
                        if (language.equals("en")) {
                            ivState.setBackgroundResource(R.mipmap.en_elites_end_link);
                        }
                    }
                } else {
                    if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {

                        ivState.setBackgroundResource(R.mipmap.huang_connet);
                        if (language.equals("en")) {
                            ivState.setBackgroundResource(R.mipmap.en_king_start_link);
                        }
                        btConnect.setBackgroundResource(R.mipmap.button_wz);
                    } else {
                        btConnect.setBackgroundResource(R.mipmap.btn_icon1);

                        ivState.setBackgroundResource(R.mipmap.lan_connet);
                        if (language.equals("en")) {
                            ivState.setBackgroundResource(R.mipmap.en_elites_start_link);
                        }
                    }
                }
                btConnect.setVisibility(View.VISIBLE);
                connectingAnim.clearAnimation();
                connectingAnim.setVisibility(View.GONE);


                //   Log.d("SSR123", t.getMessage());
            }
        });
    }


    /**
     * 连接vpn服务器
     */
    public void connectVpnService(String serverInfo) {
        String serverStr = ASCIIUtils.convertHexToString(serverInfo);
        String jsonStr = ASCIIUtils.formatString(serverStr);
        Gson gson = new Gson();
        SSRModel ssrModel = gson.fromJson(jsonStr, SSRModel.class);
        SS_SDK.getInstance().setStateCallback(MainActivity.this);
       // SS_SDK.getInstance().setProfile("104.199.230.129",14549,"iMvpn123456.","aes-256-cfb","auth_sha1_v4","","tls1.2_ticket_auth","");
        if(ssrModel.getMethod().equals("AES256CFB"))
        {
            ssrModel.setMethod("aes-256-cfb");
        }
        SS_SDK.getInstance().setProfile(ssrModel.getIp(), Integer.parseInt(ssrModel.getPort()), ssrModel.getPassword(),ssrModel.getMethod(), ssrModel.getProtocol(),"",ssrModel.getObfs(),ssrModel.getObfs_param());
        Log.d("hihio", ssrModel.getIp() + "---" + Integer.parseInt(ssrModel.getPort()) + "---" + ssrModel.getPassword()+ "---" +ssrModel.getMethod() + "---" + ssrModel.getProtocol()+ "---" +ssrModel.getObfs());
        if (!socket) {
            SS_SDK.getInstance().switchVpn(MainActivity.this);
        }
    }

    @Override
    protected void initData() {
        if (mSharedPreference.getInt("diamond", 0) > 999) {
            tvMainDiamond.setText("999+");
        } else {
            tvMainDiamond.setText(mSharedPreference.getInt("diamond", 0) + "");
        }
        //
        AppManager.getAppManager().addActivity(this);
    }

    private void initRouteTable() {
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Log.d("lxz", authorization);
        Call<RouteTable> routeTable = requestInterface.getRouteTable(authorization);
        routeTable.enqueue(new Callback<RouteTable>() {
            @Override
            public void onResponse(Call<RouteTable> call, Response<RouteTable> response) {
                if (response.body().getStatus() == REQ_SUCCESS) {
                    File domainfile = new File(MainActivity.this.getApplicationInfo().dataDir, "/gfwlist.acl");
                    File ipfile = new File(MainActivity.this.getApplicationInfo().dataDir, "/china-list.acl");
                    List<RouteTable.DataBean> dataBean = response.body().getData();
                    if (dataBean.size() != 0) {
                        List<String> domainlist = dataBean.get(0).getCriteria();
                        StringBuffer sbDomain = new StringBuffer();
                        for (int i = 0; i < domainlist.size(); i++) {
                            sbDomain.append(domainlist.get(i));
                            sbDomain.append("\r\n");
                        }
                        saveSetting(domainfile, sbDomain.toString());
                        StringBuffer sbIP = new StringBuffer();
                        List<String> iplist = dataBean.get(1).getCriteria();
                        for (int i = 0; i < iplist.size(); i++) {
                            sbIP.append(iplist.get(i));
                            sbIP.append("\r\n");
                        }
                        saveSetting(ipfile, sbIP.toString());
                    }

                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code + "");
                    ToastUtils.showShort(MainActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<RouteTable> call, Throwable t) {
                Log.d("lxz", t.getMessage());
            }
        });
    }

    @Override
    protected void backUpActivity() {
        //AppManager.getAppManager().finishActivity(MainActivity.this);
        //返回键转成Home键效果
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        Log.d("312313213","ewqeqeqwe");
    }



    @OnClick({R.id.iv_personal, R.id.ll_sign, R.id.bt_connect, R.id.ll_navigation, R.id.ll_Recharge, R.id.ll_menu_service, R.id.ll_menu_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personal:
                if (isLogined == true) {
                    Intent intent1 = new Intent(this, SettingActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_connect:
                if (isLogined == true) {
                    if (netType != -1 && vpnState == State.CONNECTED) {
                        btConnect.setClickable(false);
                        disconnect();
                    } else if (netType != -1) {
                        connect = false;
                        long current = System.currentTimeMillis() / 1000;
                        if ((int) SPUtils.get(MainActivity.this, "vip_expire_date", 0) < current && (int) SPUtils.get(MainActivity.this, "diamond", 0) == 0) {
                            Intent rechargeIntent = new Intent(this, RechargeActivity.class);
                            startActivity(rechargeIntent);
                            Toast.makeText(MainActivity.this, R.string.not_enough, Toast.LENGTH_SHORT).show();
                        } else {
                            initSSR();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_menu_service:
                if (isLogined == true) {
                    Intent intent2 = new Intent(this, ServiceActivity.class);
                    startActivityForResult(intent2, REQ_CODE);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_sign:
                if (isLogined == true) {
                    signDialog = new SignDialog(MainActivity.this, loadingView, language);
                    //signDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    if (netType != -1) {
                        loadingView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                        signDialog.show();
                        loadingView.show();
                    } else {
                        ToastUtils.showShort(MainActivity.this, getString(R.string.net_error));
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_navigation:
                if (isLogined == true) {
                    Intent websiteIntent = new Intent(MainActivity.this, WebsiteActivity.class);
                    startActivity(websiteIntent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_Recharge:
                if (isLogined == true) {
                    Intent intent4 = new Intent(this, RechargeActivity.class);
                    startActivity(intent4);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_menu_recharge:
                if (isLogined == true) {
                    Intent intent5 = new Intent(this, RechargeActivity.class);
                    startActivity(intent5);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void isFirstLogin() {
        if (verifyStoragePermissions(MainActivity.this)) {
            String userToken = mSharedPreference.getString("token", "");
            Log.d("32131311", userToken + "***");
            if (userToken == null || "".equals(userToken)) {
                account();
            } else {
                if (mSharedPreference.getBoolean("close_old_vpn", false)) {
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.remove("close_old_vpn");
                    editor.commit();
                    SS_SDK.getInstance().switchVpn(MainActivity.this);
                    isLogined = true;
                    btConnect.setClickable(true);
                    Log.d("32131311", "2222");
                } else {
                    Log.d("32131311", "1111");
                    getUserInfo();
                    initRouteTable();
                }
            }
        }
    }

    /**
     * 获取App 启动信息 App版本更新检测，系统公告等
     */
    private void getAppInfo() {

        try {
            String packageName = getPackageName();
            PackageInfo pkgInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            Log.d("adadasd", packageName + "" + String.valueOf(pkgInfo.versionCode));
            Call<AppInfoModel> accountCall = requestInterface.getAppInfo(packageName, String.valueOf(pkgInfo.versionCode), language);

            accountCall.enqueue(new Callback<AppInfoModel>() {
                @Override
                public void onResponse(Call<AppInfoModel> call, Response<AppInfoModel> response) {
                    if (response.body().getStatus() == REQ_SUCCESS) {

                        AppInfoModel.DataBean.UpdateInfoBean updateInfo = response.body().getData().getUpdate_info();
                        SharedPreferences.Editor edit = mSharedPreference.edit();
                        edit.putString("pcsoftUrl", response.body().getData().getUpdate_info().getDownload_url());
                        edit.commit();
                        if (updateInfo.getHas_update() == 1) {
                            updateDialog = new AppUpdateDialog(MainActivity.this, updateInfo.getTitle(), updateInfo.getContent(), updateInfo.getDownload_url());
                            updateDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            updateDialog.setCanceledOnTouchOutside(true);
                            //  updateDialog.show();
                        }
                        List<AppInfoModel.DataBean.ActivityBean> noticeList = response.body().getData().getActivity();
                        if (noticeList != null && noticeList.size() > 0) {
                            AppInfoModel.DataBean.ActivityBean firstNotice = noticeList.get(0);
                            noticeDialog = new AppNoticeDialog(MainActivity.this, firstNotice.getTitle(), firstNotice.getContent(), language);
                            noticeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            noticeDialog.setCanceledOnTouchOutside(true);
                             noticeDialog.show();
                        }
                        isFirstLogin();
                    } else {
                        int error_code = response.body().getError_code();
                        Log.d("'a1a1a1a1", error_code + "");
                        ToastUtils.showShort(MainActivity.this, map.get(error_code));
                    }

                }

                @Override
                public void onFailure(Call<AppInfoModel> call, Throwable t) {
                    Log.d("zxc", t.getMessage());
                    // ToastUtils.showShort(MainActivity.this, t.getMessage());
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isFirstLogin();
        }
    }

    /**
     * 无账号登录并获取账号信息
     */
    private void account() {
        loadingView.show();
        String imei = Unique.getid(MainActivity.this);
        String mac = Unique.getMacid(MainActivity.this);
        String unique = UtilsMD5.MD5(imei + mac);
        Log.d("qqqq", "--------" + unique);
        // loadingView.show();
        Call<FirstLoginModel> accountCall = requestInterface.account(unique);
        accountCall.enqueue(new Callback<FirstLoginModel>() {
            @Override
            public void onResponse(Call<FirstLoginModel> call, Response<FirstLoginModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {

                    Log.d("ffwefewf", response.body().getData().getToken() + "");
                    //根据设备唯一码查询到没有绑定手机的账号
                    if (response.body().getData().getCreate_type() == FIRST_CREATE) {
                        isLogined = true;
                        btConnect.setClickable(true);
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        editor.putInt("diamond", response.body().getData().getDiamond());
                        editor.putString("uid", response.body().getData().getUid() + "");
                        editor.putString("account", response.body().getData().getAccount());
                        editor.putString("password", response.body().getData().getPassword() + "");
                        editor.putInt("vip_expire_date", response.body().getData().getVip_expire_time());
                        editor.putInt("vip_type", response.body().getData().getVip_type().endsWith("elite") ? 1 : 2);
                        editor.putString("token", response.body().getData().getToken());
                        editor.putString("shareCode", response.body().getData().getShare_code());
                        editor.putBoolean("mIsFirstIn", false);
                        editor.commit();
                        Log.d("ffwefewf", "00");
                        //saveSetting();
                        //更换首页背景
                        Log.d("212121", String.valueOf(mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING)));
                        changeActivitySkin(mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING));
                    } else if (response.body().getData().getCreate_type() == BING_PHONE) {
                        Intent intent = new Intent(MainActivity.this, AppLoginActivity.class);
                        startActivity(intent);
                        AppManager.getAppManager().finishActivity(MainActivity.this);
                        //finish();
                    } else {
                        isLogined = true;
                        btConnect.setClickable(true);
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        editor.putBoolean("is_binded", false);
                        editor.putInt("diamond", response.body().getData().getDiamond());
                        editor.putString("uid", String.valueOf(response.body().getData().getUid()));
                        editor.putString("account", response.body().getData().getAccount());
                        editor.putInt("vip_expire_date", response.body().getData().getVip_expire_time());
                        editor.putInt("vip_type", response.body().getData().getVip_type().equals("elite") ? 1 : 2);
                        editor.putString("token", response.body().getData().getToken());
                        editor.putString("shareCode", response.body().getData().getShare_code());
                        editor.putString("refereeCode", response.body().getData().getShare_code());
                        editor.putBoolean("mIsFirstIn", false);
                        editor.commit();
                        readSetting();
                        //更换首页背景
                        changeActivitySkin(mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING));
                        Log.d("212121", String.valueOf(mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING)));
                    }

                    /*if (response.body().getData().getCreate_type() == BING_PHONE) {
                        Intent intent = new Intent(MainActivity.this, AppLoginActivity.class);
                        startActivity(intent);
                        //AppManager.getAppManager().finishActivity(MainActivity.this);
                        //finish();
                    }*/
                    Log.d("cnma", response.body().getData().getVip_type() + mSharedPreference.getInt("vip_type", 0));

                    if (mSharedPreference.getInt("diamond", 0) > 999) {
                        tvMainDiamond.setText("999+");
                    } else {
                        tvMainDiamond.setText(mSharedPreference.getInt("diamond", 0) + "");
                    }
                    // new Thread(new Waiting()).start();
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code + "");
                    ToastUtils.showShort(MainActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<FirstLoginModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("zxc", t.getMessage());
                // ToastUtils.showShort(MainActivity.this, t.getMessage());
            }
        });
    }


    /**
     * 追加文件
     */
    public void saveSetting(File file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * vpn状态
     */
    @Override
    public void callback(int state) {
        vpnState = state;
        switch (state) {
            case State.CONNECTING:
                // btConnect.setBackgroundResource(R.mipmap.button_on);
                ///btConnect.setText("连接中");
                break;
            case State.CONNECTED:
                connect = true;
                btConnect.setVisibility(View.VISIBLE);
                if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
                    btConnect.setBackgroundResource(R.mipmap.button_on_wz);

                    ivState.setBackgroundResource(R.mipmap.huang_break);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_king_end_link);
                    }
                } else {
                    btConnect.setBackgroundResource(R.mipmap.button_on);

                    ivState.setBackgroundResource(R.mipmap.lan_break);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_elites_end_link);
                    }
                }
                //  btConnect.setBackgroundResource(R.mipmap.button_on);
                btConnect.setClickable(true);
                connectingAnim.clearAnimation();
                connectingAnim.setVisibility(View.GONE);
            case State.STOPPED:
                break;

            case State.STOPPING:
                btConnect.setClickable(true);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (isFirstInMain && isLogined) {
            isFirstInMain = false;
            Log.d("32131311", mSharedPreference.getInt("used_status", 0) + "");
            int usedStatus = mSharedPreference.getInt("used_status", 0);
            switch (usedStatus) {
                case 2:
                    if (SS_SDK.getInstance().getVPNstate() != State.CONNECTED) {
                        long current = System.currentTimeMillis() / 1000;
                        if ((int) SPUtils.get(MainActivity.this, "vip_expire_date", 0) > current) {
                            Log.d("123131312", SPUtils.get(MainActivity.this, "vip_expire_date", 0) + "------" + current);

                            initSSR();
                        }
                    }
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.remove("used_status");
                    editor.commit();
                    break;
                case 1:
                    /*if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                        SS_SDK.getInstance().switchVpn(MainActivity.this);
                    }*/
                    SharedPreferences.Editor editer = mSharedPreference.edit();
                    editer.remove("used_status");
                    editer.commit();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (verifyStoragePermissions(MainActivity.this) && isFirstInMain) {

            getAppInfo();
        }
        if (mSharedPreference.getInt("diamond", 0) > 999) {
            tvMainDiamond.setText("999+");
        } else {
            tvMainDiamond.setText(mSharedPreference.getInt("diamond", 0) + "");
        }
        if (mSharedPreference.getInt("vip_expire_date", 0) == 0) {
            tvMainData.setVisibility(View.INVISIBLE);
        } else {
            long timeStamp = Long.valueOf(mSharedPreference.getInt("vip_expire_date", 1));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sd = sdf.format(new Date(timeStamp * 1000L));
            tvMainData.setVisibility(View.VISIBLE);
            tvMainData.setText(getString(R.string.Deadline) + sd);

        }


            if (mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING) == VIP_TYPE_WANGZHE) {
                changeActivitySkin(VIP_TYPE_WANGZHE);
            } else {
                changeActivitySkin(VIP_TYPE_JINGYING);
            }


        //hideBottomUIMenu();
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

    @SuppressLint("SetTextI18n")
    private void changeActivitySkin(int skinType) {
        if (skinType == VIP_TYPE_WANGZHE) {
            Log.d("wz", "1111111");
            llMain.setBackgroundResource(R.mipmap.bg_wz);
            llMenuService.setBackgroundResource(R.mipmap.lan_wz);
            ivMainXuanze.setImageResource(R.mipmap.xuanze_wz);
            tvMainDiamond.setBackgroundResource(R.mipmap.king_user_diamondbg);
            llMianTab.setText(R.string.current_service_king);
            llMianTab.setTextColor(Color.parseColor("#9E6101"));
            flBtn.setBackgroundResource(R.mipmap.btn_wz_bg);
            connectingAnim.setBackgroundResource(R.mipmap.btn_wz_mid);
            tvMainData.setTextColor(Color.parseColor("#9E6101"));
        } else {
            llMain.setBackgroundResource(R.mipmap.bg);
            llMenuService.setBackgroundResource(R.mipmap.lan);
            ivMainXuanze.setImageResource(R.mipmap.btn_icon2);
            tvMainDiamond.setBackgroundResource(R.mipmap.elites_user_diamondbg);
            llMianTab.setText(R.string.current_service_elite);
            llMianTab.setTextColor(Color.parseColor("#ffffff"));
            flBtn.setBackgroundResource(R.mipmap.btn_jy_bg);
            connectingAnim.setBackgroundResource(R.mipmap.btn_jy_mid);
            tvMainData.setTextColor(Color.parseColor("#ffffff"));
        }
        if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
            btConnect.setVisibility(View.VISIBLE);
            if (skinType == VIP_TYPE_WANGZHE) {
                btConnect.setBackgroundResource(R.mipmap.button_on_wz);

                ivState.setBackgroundResource(R.mipmap.huang_break);
                if (language.equals("en")) {
                    ivState.setBackgroundResource(R.mipmap.en_king_end_link);
                }
            } else {
                btConnect.setBackgroundResource(R.mipmap.button_on);

                ivState.setBackgroundResource(R.mipmap.lan_break);
                if (language.equals("en")) {
                    ivState.setBackgroundResource(R.mipmap.en_elites_end_link);
                }
            }
            connectingAnim.setVisibility(View.GONE);
            connectingAnim.clearAnimation();
        } else {
            if (skinType == VIP_TYPE_WANGZHE) {
                if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                    btConnect.setBackgroundResource(R.mipmap.button_on_wz);

                    ivState.setBackgroundResource(R.mipmap.huang_break);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_king_end_link);
                    }
                } else if (SS_SDK.getInstance().getVPNstate() == State.CONNECTING) {

                    ivState.setBackgroundResource(R.mipmap.huang_conneting);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_king_linking);
                    }
                } else {

                    btConnect.setBackgroundResource(R.mipmap.button_wz);

                    ivState.setBackgroundResource(R.mipmap.huang_connet);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_king_start_link);
                    }
                }
            } else {
                if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                    btConnect.setBackgroundResource(R.mipmap.button_on);

                    ivState.setBackgroundResource(R.mipmap.lan_break);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_elites_end_link);
                    }
                } else if (SS_SDK.getInstance().getVPNstate() == State.CONNECTING) {

                    ivState.setBackgroundResource(R.mipmap.lan_connecting);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_elites_linking);
                    }
                } else {
                    btConnect.setBackgroundResource(R.mipmap.btn_icon1);

                    ivState.setBackgroundResource(R.mipmap.lan_connet);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_elites_start_link);
                    }
                }
            }
            //connectingAnim.setVisibility(View.GONE);
            //connectingAnim.clearAnimation();
        }
        if (mSharedPreference.getInt("vip_expire_date", 0) == 0) {
            tvMainData.setVisibility(View.INVISIBLE);
        } else {
            long timeStamp = Long.valueOf(mSharedPreference.getInt("vip_expire_date", 1));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sd = sdf.format(new Date(timeStamp * 1000L));
            tvMainData.setVisibility(View.VISIBLE);
            tvMainData.setText(getString(R.string.Deadline) + sd);

        }
        if (mSharedPreference.getInt("diamond", 0) > 999) {
            tvMainDiamond.setText("999+");
        } else {
            tvMainDiamond.setText(mSharedPreference.getInt("diamond", 0) + "");
        }
        llMain.setVisibility(View.VISIBLE);
    }

    /**
     * 网络状态
     */
    @Override
    public void onChangeListener(int netMobile) {
        netType = netMobile;
        if (!isNetConnect()) {
            Log.d("789654", "asd" + netMobile + "");

            btConnect.setClickable(false);
        } else {
            Log.d("789654", "asd" + netMobile + "");

            btConnect.setClickable(true);
        }
    }

    /**
     * 切换服务回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dqweqwdqw", resultCode + "");
        if (resultCode == 2) {
            /*int serviceId = data.getIntExtra("type",0);
            if (SS_SDK.getInstance().getVPNstate() == State.CONNECTED) {
                SS_SDK.getInstance().switchVpn(MainActivity.this);
            }*/
            if (connect) {
                socket = true;
            } else {
                socket = false;
            }

            long current = System.currentTimeMillis() / 1000;
            if ((int) SPUtils.get(MainActivity.this, "diamond", 0) > 0) {
                initSSR();
            }
        }
        //}
    }

    /**
     * 读取账号密码
     */
    public void readSetting() {
        //开启软件设置之前的设置
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        String statu = Environment.getExternalStorageState();
        if (!statu.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("09u09u0", "SD卡未就绪");
            Toast.makeText(this, "SD卡未就绪", Toast.LENGTH_SHORT).show();
            return;
        }
        File root = Environment.getExternalStorageDirectory();
        Log.d("09u09u0", Environment.getExternalStorageDirectory() + "");
        try {
            Log.d("09u09u0", "11111");
            fis = new FileInputStream(root + "/setting.txt");
            ois = new ObjectInputStream(fis);
            try {
                loginInfo = (LoginInfo) ois.readObject();
                Log.d("09u09u0", loginInfo.getPassword());
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "未找到文件", Toast.LENGTH_SHORT).show();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {

            }
        }


    }

    /**
     * 断开连接
     */
    private void disconnect() {
        loadingView.show();

        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Call<Disconnect> disconnectCall = requestInterface.disconnect(authorization);
        disconnectCall.enqueue(new Callback<Disconnect>() {

            @Override
            public void onResponse(Call<Disconnect> call, Response<Disconnect> response) {
                loadingView.dismiss();
                btConnect.setClickable(true);
                SS_SDK.getInstance().switchVpn(MainActivity.this);
                socket = false;
                if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
                    btConnect.setBackgroundResource(R.mipmap.button_wz);

                    ivState.setBackgroundResource(R.mipmap.huang_connet);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_king_start_link);
                    }
                } else {
                    btConnect.setBackgroundResource(R.mipmap.btn_icon1);

                    ivState.setBackgroundResource(R.mipmap.lan_connet);
                    if (language.equals("en")) {
                        ivState.setBackgroundResource(R.mipmap.en_elites_start_link);
                    }
                }
                connect = false;

            }

            @Override
            public void onFailure(Call<Disconnect> call, Throwable t) {
                loadingView.dismiss();
                btConnect.setClickable(true);
                Toast.makeText(MainActivity.this, "网络异常请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取权限
     */
    private boolean verifyStoragePermissions(Activity activity) {

        if (Build.VERSION.SDK_INT < 23) /*******below android 6.0*******/ {
            return true;
        }

        // Check if we have write permission
        int permissionRead = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionState = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permissionRead != PackageManager.PERMISSION_GRANTED || permissionState != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            //     login();
            return true;
        }
    }

    private void getUserInfo() {
        loadingView.show();
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Log.d("asdasdas", authorization);
        final Call<Info> infoCall = requestInterface.getInfo(authorization);
        infoCall.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                loadingView.dismiss();
                SharedPreferences.Editor editor = mSharedPreference.edit();
                Info.DataBean userInfo = response.body().getData();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    Log.d("32131311", "success");
                    isLogined = true;
                    btConnect.setClickable(true);
                    editor.putInt("uid", userInfo.getUid());
                    editor.putString("account", userInfo.getAccount());
                    editor.putInt("used_status", userInfo.getUsed_status());
                    editor.putInt("vip_expire_date", userInfo.getVip_expire_time());
                    editor.putInt("vip_type", userInfo.getVip_type().equals("elite") ? 1 : 2);
                    editor.putBoolean("is_binded", userInfo.isIs_bind());
                    editor.putInt("diamond", userInfo.getDiamond());
                    editor.putString("shareCode", userInfo.getShare_code());
                    editor.putString("refereeCode", userInfo.getReferee_code());
                    editor.putInt("has_tested", userInfo.getIs_tested());
                    editor.commit();
                    Log.d("jojoij", mSharedPreference.getInt("used_status", 0) + "-------");
                    Log.d("vip_type", (userInfo.getVip_type().equals("elite") ? 1 : 2) + "");
                    //服务器端vpn 已连接  状态为2:；1为未连接
                    if (userInfo.getVip_type().equals("elite")) {
                        changeActivitySkin(VIP_TYPE_JINGYING);

                    } else {
                        changeActivitySkin(VIP_TYPE_WANGZHE);
                    }
                    if (userInfo.getUsed_status() == 2 && SS_SDK.getInstance().getVPNstate() != State.CONNECTED) {
                        int vip_type = userInfo.getVip_type().equals("elite") ? 1 : 2;
                        if (mSharedPreference.getInt("vpn_type", 0) == vip_type && !mSharedPreference.getString("vpn_server_info", "").isEmpty()) {
                            connectVpnService(mSharedPreference.getString("vpn_server_info", ""));
                        }
                    }
                } else {
                    int error_code = response.body().getError_code();
                    Log.d("'32131311", error_code + "");
                    ToastUtils.showShort(MainActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.d("32131311", "fail" + t.toString());
                loadingView.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
