package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/24.
 */

public class ErrorInfo implements Parcelable {
    private int err_code;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getErr_param() {
        return err_param;
    }

    public void setErr_param(String err_param) {
        this.err_param = err_param;
    }

    private String err_msg;
    private String err_param;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.err_code);
        dest.writeString(this.err_msg);
        dest.writeString(this.err_param);
    }

    public ErrorInfo() {
    }

    protected ErrorInfo(Parcel in) {
        this.err_code = in.readInt();
        this.err_msg = in.readString();
        this.err_param = in.readString();
    }

    public static final Creator<ErrorInfo> CREATOR = new Creator<ErrorInfo>() {
        @Override
        public ErrorInfo createFromParcel(Parcel source) {
            return new ErrorInfo(source);
        }

        @Override
        public ErrorInfo[] newArray(int size) {
            return new ErrorInfo[size];
        }
    };
}
