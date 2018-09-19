package com.wwws.wwwsvpn.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.ServiceModel;
import com.wwws.wwwsvpn.myapplication.ui.RechargeActivity;
import com.wwws.wwwsvpn.myapplication.ui.SettingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
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


public class ServiceAdapter extends BaseAdapter {
    private List<ServiceModel.DataBean> list;
    private LayoutInflater inflater;
    private SharedPreferences mSharedPreference;
    private Context mContext;
    /**
     * 点击事件回传
     */
    private VpnTypeListener vpnTypeListener;
    public void setVpnTypeListener(VpnTypeListener vpnTypeListener) {
        this.vpnTypeListener = vpnTypeListener;
    }
    public interface VpnTypeListener {
        void OntopicClickListener(String vpnType);
    }


    public ServiceAdapter(List<ServiceModel.DataBean> list , Context context) {
        this.list = list;
        mContext=context;
        this.inflater=LayoutInflater.from(context);
        mSharedPreference = context.getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            view = inflater.inflate(R.layout.item_service,null);
            holder = new ViewHolder();
            holder.button = (Button) view.findViewById(R.id.bt_item_service);
            holder.bottom_typename =(TextView)  view.findViewById(R.id.tv_service_item_name);
            holder.type =(TextView)  view.findViewById(R.id.tv_type);
            holder.linearLayout = (LinearLayout) view.findViewById(R.id.ll_service_bg);
            holder.price = (TextView) view.findViewById(R.id.tv_price);
            holder.desc = (TextView) view.findViewById(R.id.tv_desc);
            holder.time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final ServiceModel.DataBean service = (ServiceModel.DataBean) getItem(i);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (service.getUse_status())
                {
                    case 2:
                        //正在使用
                        break;
                    case 1:
                        //已经购买，但是没有使用
                        SharedPreferences.Editor editor = mSharedPreference.edit();
                        Log.d("65464654", service.getId()+"");
                        //editor.putInt("vip_type", service.getId());
                        editor.putInt("service_type", service.getType().equals("elite")?1:2);
                        editor.commit();
                        if (vpnTypeListener!=null&&service.getUse_status()!=2){
                            vpnTypeListener.OntopicClickListener(service.getId()+"");
                        }
                        break;
                    default:
                        //没有购买
                        if (mSharedPreference.getInt("diamond", 0) >= service.getDeduct_diamond() ){
                            SharedPreferences.Editor defEditor = mSharedPreference.edit();
                            Log.d("65464654", service.getId()+"");
                            //defEditor.putInt("vip_type", service.getId());
                            defEditor.putInt("service_type", service.getType().equals("elite")?1:2);
                            defEditor.commit();
                            if (vpnTypeListener!=null&&service.getUse_status()!=2){
                                vpnTypeListener.OntopicClickListener(service.getId()+"");
                            }
                        } else {
                            Intent intent1 = new Intent(mContext, RechargeActivity.class);
                            mContext.startActivity(intent1);
                        }
                            break;
                }

            }
        });
        holder.bottom_typename.setText(service.getName()+" "+mContext.getString(R.string.service1));
        holder.type.setText(service.getName()+" "+mContext.getString(R.string.service1));
        holder.desc.setText(service.getDetail());
        holder.price.setText(service.getPrice_detail());
        long  timeStamp = Long.valueOf( service.getVip_expire_time()+"");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp*1000L));
        Log.d("jpjpjpjp", service.getVip_expire_time()+"");
        if(service.getVip_expire_time()!=0){
        holder.time.setText(mContext.getString(R.string.deadline1)+sd);
        }else{
            holder.time.setText("");
        }
        Log.d("e12e1e12",i+""+service.getUse_status());
        if(service.getUse_status()==2) {
            holder.button.setText(R.string.In_use);
        }else {
            holder.button.setText(R.string.Used_now);
        }

        if(i==0){
            holder.price.setTextColor(Color.parseColor("#FFFFFF"));
            holder.type.setTextColor(Color.parseColor("#ffffff"));
            holder.button.setTextColor(Color.parseColor("#ffffff"));
            holder.price.setBackgroundResource(R.mipmap.jy_price);
            holder.linearLayout.setBackgroundResource(R.mipmap.jy_bg);
            holder.button.setBackgroundResource(R.mipmap.jy_btn);
            holder.time.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.price.setTextColor(Color.parseColor("#8D5E14"));
            holder.type.setTextColor(Color.parseColor("#694000"));
            holder.button.setTextColor(Color.parseColor("#905700"));
            holder.price.setBackgroundResource(R.mipmap.wz_price);
            holder.linearLayout.setBackgroundResource(R.mipmap.wz_bg);
            holder.button.setBackgroundResource(R.mipmap.wz_btn);
            holder.time.setTextColor(Color.parseColor("#694000"));
        }
        return view;

    }



    static class ViewHolder{
        Button button;
        LinearLayout linearLayout;
        TextView desc;
        TextView type;
        TextView bottom_typename;
        TextView price;
        TextView time;
    }
}
