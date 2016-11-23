package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/10/28.
 */

public class BaseResult implements Parcelable {


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
    }

    public BaseResult() {
    }

    protected BaseResult(Parcel in) {
        this.status = in.readString();
    }

}
