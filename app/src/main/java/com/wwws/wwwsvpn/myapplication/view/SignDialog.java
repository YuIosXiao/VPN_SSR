package com.wwws.wwwsvpn.myapplication.view;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wwws.wwwsvpn.myapplication.MyApplication;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.SSRModel;
import com.wwws.wwwsvpn.myapplication.model.SignupModel;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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


public class SignDialog extends Dialog {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    @BindView(R.id.tv_sign_date)
    TextView tvSignDate;
    @BindView(R.id.fl_top)
    FrameLayout flTop;
    @BindView(R.id.iv_middle)
    ImageView ivMiddle;
    private Context mContext;
    private Retrofit retrofit;
    private RequestInterface requestInterface;
    private SharedPreferences mSharedPreference;
    private ImageView iv_cancel;
    private CalendarView calendarView;
    private String langage;
    private LoadingDialog loadingView;

    public SignDialog(@NonNull Context context , LoadingDialog loadingView,String langage) {
        super(context, R.style.MyDialog);
        mContext = context;
        this.loadingView = loadingView;
        this.langage = langage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign);
        ButterKnife.bind(this);
        mSharedPreference = mContext.getSharedPreferences("config", MODE_PRIVATE);
        Resources resources = mContext.getResources();

        //
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dev-accelerate.te6-api.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestInterface = retrofit.create(RequestInterface.class);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvSignDate.setText(dateFormat.format(date));
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            ivMiddle.setBackgroundResource(R.mipmap.middle);
            if (langage.equals("en")){
                flTop.setBackgroundResource(R.mipmap.en_king_sign_header);
            }
            flTop.setBackgroundResource(R.mipmap.en_king_sign_header);
            calendarView.setSelectDayBackground(resources.getDrawable(R.mipmap.sign_date_wz));
            tvSignDate.setTextColor(Color.parseColor("#905700"));
        }else{
            if (langage.equals("en")){
                flTop.setBackgroundResource(R.mipmap.en_elites_sign_header);
            }
            calendarView.setSelectDayBackground(resources.getDrawable(R.mipmap.sign_date_jy));
        }
        sign();


        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    /**
     * 签到
     */
    private void sign() {
        String authorization = "Bearer " + mSharedPreference.getString("token", "");
        Log.d("wsnbb", authorization);
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://global-accelerate.te6-api.net/")
                .addConverterFactory(ScalarsConverterFactory.create()) //设置数据解析器
                .build();
        final RequestInterface requestInterface1 = retrofit1.create(RequestInterface.class);

        Call<String> taocanCall = requestInterface1.signup(authorization);
        taocanCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loadingView.dismiss();
                Gson gson = new Gson();
                String signUpModelInfo = response.body();
                SignupModel signupModel = gson.fromJson(signUpModelInfo, SignupModel.class);
                if (signupModel.getStatus() == 1) {
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putInt("vip_expire_date", signupModel.getData().getExpireAt());
                    editor.commit();
                    List<String> list = signupModel.getData().getDays();
                    List<String> data = new ArrayList();
                    // 这里的日期格式可以通过 setDateFormatPattern() 方法设置，默认是 yyyyMMdd
                    for (String s : list) {
                        data.add(s);
                        Log.d("sinlist", s);
                    }
                    calendarView.setSelectDate(data);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadingView.dismiss();
            }
        });


      /*
       Call<SignupModel> signupCall = requestInterface.signup(authorization);
        signupCall.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                Log.d("signup", response.message());
                loadingView.dismiss();
                if (response.body().getStatus() == 1){
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putInt("vip_expire_date", response.body().getData().getExpireAt());
                    editor.commit();
                    List<String> list = response.body().getData().getDays();
                    List<String> data = new ArrayList();
                    // 这里的日期格式可以通过 setDateFormatPattern() 方法设置，默认是 yyyyMMdd
                    for (String s : list) {
                        data.add(s);
                        Log.d("sinlist", s);
                    }
                    calendarView.setSelectDate(data);
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                loadingView.dismiss();
            }
        });*/
    }


}
