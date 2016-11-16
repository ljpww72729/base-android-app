package com.ww.lp.base.data;

/**
 * Created by LinkedME06 on 16/10/29.
 */

public class LoginResult extends BaseResult {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
