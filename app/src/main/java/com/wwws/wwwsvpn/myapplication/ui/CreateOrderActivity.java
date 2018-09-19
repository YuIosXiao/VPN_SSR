package com.wwws.wwwsvpn.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.GetStatus;
import com.wwws.wwwsvpn.myapplication.model.Info;
import com.wwws.wwwsvpn.myapplication.model.LoginInfo;
import com.wwws.wwwsvpn.myapplication.model.OrderModel;
import com.wwws.wwwsvpn.myapplication.model.TestPaySuccess;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.VerifyModel;
import com.wwws.wwwsvpn.myapplication.net.RequestInterface;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.PayResult;
import com.wwws.wwwsvpn.myapplication.utils.SPUtils;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/5/19.
 */

public class CreateOrderActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;

    @BindView(R.id.tb_createorder)
    TopBar tbCreateOrder;
    @BindView(R.id.ll_createorder)
    LinearLayout llCreateOrder;
    @BindView(R.id.iv_state)
    ImageView ivState;

    @BindView(R.id.order_type)
    TextView tvOrderType;
    @BindView(R.id.order_createtime)
    TextView tvOrderCreatetime;
    @BindView(R.id.order_price)
    TextView tvOrderPrice;

    @BindView(R.id.order_info_icon)
    ImageView ivOrderInfoIcon;
    @BindView(R.id.order_pay_icon)
    ImageView ivOrderPayIcon;

    @BindView(R.id.wx_pay_item)
    LinearLayout llWXPayItem;
    @BindView(R.id.ali_pay_item)
    LinearLayout llAliPayItem;

    @BindView(R.id.wx_pay_sel)
    ImageView ivWXPayIcon;
    @BindView(R.id.ali_pay_sel)
    ImageView ivAliPayIcon;

    @BindView(R.id.bt_pay_order)
    Button btPayOrder;

    private String mealId;
    private String mealType;
    private String mealPrice;
    private String mealName;

    private String order_id;
    private boolean isAliPay = false;

    private final static int SDK_PAY_FLAG = 1;
    Handler mHandler1 = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_neworder;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbCreateOrder.setBackClickListener(this);
    }

    @Override
    protected void initData() {
        ivAliPayIcon.setImageResource(R.mipmap.item_seled_icon);
        mealId = getIntent().getStringExtra("mealId");
        mealType= getIntent().getStringExtra("mealType");
        mealPrice = getIntent().getStringExtra("mealPrice");
        mealName = getIntent().getStringExtra("mealName");

        tvOrderType.setText(String.format(getString(R.string.type),mealName));
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        tvOrderCreatetime.setText(String.format(getString(R.string.Purchase_time),format.format(new Date())));
        tvOrderPrice.setText(Html.fromHtml(String.format(getString(R.string.Amount),mealPrice)));
        AppManager.getAppManager().addActivity(this);
        mHandler1.postDelayed(r, 100);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(CreateOrderActivity.this);
    }

    @OnClick({R.id.bt_pay_order,R.id.ali_pay_item,R.id.wx_pay_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_pay_order:
                createOrder();
               // ToastUtils.showShort(this,"第四方支付");
                break;
            case R.id.ali_pay_item:
               // ivAliPayIcon.setImageResource(R.mipmap.item_seled_icon);
               // ivWXPayIcon.setImageResource(R.mipmap.item_nosel_icon);
                isAliPay = true;
                break;
            case R.id.wx_pay_item:
                ivAliPayIcon.setImageResource(R.mipmap.item_nosel_icon);
                ivWXPayIcon.setImageResource(R.mipmap.item_seled_icon);
                isAliPay = false;
                break;
        }
    }

    private void createOrder() {
        loadingView.show();
        String authorization = "Bearer "+mSharedPreference.getString("token","");

        //int type  = Integer.parseInt(mealType)+1;
        //Log.d("987456","------"+ type);
        String payType = isAliPay ? "alipay" : "wechat";
        Call<OrderModel> orderModelCall = requestInterface.createOrder(authorization,"alipay",mealId+"");
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.body().getStatus() == REQ_SUCCESS) {
                    SPUtils.put(CreateOrderActivity.this,"order_id",response.body().getData().getOrder_id());
                    Uri uri = Uri.parse(response.body().getData().getPay_link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                   // finish();
                    loadingView.dismiss();
                }else {
                    loadingView.dismiss();
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    ToastUtils.showShort(CreateOrderActivity.this, map.get(error_code));
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                loadingView.dismiss();
                AppManager.getAppManager().finishActivity(RechargeActivity.class);
                AppManager.getAppManager().finishActivity(CreateOrderActivity.class);
            }
        });
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            getStatus();
            mHandler1.postDelayed(this, 5000);
        }
    };

    private void getStatus(){
        String authorization = "Bearer "+mSharedPreference.getString("token","");
        String order_id = (String) SPUtils.get(CreateOrderActivity.this,"order_id","");
        Call<GetStatus> orderModelCall = requestInterface.getStatus(authorization,order_id);
        orderModelCall.enqueue(new Callback<GetStatus>() {
            @Override
            public void onResponse(Call<GetStatus> call, Response<GetStatus> response) {
                if (response.body().getStatus() == REQ_SUCCESS) {
                    if(response.body().getData().getStatus().equals("completed")){
                      //  ToastUtils.showShort(CreateOrderActivity.this,"支付成功");
                    }else{
                        int error_code = response.body().getError_code();
                        Log.d("'a1a1a1a1", error_code+"");
                      //  ToastUtils.showShort(CreateOrderActivity.this, map.get(error_code));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetStatus> call, Throwable t) {

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbCreateOrder.setBackgroundResource(R.drawable.shape_topbar_wz);
            llCreateOrder.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
            ivOrderInfoIcon.setBackgroundResource(R.mipmap.king_order_title);
            ivOrderPayIcon.setBackgroundResource(R.mipmap.king_order_paytype);
            btPayOrder.setBackgroundResource(R.mipmap.submit);
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(CreateOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(CreateOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };




}
