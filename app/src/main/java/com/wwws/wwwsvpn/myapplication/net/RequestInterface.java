package com.wwws.wwwsvpn.myapplication.net;

import com.wwws.wwwsvpn.myapplication.model.AppInfoModel;
import com.wwws.wwwsvpn.myapplication.model.Disconnect;
import com.wwws.wwwsvpn.myapplication.model.FeedModel;
import com.wwws.wwwsvpn.myapplication.model.FirstLoginModel;
import com.wwws.wwwsvpn.myapplication.model.GetStatus;
import com.wwws.wwwsvpn.myapplication.model.Info;
import com.wwws.wwwsvpn.myapplication.model.OrderListModel;
import com.wwws.wwwsvpn.myapplication.model.OrderModel;
import com.wwws.wwwsvpn.myapplication.model.QuestionModel;
import com.wwws.wwwsvpn.myapplication.model.RegisterModel;
import com.wwws.wwwsvpn.myapplication.model.RouteTable;
import com.wwws.wwwsvpn.myapplication.model.ServerModel;
import com.wwws.wwwsvpn.myapplication.model.ServiceModel;
import com.wwws.wwwsvpn.myapplication.model.SignupModel;
import com.wwws.wwwsvpn.myapplication.model.TaocanModel;
import com.wwws.wwwsvpn.myapplication.model.TestPaySuccess;
import com.wwws.wwwsvpn.myapplication.model.UpdataModel;
import com.wwws.wwwsvpn.myapplication.model.UserInfoModel;
import com.wwws.wwwsvpn.myapplication.model.VerifyModel;
import com.wwws.wwwsvpn.myapplication.model.WebsiteModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


public interface RequestInterface {

    //创建用户
    @POST("User/Create")
    @FormUrlEncoded
    Call<FirstLoginModel> account(@Field("device_identified") String unique);

    // 获取更新消息和活动消息
    //获取APP配置
    @GET("Globally/Config")
    Call<AppInfoModel> getAppInfo(@Query("pkgname") String pkgname,
                                  @Query("version")String version,
                                  @Query("locale")String locale);

    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段

    //登录
    @POST("Auth/Login")
    @FormUrlEncoded
    Call<UserInfoModel> login(@Field("account") String account,
                              @Field("password") String password,
                              @Field("device_identified") String device_identified);

    //修改密码验证码
    @GET("Captcha/ChangePassword")
    Call<VerifyModel> changePasswordverify(@Header("Authorization") String authorization);

    //注册验证码
    @GET("Captcha/Register")
    Call<VerifyModel> verifyNotHeader(@Header("Authorization") String authorization,
                                      @Query("cellphone") String cellphone);

    //忘记密码验证码
    @GET("Captcha/ForgetPassword")
    Call<VerifyModel> forgetPasswordVerify(@Header("Authorization") String authorization,
                                           @Query("cellphone") String cellphone);

    //绑定手机验证码
    @GET("Captcha/BindCellphone")
    Call<VerifyModel> bingVerify(@Header("Authorization") String authorization,
                                 @Query("cellphone") String cellphone);

    //手机号注册用户
    @POST("Auth/Register")
    @FormUrlEncoded
    Call<RegisterModel> register(@Field("account") String account,
                                 @Field("password") String password,
                                 @Field("device_identified") String ssr,
                                 @Field("captcha") String captcha);

    //忘记密码
    @POST("User/ForgetPassword")
    @FormUrlEncoded
    Call<FeedModel> forgetPassword( @Field("account") String account,
                                    @Field("password") String password,
                                    @Field("captcha") String captcha);

    //修改密码
    @POST("User/ChangePassword")
    @FormUrlEncoded
    Call<UpdataModel> changePassword(@Header("Authorization") String authorization,
                                     @Field("cellphone") String cellphone,
                                     @Field("password") String password,
                                     @Field("captcha") String captcha);

    //绑定手机号
    @POST("User/BindCellphone")
    @FormUrlEncoded
    Call<UpdataModel> bind(@Header("Authorization") String authorization,
                           @Field("cellphone") String cellphone,
                           @Field("password") String password,
                           @Field("captcha") String captcha);


    //绑定推荐码
    @POST("User/BindShareCode")
    @FormUrlEncoded
    Call<UpdataModel> bindShareCode(@Header("Authorization") String authorization,
                                    @Field("code") String shareCode);


    //网址导航
    @POST("Globally/WebsiteNavigation")
    Call<WebsiteModel> website(@Query("locale") String locale);


    //提交反馈
    @POST("User/Feedback")
    @FormUrlEncoded
    Call<FeedModel> feed(@Header("Authorization") String authorization,
                         @Field("content") String content,
                         @Field("contact") String contact);


    //签到
    @GET("User/clockIn")
    Call<String> signup(@Header("Authorization") String authorization);



    //问题列表
    @GET("Globally/Question")
    Call<QuestionModel> question( @Query("locale") String locale);


    //服务器类别
    @GET("Service/Type")
    Call<ServiceModel> service(@Header("Authorization") String authorization,
                               @Query("locale") String locale);

    //套餐
    @GET("Globally/Meal")
    Call<TaocanModel> taocan(@Header("Authorization") String authorization,
                             @Query("locale") String locale);
/*    //套餐
    @GET("Globally/Meal")
    Call<String> taocan1(@Header("Authorization") String authorization);*/

    //断开连接
    @GET("Server/Disconnect")
    Call<Disconnect> disconnect(@Header("Authorization") String authorization);


    //获取服务器
    @GET("Server/Connect")
    Call<ServerModel> ssr(@Header("Authorization") String authorization,
                          @Query("service_type") String cellphone);
    //获取服务器
    @GET("Server/Connect")
    Call<String> ssr1(@Header("Authorization") String authorization,
                          @Query("service_type") String service_type);



    //获取订单列表
    @GET("Order/ListData")
    Call<OrderListModel> orderList(@Header("Authorization") String authorization,
                                   @Query("locale") String locale,
                                   @Query("page") String page);

    //用户信息
    @POST("User/Info")
    Call<Info> getInfo(@Header("Authorization") String authorization);

    //创建订单
    @GET("Order/Create")
    Call<OrderModel> createOrder(@Header("Authorization") String authorization,
                                 @Query("payment_tool") String payment_tool,
                                 @Query("meal_id") String meal_id);

    //获取订单状态
    @GET("Order/GetStatus")
    Call<GetStatus> getStatus(@Header("Authorization") String authorization,
                              @Query("order_id") String order_id);
    //获取路由表
    @GET("Globally/RouteTable")
    Call<RouteTable> getRouteTable(@Header("Authorization") String authorization);

}
