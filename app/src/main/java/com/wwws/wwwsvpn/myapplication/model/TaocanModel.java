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


public class TaocanModel {


    /**
     * info : ok
     * status : 1
     * data : [{"id":1,"name":"钻石充值","meal":[{"id":"1","name":"30个","num":"30","o_price":3500,"price":3000},{"id":"2","name":"60个","num":"60","o_price":6500,"price":6000},{"id":"3","name":"90个","num":"90","o_price":9500,"price":9000}]},{"name":"王者","meal":[{"id":"5","name":"1个月会员","o_price":4500,"price":4000,"month":"1"},{"id":"6","name":"3个月会员","o_price":15000,"price":10000,"month":"3"},{"id":"7","name":"12个月会员","o_price":30000,"price":25000,"month":"12"}],"id":3},{"name":"精英","meal":[{"id":"2","name":"1个月会员","o_price":3500,"price":3000,"month":"1"},{"id":"3","name":"3个月会员","o_price":8500,"price":8000,"month":"3"},{"id":"4","name":"12个月会员","o_price":28800,"price":18800,"month":"12"}],"id":2}]
     */

    private String info;
    private int status;
    private int error_code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 钻石充值
         * meal : [{"id":"1","name":"30个","num":"30","o_price":3500,"price":3000},{"id":"2","name":"60个","num":"60","o_price":6500,"price":6000},{"id":"3","name":"90个","num":"90","o_price":9500,"price":9000}]
         */

        private int id;
        private String name;
        private List<MealBean> meal;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MealBean> getMeal() {
            return meal;
        }

        public void setMeal(List<MealBean> meal) {
            this.meal = meal;
        }

        public static class MealBean {
            /**
             * id : 1
             * name : 30个
             * num : 30
             * o_price : 3500
             * price : 3000
             */

            private String id;
            private String name;
            private String num;
            private int o_price;
            private int price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getO_price() {
                return o_price;
            }

            public void setO_price(int o_price) {
                this.o_price = o_price;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}