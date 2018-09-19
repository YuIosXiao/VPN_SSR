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


public class ServerModel {


    /**
     * info : 1
     * status : 1
     * data : {"vip_type":"elite","vip_expire_time":1535623298,"server_info":"014119136d7367667363661111415b03757b61617b7a0b74117012126f7f6d342c383b0b130a4c4b7f6061666d392220101a1c130b4659585242443e283f757d6d291d150b0e4562796d120f1c1775131d6c646f7f6d3431253b464a47115e77712c38262e2a3d4141510e0d02105859521f3b3d3508222629080d06430d622c2d3539105521573f470477713c767a64761b1b4a4d4a2e64212e29797267530f43075a5253480146402970716e347f7156000f0858352d61302522073d"}
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
         * vip_type : elite
         * vip_expire_time : 1535623298
         * server_info : 014119136d7367667363661111415b03757b61617b7a0b74117012126f7f6d342c383b0b130a4c4b7f6061666d392220101a1c130b4659585242443e283f757d6d291d150b0e4562796d120f1c1775131d6c646f7f6d3431253b464a47115e77712c38262e2a3d4141510e0d02105859521f3b3d3508222629080d06430d622c2d3539105521573f470477713c767a64761b1b4a4d4a2e64212e29797267530f43075a5253480146402970716e347f7156000f0858352d61302522073d
         */

        private String vip_type;
        private int vip_expire_time;
        private String server_info;

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

        public String getServer_info() {
            return server_info;
        }

        public void setServer_info(String server_info) {
            this.server_info = server_info;
        }
    }
}
