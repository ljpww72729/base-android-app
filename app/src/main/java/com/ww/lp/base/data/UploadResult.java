package com.ww.lp.base.data;

import android.os.Parcel;

/**
 * Created by LinkedME06 on 16/11/28.
 */

public class UploadResult extends BaseResult {

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.data);
    }

    public UploadResult() {
    }

    protected UploadResult(Parcel in) {
        super(in);
        this.data = in.readString();
    }

    public static final Creator<UploadResult> CREATOR = new Creator<UploadResult>() {
        @Override
        public UploadResult createFromParcel(Parcel source) {
            return new UploadResult(source);
        }

        @Override
        public UploadResult[] newArray(int size) {
            return new UploadResult[size];
        }
    };
}
