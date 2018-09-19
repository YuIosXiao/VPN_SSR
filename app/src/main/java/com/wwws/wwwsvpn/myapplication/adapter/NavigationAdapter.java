package com.wwws.wwwsvpn.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.WebsiteModel;

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


public class NavigationAdapter extends BaseAdapter {

    private final static int VIP_TYPE_WANGZHE = 2;
    private List<WebsiteModel.DataBean> list;
    private LayoutInflater inflater;
    private Context context;
    private boolean isWangZhe = false;
    private final static String BASE_URL  = "https://global-accelerate.te6-api.net/";

    public NavigationAdapter(List<WebsiteModel.DataBean> list , Context context) {

        this.list = list;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        if (context.getSharedPreferences("config", MODE_PRIVATE).getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            isWangZhe = true;
        }
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



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view ==null){
            view = inflater.inflate(R.layout.item_navigation,null);
            holder = new ViewHolder();
            holder.linearLayout = (LinearLayout) view.findViewById(R.id.ll_item_navigation);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_icon);
            holder.typename = (TextView) view.findViewById(R.id.tv_website);
            holder.typedesc = (TextView) view.findViewById(R.id.tv_websitedesc);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if (isWangZhe){
            holder.linearLayout.setBackgroundResource(R.mipmap.king_naviitem_bg);
        } else {
            holder.linearLayout.setBackgroundResource(R.mipmap.elites_naviitem_bg);
        }
        WebsiteModel.DataBean websiteBean = (WebsiteModel.DataBean) getItem(i);
        holder.typename.setText(websiteBean.getName());
        holder.typedesc.setText(websiteBean.getDetail());
        final String url = websiteBean.getUrl();
        String iconurl = BASE_URL +websiteBean.getIcon();
        Log.d("dasdadada",iconurl);
        Glide.with(context)
                .load(iconurl)
                .into(holder.imageView);

         /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               holder.typename.setTextColor(Color.parseColor("#12BBE7"));
               holder.imageView.setVisibility(View.VISIBLE);
               Uri uri = Uri.parse(url);
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               context.startActivity(intent);

           }
       });*/
        return view;
    }

    static class ViewHolder{
        LinearLayout linearLayout;
        TextView typename;
        ImageView imageView;
        TextView typedesc;
    }

}
