package com.wwws.wwwsvpn.myapplication.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wwws.wwwsvpn.myapplication.R;
import com.wwws.wwwsvpn.myapplication.adapter.ServerInfoAdapter;
import com.wwws.wwwsvpn.myapplication.base.BaseActivity;
import com.wwws.wwwsvpn.myapplication.model.SSRModel;
import com.wwws.wwwsvpn.myapplication.model.ServerInfoItem;
import com.wwws.wwwsvpn.myapplication.utils.ASCIIUtils;
import com.wwws.wwwsvpn.myapplication.utils.AppManager;
import com.wwws.wwwsvpn.myapplication.view.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


public class ServerInfoActivity extends BaseActivity {
    private final static int VIP_TYPE_JINGYING = 1;
    private final static int VIP_TYPE_WANGZHE = 2;

    private final static int REQ_SUCCESS = 1;
    @BindView(R.id.tb_serverinfo)
    TopBar tbServerInfo;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.ll_serverinfo)
    LinearLayout llServerInfo;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.copy_download_url)
    Button copyDownloadUrl;
    private List<ServerInfoItem> list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_serverinfo;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tbServerInfo.setBackClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getInt("vip_type", 0) == VIP_TYPE_WANGZHE) {
            tbServerInfo.setBackgroundResource(R.drawable.shape_topbar_wz);
            llServerInfo.setBackgroundColor(Color.parseColor("#d8a04e"));
            ivState.setBackgroundResource(R.drawable.shape_topbar_wz);
            copyDownloadUrl.setBackgroundResource(R.mipmap.copy_btn_wz);
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

    @Override
    protected void initData() {
        String serverInfo = mSharedPreference.getString("vpn_server_info", "");
        String serverStr = ASCIIUtils.convertHexToString(serverInfo);
        String jsonStr = ASCIIUtils.formatString(serverStr);
        Gson gson = new Gson();
        SSRModel ssrModel = gson.fromJson(jsonStr, SSRModel.class);
        list = new ArrayList<>();
        ServerInfoItem itemServer = new ServerInfoItem();
        itemServer.setIconRes(R.mipmap.item_server_icon);
        itemServer.setTitle(getString(R.string.service));
        itemServer.setContent(ssrModel.getIp());
        list.add(itemServer);
        ServerInfoItem itemreport = new ServerInfoItem();
        itemreport.setIconRes(R.mipmap.item_roport_icon);
        itemreport.setTitle(getString(R.string.remote_port));
        itemreport.setContent(String.format(getString(R.string.remote_port_tip),ssrModel.getPort()));
        list.add(itemreport);
        ServerInfoItem itemlocal = new ServerInfoItem();
        itemlocal.setIconRes(R.mipmap.item_loport_icon);
        itemlocal.setTitle(getString(R.string.local_port));
        itemlocal.setContent(getString(R.string.local_port_tip));
        list.add(itemlocal);
        ServerInfoItem itempwd = new ServerInfoItem();
        itempwd.setIconRes(R.mipmap.item_pwd_icon);
        itempwd.setTitle(getString(R.string.password));
        itempwd.setContent(ssrModel.getPassword());
        list.add(itempwd);
        ServerInfoItem itemEncryp = new ServerInfoItem();
        itemEncryp.setIconRes(R.mipmap.item_encryp_icon);
        itemEncryp.setTitle(getString(R.string.encrypt_method));
        itemEncryp.setContent(ssrModel.getMethod());
        list.add(itemEncryp);
        ServerInfoItem itemprotocol = new ServerInfoItem();
        itemprotocol.setIconRes(R.mipmap.item_protocol_icon);
        itemprotocol.setTitle(getString(R.string.protocol));
        itemprotocol.setContent(ssrModel.getProtocol());
        list.add(itemprotocol);
        ServerInfoItem itemproparam = new ServerInfoItem();
        itemproparam.setIconRes(R.mipmap.item_protocolparam_icon);
        itemproparam.setTitle(getString(R.string.Protocol_param));
        itemproparam.setContent(getString(R.string.default1));
        list.add(itemproparam);
        ServerInfoItem itemmuddle = new ServerInfoItem();
        itemmuddle.setIconRes(R.mipmap.item_muddle_icon);
        itemmuddle.setTitle(getString(R.string.obsf));
        itemmuddle.setContent(ssrModel.getObfs());
        list.add(itemmuddle);
        ServerInfoItem itemmuddleparam = new ServerInfoItem();
        itemmuddleparam.setIconRes(R.mipmap.item_muddleparam_icon);
        itemmuddleparam.setTitle(getString(R.string.obfs_param));
        itemmuddleparam.setContent(getString(R.string.default1));
        list.add(itemmuddleparam);
        listView.setAdapter(new ServerInfoAdapter(list, ServerInfoActivity.this));
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void backUpActivity(){
        AppManager.getAppManager().finishActivity(ServerInfoActivity.this);
    }

    @OnClick({R.id.copy_download_url})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copy_download_url:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(mSharedPreference.getString("pcsoftUrl",""));
                Toast.makeText(this, "Copying success!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}


