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

public class OrderListModel {


    /**
     * info : ok
     * status : 1
     * data : {"totalPage":3,"list":[{"order_id":"313015263725231007","meal_title":"钻石套餐 90个","status":"已完成","price":"90.00","create_time":"2018-05-15 16:22:03"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"}]}
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
         * totalPage : 3
         * list : [{"order_id":"313015263725231007","meal_title":"钻石套餐 90个","status":"已完成","price":"90.00","create_time":"2018-05-15 16:22:03"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"},{"order_id":"313015251859241006","meal_title":"精英套餐 1个月会员","status":"已完成","price":"0.00","create_time":"2018-05-01 22:45:24"}]
         */

        private int totalPage;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * order_id : 313015263725231007
             * meal_title : 钻石套餐 90个
             * status : 已完成
             * price : 90.00
             * create_time : 2018-05-15 16:22:03
             */

            private String order_id;
            private String meal_title;
            private String status;
            private String price;
            private String create_time;

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getMeal_title() {
                return meal_title;
            }

            public void setMeal_title(String meal_title) {
                this.meal_title = meal_title;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
