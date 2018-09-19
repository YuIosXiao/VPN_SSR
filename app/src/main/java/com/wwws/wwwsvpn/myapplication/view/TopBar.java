package com.wwws.wwwsvpn.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wwws.wwwsvpn.myapplication.R;


public class TopBar extends RelativeLayout{
    private TextView btRightItem;
    private Button bt_back;
    private TextView tv_title;
    private RelativeLayout relativeLayout;
    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    private OnBackClickListener listener;//监听点击事件

    //设置监听器
    public void setBackClickListener(OnBackClickListener listener) {
        this.listener = listener;
    }

    //按钮点击接口
    public interface OnBackClickListener {
        void OnBackButtonClick();

    }

    @Override
    public void setBackgroundResource(int resid) {
        relativeLayout.setBackgroundResource(resid);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_topbar, this);
       bt_back = (Button) findViewById(R.id.bt_back);
       tv_title = (TextView) findViewById(R.id.tv_title);
       btRightItem = (TextView) findViewById(R.id.bt_right_item);
       relativeLayout = (RelativeLayout) findViewById(R.id.rl_tb);
        bt_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnBackButtonClick();//点击回调
                }
            }
        });
        //获得自定义属性并赋值
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        String titleText = typeArray.getString(R.styleable.TopBar_titleText);

        //释放资源
        typeArray.recycle();
        tv_title.setText(titleText);

    }

    public void setRightItem(String title,View.OnClickListener listener) {
        btRightItem.setText(title);
        btRightItem.setVisibility(View.VISIBLE);
        btRightItem.setOnClickListener(listener);
    }
}
