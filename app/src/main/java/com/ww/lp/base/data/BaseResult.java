package com.ww.lp.base.data;

import java.io.Serializable;

/**
 * Created by LinkedME06 on 16/10/28.
 */

public class BaseResult implements Serializable {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

}
