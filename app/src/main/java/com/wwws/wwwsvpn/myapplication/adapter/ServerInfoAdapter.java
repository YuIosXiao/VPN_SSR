package com.wwws.wwwsvpn.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.QuestionModel;
import com.wwws.wwwsvpn.myapplication.model.ServerInfoItem;

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


public class ServerInfoAdapter extends BaseAdapter {
    private List<ServerInfoItem> list;
    private LayoutInflater inflater;
    public ServerInfoAdapter(List<ServerInfoItem> list, Context context) {
        this.list = list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public ServerInfoItem getItem(int i) {
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
            view = inflater.inflate(R.layout.item_serverinfo,null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_item_title);
            holder.content = (TextView) view.findViewById(R.id.tv_item_content);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_item_icon);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        ServerInfoItem itemInfo = getItem(i);
        holder.imageView.setBackgroundResource(itemInfo.getIconRes());
        holder.title.setText(itemInfo.getTitle());
        holder.content.setText(itemInfo.getContent());

        return view;
    }

    static class ViewHolder{
        TextView    title;
        TextView    content;
        ImageView    imageView;
    }
}
