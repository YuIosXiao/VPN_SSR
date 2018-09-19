package com.wwws.wwwsvpn.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.TaocanModel;
import com.wwws.wwwsvpn.myapplication.ui.CreateOrderActivity;
import com.wwws.wwwsvpn.myapplication.ui.ModifyPWDActivity;
import com.wwws.wwwsvpn.myapplication.ui.SettingActivity;

import java.text.DecimalFormat;
import java.util.List;

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


public class RechargeTaocanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int type_diamond = 1;
    public static final int type_monthly = 2;
    private List<TaocanModel.DataBean> taocanListBean;
    private int type;//用于判断充值类型
    private Context context;
    private DecimalFormat df = new DecimalFormat("######0.00");
    private String language;
    static class MonthlyViewHolder extends RecyclerView.ViewHolder {

        TextView monthly_name;
        TextView monthly_newPrice;
        TextView monthly_oldPrice;
        LinearLayout linearLayout;

        public MonthlyViewHolder(View view) {
            super(view);
            monthly_name = (TextView) view.findViewById(R.id.tv_monthly_name);
            monthly_newPrice = (TextView) view.findViewById(R.id.tv_monthly_new_price);
            monthly_oldPrice = (TextView) view.findViewById(R.id.tv_monthly_old_price);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_monthly);
        }
    }

    static class DiamondViewHolder extends RecyclerView.ViewHolder {


        TextView diamond_newPrice;
        LinearLayout linearLayout;

        public DiamondViewHolder(View view) {
            super(view);
            diamond_newPrice = (TextView) view.findViewById(R.id.tv_diamond_new_price);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_item_diamond);
        }
    }


    public RechargeTaocanAdapter(List<TaocanModel.DataBean> taocanListBean, Context context, int type,String language) {
        this.context = context;
        this.taocanListBean = taocanListBean;
        this.language = language;
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case type_diamond:
                View diamondView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_diamond, parent, false);
                DiamondViewHolder diamodViewHolder = new DiamondViewHolder(diamondView);
                return diamodViewHolder;
            default:
                View MonthlyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_monthly, parent, false);
                MonthlyViewHolder monthlyViewHolder = new MonthlyViewHolder(MonthlyView);
                return monthlyViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final List<TaocanModel.DataBean.MealBean> taocanBean = taocanListBean.get(type).getMeal();
        if (holder instanceof DiamondViewHolder) {
            final String money = "¥" +  df.format(taocanBean.get(position).getPrice()/100);
            final String meal_type = taocanBean.get(position).getId();
            final String mealName = taocanListBean.get(type).getName()+" "+taocanBean.get(position).getName();
            DiamondViewHolder diamondViewHolder = (DiamondViewHolder) holder;
            if (position == 0) {
                diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.zuanshi1);
                if(language.equals("en")){
                    diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.en_one_diamond);
                }
            } else if (position == 1) {
                diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.zuanshi2);
                if(language.equals("en")){
                    diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.en_two_diamond);
                }
            } else {
                diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.zuanshi3);
                if(language.equals("en")){
                    diamondViewHolder.linearLayout.setBackgroundResource(R.mipmap.en_three_diamond);
                }
            }
            diamondViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CreateOrderActivity.class);
                    intent.putExtra("mealId",meal_type);
                    intent.putExtra("mealType","1");
                    intent.putExtra("mealPrice",money);
                    intent.putExtra("mealName",mealName);
                    context.startActivity(intent);
                    /*PayDialog payDialog = new PayDialog(context, type + "", meal_type);
                    payDialog.show();
                    payDialog.setPrice(money);*/

                }
            });
            diamondViewHolder.diamond_newPrice.setText(money);


        } else {
            final String meal_type = taocanBean.get(position).getId();
            final String old_price = "¥" + df.format( taocanBean.get(position).getO_price()/100);
            final String money = "¥" + df.format(taocanBean.get(position).getPrice() / 100);
            final String mealName = taocanListBean.get(type).getName()+taocanBean.get(position).getName();
            MonthlyViewHolder monthlyViewHolder = (MonthlyViewHolder) holder;
            if (type == 2) {
                monthlyViewHolder.linearLayout.setBackgroundResource(R.mipmap.recharge_wz);
                if(language.equals("en")){
                    monthlyViewHolder.linearLayout.setBackgroundResource(R.mipmap.en_item_king);
                }
                monthlyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CreateOrderActivity.class);
                        intent.putExtra("mealId",meal_type);
                        intent.putExtra("mealType","3");
                        intent.putExtra("mealPrice",money);
                        intent.putExtra("mealName",mealName);
                        context.startActivity(intent);
                        /*PayDialog payDialog = new PayDialog(context, 2 + "", meal_type);
                        payDialog.show();
                        payDialog.setPrice(money);*/
                    }
                });
            } else {
                monthlyViewHolder.linearLayout.setBackgroundResource(R.mipmap.recharge_jy);
                if(language.equals("en")){
                    monthlyViewHolder.linearLayout.setBackgroundResource(R.mipmap.en_item_elitimg);
                }
                monthlyViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("tcid", taocanBean.get(position).getId());
                        Intent intent = new Intent(context, CreateOrderActivity.class);
                        intent.putExtra("mealId",meal_type);
                        intent.putExtra("mealType","2");
                        intent.putExtra("mealPrice",money);
                        intent.putExtra("mealName",mealName);
                        context.startActivity(intent);
                        /*PayDialog payDialog = new PayDialog(context, 1 + "", meal_type);
                        payDialog.show();
                        payDialog.setPrice(money);*/
                    }
                });
            }

            monthlyViewHolder.monthly_newPrice.setText(money);
            monthlyViewHolder.monthly_oldPrice.setText(old_price);
            monthlyViewHolder.monthly_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            monthlyViewHolder.monthly_name.setText(taocanBean.get(position).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("987456", "------" + type);
        return type == 0 ? type_diamond : type_monthly;
    }

    @Override
    public int getItemCount() {
        return taocanListBean != null ? taocanListBean.size() : 0;
    }
}
