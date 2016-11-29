package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/27.
 */

public class ErrorResult implements Parcelable {
    private String status;

    public ErrorInfo getData() {
        return data;
    }

    public void setData(ErrorInfo data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private ErrorInfo data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public ErrorResult() {
    }

    protected ErrorResult(Parcel in) {
        this.status = in.readString();
        this.data = in.readParcelable(ErrorInfo.class.getClassLoader());
    }

    public static final Creator<ErrorResult> CREATOR = new Creator<ErrorResult>() {
        @Override
        public ErrorResult createFromParcel(Parcel source) {
            return new ErrorResult(source);
        }

        @Override
        public ErrorResult[] newArray(int size) {
            return new ErrorResult[size];
        }
    };
}
