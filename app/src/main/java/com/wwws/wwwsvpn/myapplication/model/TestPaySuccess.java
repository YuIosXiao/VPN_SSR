package com.wwws.wwwsvpn.myapplication.model;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class TestPaySuccess {

    /**
     * info : 1
     * status : 1
     * data : {"payment_tool":"alipay","pay_link":"https://pay.wen25.com/?to=SFRUUFM6Ly9RUi5BTElQQVkuQ09NL0ZLWDA3Njg0VlJYMFNKUlRVUFBCMUY/dD0xNTM1NDIyMzUyNTU1"}
     */

    private int info;
    private int status;
    private DataBean data;

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
         * payment_tool : alipay
         * pay_link : https://pay.wen25.com/?to=SFRUUFM6Ly9RUi5BTElQQVkuQ09NL0ZLWDA3Njg0VlJYMFNKUlRVUFBCMUY/dD0xNTM1NDIyMzUyNTU1
         */

        private String payment_tool;
        private String pay_link;

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
