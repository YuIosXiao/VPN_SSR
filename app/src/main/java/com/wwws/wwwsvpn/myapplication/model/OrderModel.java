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

public class OrderModel {


    /**
     * info : 1
     * status : 1
     * data : {"order_id":"100115360432521004","payment_tool":"alipay","pay_link":"https://pay.wen25.com/?to=SFRUUFM6Ly9RUi5BTElQQVkuQ09NL0ZLWDA1MTIwSldYTjhCREVYSjNBMzc/dD0xNTM2MDQzMjU1MDgx"}
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
         * order_id : 100115360432521004
         * payment_tool : alipay
         * pay_link : https://pay.wen25.com/?to=SFRUUFM6Ly9RUi5BTElQQVkuQ09NL0ZLWDA1MTIwSldYTjhCREVYSjNBMzc/dD0xNTM2MDQzMjU1MDgx
         */

        private String order_id;
        private String payment_tool;
        private String pay_link;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPayment_tool() {
            return payment_tool;
        }

        public void setPayment_tool(String payment_tool) {
            this.payment_tool = payment_tool;
        }

        public String getPay_link() {
            return pay_link;
        }

        public void setPay_link(String pay_link) {
            this.pay_link = pay_link;
        }
    }
}
