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


public class WebsiteModel {


    /**
     * info : 1
     * status : 1
     * data : [{"name":"Instagram","url":"http://www.instagram.com","icon":"assets/images/icons/instagram-@2x.png","detail":"Photo saving and sharing software","android_pkg":"com.instagram.android","apple_store":"instagram"},{"name":"官方网站","url":"http://www.baidu.com","icon":"assets/images/icons/baidu-@2x.png","detail":"智能加速，优质服务","android_pkg":"com.baidu.browser","apple_store":""},{"name":"YouTube","url":"http://www.youtube.com","icon":"assets/images/icons/youtube-@2x.png","detail":"著名视频分享网站","android_pkg":"com.google.android.youtube","apple_store":"youtube"},{"name":"Facebook","url":"http://www.facebook.com","icon":"assets/images/icons/facebook-@2x.png","detail":"社交网络服务网站","android_pkg":"com.facebook.katana","apple_store":"fb"},{"name":"Twitter","url":"http://www.twitter.com","icon":"assets/images/icons/twitter-@2x.png","detail":"社交网络及微博服务网站","android_pkg":"com.twitter.android","apple_store":"twitter"},{"name":"Telegram","url":"http://www.telegram.com","icon":"assets/images/icons/telegram-@2x.png","detail":"隐私和安全通信软件","android_pkg":"org.telegram.messenger","apple_store":"telegram"},{"name":"Google","url":"http://www.google.com","icon":"assets/images/icons/google-@2x.png","detail":"互联网搜索引擎，资源搜索","android_pkg":"com.google.android.googlequicksearchbox","apple_store":"googlechrome"}]
     */
    private int error_code;
    private int info;
    private int status;
    private List<DataBean> data;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : Instagram
         * url : http://www.instagram.com
         * icon : assets/images/icons/instagram-@2x.png
         * detail : Photo saving and sharing software
         * android_pkg : com.instagram.android
         * apple_store : instagram
         */

        private String name;
        private String url;
        private String icon;
        private String detail;
        private String android_pkg;
        private String apple_store;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getAndroid_pkg() {
            return android_pkg;
        }

        public void setAndroid_pkg(String android_pkg) {
            this.android_pkg = android_pkg;
        }

        public String getApple_store() {
            return apple_store;
        }

        public void setApple_store(String apple_store) {
            this.apple_store = apple_store;
        }
    }
}
