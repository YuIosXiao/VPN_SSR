package com.wwws.wwwsvpn.myapplication;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.github.shadowsocks.utils.SS_SDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.Locale;


/**
 * Created by victor on 2017/4/12.
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SS_SDK.init(this);
        UMConfigure.init(this,"5b0d09c4f43e48674a000119","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");


    }

}
