package com.ww.lp.base.data.pay;

import android.os.Parcel;

import com.ww.lp.base.data.BaseResult;

/**
 * Created by LinkedME06 on 27/12/2016.
 */

public class SignResult extends BaseResult {

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

    public SignResult() {
    }

    protected SignResult(Parcel in) {
        super(in);
        this.data = in.readString();
    }

    public static final Creator<SignResult> CREATOR = new Creator<SignResult>() {
        @Override
        public SignResult createFromParcel(Parcel source) {
            return new SignResult(source);
        }

        @Override
        public SignResult[] newArray(int size) {
            return new SignResult[size];
        }
    };
}
