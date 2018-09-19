package com.wwws.wwwsvpn.myapplication.model;

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


public class QuestionModel {
    /**
     * info : ok
     * status : 1
     * data : [{"title":"不同的服务有何区别？","content":"每种服务带宽限制有所不同，您可根据自身需求购买相应的服务类型"},{"title":"使用时如何扣费？","content":"只要您在会员有效期内就能够享受我们的服务。"},{"title":"一个账号可以登录多台设备吗？","content":"绑定手机号以后可以在不同设备登录，但是同时只能有一台机器在线！"},{"title":"手机连接后打不开外网？","content":"1 检查版本更新，或者下载最新版本。\r\n2 查看手机后台进程有没有被清楚。\r\n3 切换网络重试。\r\n4 打开飞行模式再关闭重试。"}]
     */
    private int error_code;
    private String info;
    private int status;
    private List<QusetionBean> data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<QusetionBean> getData() {
        return data;
    }

    public void setData(List<QusetionBean> data) {
        this.data = data;
    }

    public static class QusetionBean {
        /**
         * title : 不同的服务有何区别？
         * content : 每种服务带宽限制有所不同，您可根据自身需求购买相应的服务类型
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
