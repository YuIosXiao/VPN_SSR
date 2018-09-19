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


public class QusetionAdapter extends BaseAdapter {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;
    private List<QuestionModel.QusetionBean> list;
    private LayoutInflater inflater;
    private SharedPreferences mSharedPreference;
    public QusetionAdapter(List<QuestionModel.QusetionBean> list, Context context) {
        this.list = list;
        this.inflater=LayoutInflater.from(context);
        mSharedPreference = context.getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public QuestionModel.QusetionBean getItem(int i) {
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
            view = inflater.inflate(R.layout.item_question,null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_item_title);
            holder.content = (TextView) view.findViewById(R.id.tv_item_content);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_question);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            holder.imageView.setBackgroundResource(R.mipmap.question_icon);
        }
        QuestionModel.QusetionBean question = getItem(i);
        holder.title.setText(question.getTitle());
        holder.content.setText(question.getContent());

        return view;
    }

    static class ViewHolder{
        TextView    title;
        TextView    content;
        ImageView    imageView;
    }
}
