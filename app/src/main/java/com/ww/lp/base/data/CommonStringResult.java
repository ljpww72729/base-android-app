package com.ww.lp.base.data;

import android.os.Parcel;

/**
 * Created by LinkedME06 on 28/12/2016.
 */

public class CommonStringResult extends BaseResult {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.data);
    }

    public CommonStringResult() {
    }

    protected CommonStringResult(Parcel in) {
        super(in);
        this.data = in.readString();
    }

    public static final Creator<CommonStringResult> CREATOR = new Creator<CommonStringResult>() {
        @Override
        public CommonStringResult createFromParcel(Parcel source) {
            return new CommonStringResult(source);
        }

        @Override
        public CommonStringResult[] newArray(int size) {
            return new CommonStringResult[size];
        }
    };
}
