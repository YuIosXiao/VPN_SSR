package com.wwws.wwwsvpn.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.OrderListModel;
import com.wwws.wwwsvpn.myapplication.model.QuestionModel;

import java.util.List;

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


public class OrderItemAdapter extends BaseAdapter {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private String language;
    private List<OrderListModel.DataBean.ListBean> list;
    private LayoutInflater inflater;
    private SharedPreferences mSharedPreference;
    public OrderItemAdapter(List<OrderListModel.DataBean.ListBean> list, Context context,String language) {
        this.language = language;
        this.list = list;
        this.inflater=LayoutInflater.from(context);
        mSharedPreference = context.getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    public void addOrderItem(List<OrderListModel.DataBean.ListBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public OrderListModel.DataBean.ListBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            view = inflater.inflate(R.layout.item_orderinfo,null);
            holder = new ViewHolder();
            holder.createtime = (TextView) view.findViewById(R.id.tv_item_createtime);
            holder.orderState = (TextView) view.findViewById(R.id.tv_item_state);
            holder.orderInfo = (TextView) view.findViewById(R.id.tv_item_orderinfo);
            holder.orderPrice = (TextView) view.findViewById(R.id.tv_item_orderprice);
            holder.cancelImage = (ImageView) view.findViewById(R.id.tv_item_ordercancel);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        OrderListModel.DataBean.ListBean orderItem = getItem(i);
        holder.createtime.setText(orderItem.getCreate_time());
        holder.orderInfo.setText(String.format("订单编号：%s\n套餐类型：%s", orderItem.getOrder_id(), orderItem.getMeal_title()));
        holder.orderPrice.setText(Html.fromHtml(String.format("金额：<font color='#ff0000'>¥%s</font>",orderItem.getPrice())));
        if(language.equals("en")){
            holder.orderInfo.setText(String.format("Order number：%s\nType ：%s", orderItem.getOrder_id(), orderItem.getMeal_title()));
            holder.orderPrice.setText(Html.fromHtml(String.format("Amount：<font color='#ff0000'>¥%s</font>",orderItem.getPrice())));
            holder.cancelImage.setImageResource(R.mipmap.en_order_status_complate);
        }

        if ("已完成".equals(orderItem.getStatus())){
            holder.orderState.setVisibility(View.GONE);
            holder.cancelImage.setVisibility(View.VISIBLE);
        } else {
            holder.orderState.setVisibility(View.VISIBLE);
            holder.cancelImage.setVisibility(View.GONE);
        }
        return view;
    }

    static class ViewHolder{
        TextView    createtime;
        TextView    orderState;
        TextView    orderInfo;
        TextView    orderPrice;
        ImageView    cancelImage;
    }
}
