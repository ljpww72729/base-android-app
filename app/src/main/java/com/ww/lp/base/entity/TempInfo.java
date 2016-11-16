package com.ww.lp.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TempInfo implements Parcelable {

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.info);
    }

    public TempInfo() {
    }

    protected TempInfo(Parcel in) {
        this.info = in.readString();
    }

    public static final Creator<TempInfo> CREATOR = new Creator<TempInfo>() {
        @Override
        public TempInfo createFromParcel(Parcel source) {
            return new TempInfo(source);
        }

        @Override
        public TempInfo[] newArray(int size) {
            return new TempInfo[size];
        }
    };
}
