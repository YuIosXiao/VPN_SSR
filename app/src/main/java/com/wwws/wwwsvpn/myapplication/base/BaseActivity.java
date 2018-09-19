package com.wwws.wwwsvpn.myapplication.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wwws.wwwsvpn.myapplication.MyApplication;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.utils.AndroidWorkaround;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.NetBroadcastReceiver;
import com.wwws.wwwsvpn.myapplication.utils.NetUtil;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.LoadingDialog;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public abstract class BaseActivity extends Activity implements TopBar.OnBackClickListener,NetBroadcastReceiver.NetChangeListener {
    public final static int KING_USER = 2;
    public final static int ELITES_USER = 1;
    public   Map<Integer,String> map = new HashMap<>();

    /**
     * 网络类型
     */
    public int netType;
    public Retrofit retrofit;
    public String language;
    public RequestInterface requestInterface;
    public SharedPreferences mSharedPreference;
    public LoadingDialog loadingView;
    public static NetBroadcastReceiver.NetChangeListener listener;
    private NetBroadcastReceiver netBroadcastReceiver;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        map.put(20001,getString(R.string.a20001));
        map.put(20002,getString(R.string.a20002));
        map.put(20003,getString(R.string.a20003));
        map.put(20004,getString(R.string.a20004));
        map.put(20005,getString(R.string.a20005));
        map.put(20006,getString(R.string.a20006));
        map.put(20007,getString(R.string.a20007));
        map.put(20008,getString(R.string.a20008));
        map.put(20009,getString(R.string.a20009));
        map.put(20010,getString(R.string.a20010));
        map.put(20011,getString(R.string.a20011));
        map.put(20012,getString(R.string.a20012));
        map.put(20013,getString(R.string.a20013));
        map.put(20014,getString(R.string.a20014));
        map.put(20015,getString(R.string.a20015));
        map.put(20016,getString(R.string.a20016));
        map.put(20017,getString(R.string.a20017));
        map.put(20018,getString(R.string.a20018));
        map.put(20019,getString(R.string.a20019));
        map.put(20020,getString(R.string.a20020));
        map.put(20021,getString(R.string.a20021));
        map.put(20022,getString(R.string.a20022));
        map.put(20023,getString(R.string.a20023));
        map.put(20024,getString(R.string.a20024));
        map.put(20025,getString(R.string.a20025));
        map.put(20026,getString(R.string.a20026));
        map.put(20027,getString(R.string.a20027));
        map.put(20028,getString(R.string.a20028));
        map.put(20029,getString(R.string.a20029));
        map.put(20030,getString(R.string.a20030));
        map.put(20031,getString(R.string.a20031));
        map.put(20032,getString(R.string.a20032));
        map.put(20033,getString(R.string.a20033));
        map.put(20034,getString(R.string.a20034));
        map.put(20035,getString(R.string.a20035));
        map.put(20036,getString(R.string.a20036));
        map.put(20037,getString(R.string.a20037));
        map.put(20038,getString(R.string.a20038));
        map.put(20039,getString(R.string.a20039));
        map.put(20040,getString(R.string.a20040));
        map.put(20041,getString(R.string.a20041));
        map.put(20042,getString(R.string.a20042));
        map.put(20043,getString(R.string.a20043));
        map.put(20044,getString(R.string.a20044));
        map.put(20045,getString(R.string.a20045));
        map.put(20046,getString(R.string.a20046));
        map.put(20047,getString(R.string.a20047));
        map.put(20048,getString(R.string.a20048));
        map.put(20049,getString(R.string.a20049));
        map.put(20050,getString(R.string.a20050));
        map.put(20051,getString(R.string.a20051));
        map.put(20052,getString(R.string.a20052));
        map.put(20053,getString(R.string.a20053));
        map.put(20054,getString(R.string.a20054));
        map.put(20055,getString(R.string.a20055));
        map.put(20056,getString(R.string.a20056));
        map.put(20057,getString(R.string.a20057));
        map.put(20058,getString(R.string.a20058));
        map.put(20059,getString(R.string.a20059));
        map.put(20060,getString(R.string.a20060));

        map.put(20061,getString(R.string.a20061));
        map.put(30001,getString(R.string.a20062));
        map.put(30002,getString(R.string.a20062));
        map.put(30003,getString(R.string.a20062));
        map.put(30004,getString(R.string.a20062));
        map.put(40001,getString(R.string.a20062));

        mSharedPreference = getSharedPreferences("config", MODE_PRIVATE);
        language = Locale.getDefault().toString();
      if(language.substring(0,2).equals("en")){
            language = "en";
        }else{
            language = "zh-CN";
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://global-accelerate.te6-api.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestInterface = retrofit.create(RequestInterface.class);
        loadingView = new LoadingDialog.Builder(this)
                .setMessage(getString(R.string.loading_text))
                .setCancelable(false)
                .create();
        listener = this;
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            registerReceiver(netBroadcastReceiver, filter);
        }
        checkNet();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     *网络变化之后的类型
     */
    @Override
    public void onChangeListener(int netMobile) {
        this.netType = netMobile;
        if (!isNetConnect()) {
            ToastUtils.showShort(this,"网络异常，请检查网络");
        } else {
           // ToastUtils.showShort(this,"网络已连接");
        }

    }
    /**
     * 初始化时判断有没有网络
     */
    public boolean checkNet() {
        this.netType = NetUtil.getNetWorkState(BaseActivity.this);
        if (!isNetConnect()) {
            //网络异常，请检查网络
            ToastUtils.showShort(this,getString(R.string.net_error));
        }
        return isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netType == 1) {
            return true;
        } else if (netType == 0) {
            return true;
        } else if (netType == -1) {
            return false;
        }
        return false;
    }

    protected abstract void backUpActivity();

    @Override
    public void OnBackButtonClick() {
        backUpActivity();
        if (null != netBroadcastReceiver){
            unregisterReceiver(netBroadcastReceiver);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            backUpActivity();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
