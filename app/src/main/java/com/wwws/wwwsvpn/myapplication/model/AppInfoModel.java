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


public class AppInfoModel {


    /**
     * info : 1
     * status : 1
     * data : {"update_info":{"has_update":1,"title":"新版本","content":"更新了UI, 更新接口,添加了新活动.","download_url":"https://dev-accelerate.te6-api.net/release/app-release.apk"},"activity":[{"title":"Activity","content":"Recharge 199 to 20 diamonds."},{"title":"Clock in activity","content":"Punch in the card every day and give 1 hour for use every day."}],"route_table_version":"a5b8d9c948dd966bb4d587d93d7e0e45","four_pay_platform_opened":1}
     */

    private int info;
    private int status;
    private DataBean data;
    private int error_code;
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * update_info : {"has_update":1,"title":"新版本","content":"更新了UI, 更新接口,添加了新活动.","download_url":"https://dev-accelerate.te6-api.net/release/app-release.apk"}
         * activity : [{"title":"Activity","content":"Recharge 199 to 20 diamonds."},{"title":"Clock in activity","content":"Punch in the card every day and give 1 hour for use every day."}]
         * route_table_version : a5b8d9c948dd966bb4d587d93d7e0e45
         * four_pay_platform_opened : 1
         */

        private UpdateInfoBean update_info;
        private String route_table_version;
        private int four_pay_platform_opened;
        private List<ActivityBean> activity;

        public UpdateInfoBean getUpdate_info() {
            return update_info;
        }

        public void setUpdate_info(UpdateInfoBean update_info) {
            this.update_info = update_info;
        }

        public String getRoute_table_version() {
            return route_table_version;
        }

        public void setRoute_table_version(String route_table_version) {
            this.route_table_version = route_table_version;
        }

        public int getFour_pay_platform_opened() {
            return four_pay_platform_opened;
        }

        public void setFour_pay_platform_opened(int four_pay_platform_opened) {
            this.four_pay_platform_opened = four_pay_platform_opened;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class UpdateInfoBean {
            /**
             * has_update : 1
             * title : 新版本
             * content : 更新了UI, 更新接口,添加了新活动.
             * download_url : https://dev-accelerate.te6-api.net/release/app-release.apk
             */

            private int has_update;
            private String title;
            private String content;
            private String download_url;

            public int getHas_update() {
                return has_update;
            }

            public void setHas_update(int has_update) {
                this.has_update = has_update;
            }

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

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }
        }

        public static class ActivityBean {
            /**
             * title : Activity
             * content : Recharge 199 to 20 diamonds.
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
}
