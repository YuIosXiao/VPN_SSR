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


public class ServiceModel {


    /**
     * info : 1
     * status : 1
     * data : [{"id":"1","type":"elite","name":"精英","price_detail":"1钻石/天","detail":"访问Facebook、Twitter、Instagram等网站; 使用Telegram等通讯工具。","deduct_diamond":1,"vip_expire_time":0,"use_status":0},{"id":"2","type":"king","name":"王者","price_detail":"3钻石/天","detail":"享受\u201c精英服务\u201d所有特权; 流畅观看高清Youtube、Twitch视频。","deduct_diamond":3,"vip_expire_time":0,"use_status":0}]
     */

    private int info;
    private int status;
    private List<DataBean> data;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * type : elite
         * name : 精英
         * price_detail : 1钻石/天
         * detail : 访问Facebook、Twitter、Instagram等网站; 使用Telegram等通讯工具。
         * deduct_diamond : 1
         * vip_expire_time : 0
         * use_status : 0
         */

        private String id;
        private String type;
        private String name;
        private String price_detail;
        private String detail;
        private int deduct_diamond;
        private int vip_expire_time;
        private int use_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice_detail() {
            return price_detail;
        }

        public void setPrice_detail(String price_detail) {
            this.price_detail = price_detail;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getDeduct_diamond() {
            return deduct_diamond;
        }

        public void setDeduct_diamond(int deduct_diamond) {
            this.deduct_diamond = deduct_diamond;
        }

        public int getVip_expire_time() {
            return vip_expire_time;
        }

        public void setVip_expire_time(int vip_expire_time) {
            this.vip_expire_time = vip_expire_time;
        }

        public int getUse_status() {
            return use_status;
        }

        public void setUse_status(int use_status) {
            this.use_status = use_status;
        }
    }
}

