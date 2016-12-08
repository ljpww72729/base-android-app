package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/29.
 */

public class ServerResult implements Parcelable {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String status;
    private String data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.data);
    }

    public ServerResult() {
    }

    protected ServerResult(Parcel in) {
        this.status = in.readString();
        this.data = in.readString();
    }

    public static final Creator<ServerResult> CREATOR = new Creator<ServerResult>() {
        @Override
        public ServerResult createFromParcel(Parcel source) {
            return new ServerResult(source);
        }

        @Override
        public ServerResult[] newArray(int size) {
            return new ServerResult[size];
        }
    };
}
