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


public class FirstLoginModel {


    /**
     * info : 1
     * status : 1
     * data : {"uid":28,"account":"HQ10100","password":"432423","is_bind":0,"is_tested":0,"vip_type":"elite","vip_expire_time":0,"share_code":"15FTGI","referee_code":"","diamond":0,"create_type":2,"token":"EMk0Ep4iOikjVaPiLdkhbGCiOikHUNHazik9.EMkYDGKiOikiCnVN1da0CmVhC3VMZSHsHmlNCMH6HmkMDpzoLpRMZWfNDpklLpzlCnZXq2UiLdkXqpPiOJeayNUayJqAzJysHmVACdH6yTU0yNywyJg2yMwiC3ViHJoiyJgiLdkKZpZXq2VF1WRlbnRXZmllZdH6HmfJy2VlzJDKZGRKOGPxyWZKZmkJzc4My2qAZWy0ZJZlHn0.hAeDCRJ3jdIMfCIl5ZHiczztrr94CX59VEeV0DKmyEU"}
     */

    private String info;
    private int status;
    private DataBean data;
    private int error_code;
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
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
         * uid : 28
         * account : HQ10100
         * password : 432423
         * is_bind : 0
         * is_tested : 0
         * vip_type : elite
         * vip_expire_time : 0
         * share_code : 15FTGI
         * referee_code :
         * diamond : 0
         * create_type : 2
         * token : EMk0Ep4iOikjVaPiLdkhbGCiOikHUNHazik9.EMkYDGKiOikiCnVN1da0CmVhC3VMZSHsHmlNCMH6HmkMDpzoLpRMZWfNDpklLpzlCnZXq2UiLdkXqpPiOJeayNUayJqAzJysHmVACdH6yTU0yNywyJg2yMwiC3ViHJoiyJgiLdkKZpZXq2VF1WRlbnRXZmllZdH6HmfJy2VlzJDKZGRKOGPxyWZKZmkJzc4My2qAZWy0ZJZlHn0.hAeDCRJ3jdIMfCIl5ZHiczztrr94CX59VEeV0DKmyEU
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
        private int create_type;
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

        public int getCreate_type() {
            return create_type;
        }

        public void setCreate_type(int create_type) {
            this.create_type = create_type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
