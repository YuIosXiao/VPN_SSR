package com.wwws.wwwsvpn.myapplication.model;

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


public class RegisterModel {

    /**
     * info : 1
     * status : 1
     * data : {"uid":32,"account":"18986219823","password":"123456","is_bind":1,"is_tested":0,"vip_type":"elite","vip_expire_time":0,"share_code":"15FTGM","referee_code":"","diamond":0,"token":"EMk0Ep4iOikjVaPiLdkhbGCiOikHUNHazik9.EMkYDGKiOikiCnVN1da0CmVhC3VMZSHsHmlNCMH6HmkMDpzoLpRMZWfNDpklLpzlCnZXq2UiLdkXqpPiOJeayNUayNP3ycgsHmVACdH6yTU0yNyxycCwOdwiC3ViHJoiyNHiLdkKZpZXq2VF1WRlbnRXZmllZdH6HJKMymHwzmy3qmRmyce0yGHwZJU2yckizJDmOTP5ZT4aHn0.RmbnJSY6BtWIlIfS0Ae6Eswjr4VdFPanXY1RXv1-VkC"}
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
         * uid : 32
         * account : 18986219823
         * password : 123456
         * is_bind : 1
         * is_tested : 0
         * vip_type : elite
         * vip_expire_time : 0
         * share_code : 15FTGM
         * referee_code :
         * diamond : 0
         * token : EMk0Ep4iOikjVaPiLdkhbGCiOikHUNHazik9.EMkYDGKiOikiCnVN1da0CmVhC3VMZSHsHmlNCMH6HmkMDpzoLpRMZWfNDpklLpzlCnZXq2UiLdkXqpPiOJeayNUayNP3ycgsHmVACdH6yTU0yNyxycCwOdwiC3ViHJoiyNHiLdkKZpZXq2VF1WRlbnRXZmllZdH6HJKMymHwzmy3qmRmyce0yGHwZJU2yckizJDmOTP5ZT4aHn0.RmbnJSY6BtWIlIfS0Ae6Eswjr4VdFPanXY1RXv1-VkC
         */

        private int uid;
        private String account;
        private String password;
        private int is_bind;
        private int is_tested;
        private String vip_type;
        private int vip_expire_time;
        private String share_code;
        private String referee_code;
        private int diamond;
        private String token;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(int is_bind) {
            this.is_bind = is_bind;
        }

        public int getIs_tested() {
            return is_tested;
        }

        public void setIs_tested(int is_tested) {
            this.is_tested = is_tested;
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

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
