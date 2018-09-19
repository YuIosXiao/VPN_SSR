package com.wwws.wwwsvpn.myapplication.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class LoginInfo implements Serializable {
    private String userName;
    private String password;


    public LoginInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
