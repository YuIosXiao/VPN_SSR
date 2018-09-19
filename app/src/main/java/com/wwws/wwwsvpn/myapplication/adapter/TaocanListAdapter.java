package com.wwws.wwwsvpn.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.model.TaocanModel;

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


public class TaocanListAdapter extends BaseAdapter {
    private final static int DIAMOND_RECHARGE = 1;
    private Context context;
    private List<TaocanModel.DataBean> list;
    private LayoutInflater inflater;
    private String language;
    public TaocanListAdapter(List<TaocanModel.DataBean> list , Context context,String language) {
        this.context = context;
        this.list = list;
        this.language = language;
        this.inflater=LayoutInflater.from(context);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            view = inflater.inflate(R.layout.item_recharge_list,null);
            holder = new ViewHolder();
            holder.recyclerView = (RecyclerView) view.findViewById(R.id.rl_recharge);
            holder.typename = (TextView) view.findViewById(R.id.tv_item_recharge_list);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.typename.setText(list.get(i).getName());
        StaggeredGridLayoutManager layoutmanager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        holder.recyclerView.setLayoutManager(layoutmanager);
        holder.recyclerView.setAdapter(new RechargeTaocanAdapter(list, context,i,language));
        return view;

    }
    static class ViewHolder{
        RecyclerView recyclerView;
        TextView typename;
    }
}
