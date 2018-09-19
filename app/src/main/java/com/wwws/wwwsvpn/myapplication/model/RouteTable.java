package com.wwws.wwwsvpn.myapplication.model;

import java.util.List;

public class RouteTable {


    /**
     * info : 1
     * status : 1
     * data : [{"type":"domainlist","adapter":"proxyAdapter","criteria":["s,sina.com","s,baidu.com","s,ipip.com","s,google.com","s,facebook.com"]},{"type":"iplist","adapter":"proxyAdapter","criteria":["127.0.0.0/8","129.168.1.2/16","58.49.120.26/32"]}]
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
         * type : domainlist
         * adapter : proxyAdapter
         * criteria : ["s,sina.com","s,baidu.com","s,ipip.com","s,google.com","s,facebook.com"]
         */

        private String type;
        private String adapter;
        private List<String> criteria;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAdapter() {
            return adapter;
        }

        public void setAdapter(String adapter) {
            this.adapter = adapter;
        }

        public List<String> getCriteria() {
            return criteria;
        }

        public void setCriteria(List<String> criteria) {
            this.criteria = criteria;
        }
    }
}
