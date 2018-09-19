package com.wwws.wwwsvpn.myapplication.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.wwws.wwwsvpn.myapplication.BuildConfig;
import com.wwws.wwwsvpn.myapplication.R;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by admin on 2018/6/4.
 */

public class DownAPKService extends Service {

    private static final String TAG = "DownAPKService";
    private final int NotificationID = 0x10000;
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

    private DownloadUtil downloadUtil;

    // 文件下载路径
    private String APK_url = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        // 接收Intent传来的参数:
        APK_url = intent.getStringExtra("apk_url");
        DownFile(APK_url);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     *
     * @Description:判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void DownFile(String file_url) {
        downloadUtil = new DownloadUtil();
        downloadUtil.downloadFile(file_url, new DownloadListener() {
            @Override
            public void onStart() {
                Log.e(TAG, "onStart: ");
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setTicker("正在下载新版本");
                builder.setContentTitle(getApplicationName());
                builder.setContentText("正在下载,请稍后...");
                builder.setNumber(0);
                builder.setAutoCancel(true);
                mNotificationManager.notify(NotificationID, builder.build());
            }

            @Override
            public void onProgress(final int currentLength) {
                Log.e(TAG, "onLoading: " + currentLength);
                builder.setProgress(100,currentLength,false);
                mNotificationManager.notify(NotificationID, builder.build());

            }

            @Override
            public void onFinish(final String localPath) {
                Log.e(TAG, "onFinish: " + localPath);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(DownAPKService.this, "com.wwws.wwwsvpn.myapplication.fileProvider", new File(localPath));
                    installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    installIntent.setDataAndType(Uri.fromFile(new File(localPath)), "application/vnd.android.package-archive");
                }

                /*Intent installIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(new File(localPath));
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                PendingIntent mPendingIntent = PendingIntent.getActivity(DownAPKService.this, 0, installIntent, 0);
                if (builder != null){
                    builder.setContentText("下载完成,请点击安装");
                    builder.setContentIntent(mPendingIntent);
                    mNotificationManager.notify(NotificationID, builder.build());
                }
                stopSelf();
                startActivity(installIntent);// 下载完成之后自动弹出安装界面
                if (builder != null) {
                    mNotificationManager.cancel(NotificationID);
                }
            }

            @Override
            public void onFailure(final String erroInfo) {
                Log.e(TAG, "onFailure: " + erroInfo);
                mNotificationManager.cancel(NotificationID);
                Toast.makeText(getApplicationContext(), "下载失败，请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @return
     * @Description:获取当前应用的名称
     */
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
