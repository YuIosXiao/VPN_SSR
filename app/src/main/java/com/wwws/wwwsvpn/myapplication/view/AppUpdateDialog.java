package com.wwws.wwwsvpn.myapplication.view;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.SignupModel;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.ui.BindingActivity;
import com.wwws.wwwsvpn.myapplication.ui.SettingActivity;
import com.wwws.wwwsvpn.myapplication.utils.DownAPKService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


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


public class AppUpdateDialog extends Dialog {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private Context mContext;

    private ImageView iv_cancel;

    private String updateTitle;
    private String updateContent;
    private String updateUrl;

    public AppUpdateDialog(@NonNull Context context,@NonNull String title,@NonNull String content,@NonNull String appUrl) {
        super(context, R.style.MyDialog);
        mContext = context;
        updateTitle = title;
        updateContent = content;
        updateUrl = appUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_appupdate);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        getWindow().getAttributes().gravity = Gravity.CENTER;

        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        TextView tvUpdateTitle = (TextView) findViewById(R.id.update_title);
        TextView tvUpdateContent = (TextView) findViewById(R.id.update_content);
        tvUpdateTitle.setText(updateTitle);
        tvUpdateContent.setText(updateContent);
        ((TextView) findViewById(R.id.cancel_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        ((TextView) findViewById(R.id.start_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DownAPKService.class);
                intent.putExtra("apk_url",updateUrl);
                mContext.startService(intent);
                cancel();
            }
        });
    }

    @OnClick({R.id.cancel_update, R.id.start_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_update:

                break;
            case R.id.start_update:

                break;
        }
    }


}
