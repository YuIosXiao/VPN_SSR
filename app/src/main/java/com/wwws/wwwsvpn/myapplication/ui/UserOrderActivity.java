package com.wwws.wwwsvpn.myapplication.ui;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.adapter.OrderItemAdapter;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.OrderListModel;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.utils.ToastUtils;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2018/5/25.
 */

public class UserOrderActivity  extends BaseActivity implements AbsListView.OnScrollListener{
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private final static int REQ_SUCCESS = 1;
    @BindView(R.id.tb_userorder)
    TopBar tbUserOrder;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.ll_userorder)
    LinearLayout llUserOrder;
    public View loadmoreView;
    public LinearLayout loadingMoreView;
    public TextView noMoreView;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.empty_tips)
    LinearLayout emptyView;

    OrderItemAdapter orderItemAdapter;
    private int currPage = 1;
    private int totalPage = 1;
    String authorization;
    //public LoadingDialog loadingView;
    public int last_index;
    public int total_index;
    public boolean isLoading = false;//表示是否正处于加载状态
    public boolean canGetMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userorder;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        tbUserOrder.setBackClickListener(this);
        loadmoreView = LayoutInflater.from(this).inflate(R.layout.load_more, null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的
        loadingMoreView = (LinearLayout) loadmoreView.findViewById(R.id.loadmore_loading);
        noMoreView = (TextView) loadmoreView.findViewById(R.id.no_more_tips);
        listView.addFooterView(loadmoreView);
        listView.setOnScrollListener(this);
    }

    @Override
    protected void initData() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        authorization = "Bearer " + mSharedPreference.getString("token", "");
        currPage = 1;
        getOrderListByPage();
        if (mSharedPreference.getInt("vip_type", VIP_TYPE_JINGYING) == VIP_TYPE_WANGZHE) {
            tbUserOrder.setBackgroundResource(R.drawable.shape_topbar_wz);
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

    private void getOrderListByPage(){
        loadingView.show();

        Call<OrderListModel> stringCall = requestInterface.orderList(authorization, language, Integer.toString(currPage));
        stringCall.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                loadingView.dismiss();
                if (response.body().getStatus() == REQ_SUCCESS) {
                    totalPage = response.body().getData().getTotalPage();
                    if (currPage == 1){
                        if (response.body().getData().getList().size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            listView.removeFooterView(loadmoreView);
                            canGetMore = false;
                        } else {
                            orderItemAdapter = new OrderItemAdapter(response.body().getData().getList(),UserOrderActivity.this,language);
                            listView.setAdapter(orderItemAdapter);
                            if (currPage < totalPage && response.body().getData().getList().size() == 10) {
                                currPage++;
                                canGetMore = true;
                            }else {
                                canGetMore = false;
                                loadingMoreView.setVisibility(View.GONE);
                                noMoreView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else{
                        orderItemAdapter.addOrderItem(response.body().getData().getList());
                        if (currPage < totalPage) {
                            currPage++;
                            canGetMore = true;
                        }else {
                            canGetMore = false;
                            loadingMoreView.setVisibility(View.GONE);
                            noMoreView.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    int error_code = response.body().getError_code();
                    Log.d("'a1a1a1a1", error_code+"");
                    Log.d("'13161651", language);
                    ToastUtils.showShort(UserOrderActivity.this, map.get(error_code));
                }
                hideBottomUIMenu();
                isLoading = false;
            }

            @Override
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                loadingView.dismiss();
                Log.d("records123", t.getMessage());
                emptyView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last_index = firstVisibleItem+visibleItemCount;
        total_index = totalItemCount;
        System.out.println("last:  "+last_index);
        System.out.println("total:  "+total_index);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
        {
            //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
            if(!isLoading && canGetMore)
            {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                loadmoreView.setVisibility(View.VISIBLE);
                loadingMoreView.setVisibility(View.VISIBLE);
                noMoreView.setVisibility(View.GONE);
                getOrderListByPage();
            }
        }
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(UserOrderActivity.this);
    }
}
