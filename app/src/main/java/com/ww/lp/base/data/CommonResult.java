package com.ww.lp.base.data;

import android.os.Parcel;

/**
 * Created by LinkedME06 on 27/12/2016.
 */

public class CommonResult extends BaseResult {
    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    private boolean data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.data ? (byte) 1 : (byte) 0);
    }

    public CommonResult() {
    }

    protected CommonResult(Parcel in) {
        super(in);
        this.data = in.readByte() != 0;
    }

    public static final Creator<CommonResult> CREATOR = new Creator<CommonResult>() {
        @Override
        public CommonResult createFromParcel(Parcel source) {
            return new CommonResult(source);
        }

        @Override
        public CommonResult[] newArray(int size) {
            return new CommonResult[size];
        }
    };
}
