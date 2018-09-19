package com.wwws.wwwsvpn.myapplication.model;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class Info {

    /**
     * info : 1
     * status : 1
     * data : {"uid":29,"account":"15738539221","used_status":1,"vip_type":"elite","vip_expire_time":0,"is_bind":true,"diamond":0,"is_tested":0,"share_code":"15FTGJ","referee_code":""}
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
         * uid : 29
         * account : 15738539221
         * used_status : 1
         * vip_type : elite
         * vip_expire_time : 0
         * is_bind : true
         * diamond : 0
         * is_tested : 0
         * share_code : 15FTGJ
         * referee_code :
         */

        private int uid;
        private String account;
        private int used_status;
        private String vip_type;
        private int vip_expire_time;
        private boolean is_bind;
        private int diamond;
        private int is_tested;
        private String share_code;
        private String referee_code;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getUsed_status() {
            return used_status;
        }

        public void setUsed_status(int used_status) {
            this.used_status = used_status;
        }

        public String getVip_type() {
            return vip_type;
        }

        public void setVip_type(String vip_type) {
            this.vip_type = vip_type;
        }

        public int getVip_expire_time() {
            return vip_expire_time;
        }

        public void setVip_expire_time(int vip_expire_time) {
            this.vip_expire_time = vip_expire_time;
        }

        public boolean isIs_bind() {
            return is_bind;
        }

        public void setIs_bind(boolean is_bind) {
            this.is_bind = is_bind;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getIs_tested() {
            return is_tested;
        }

        public void setIs_tested(int is_tested) {
            this.is_tested = is_tested;
        }

        public String getShare_code() {
            return share_code;
        }

        public void setShare_code(String share_code) {
            this.share_code = share_code;
        }

        public String getReferee_code() {
            return referee_code;
        }

        public void setReferee_code(String referee_code) {
            this.referee_code = referee_code;
        }
    }
}
