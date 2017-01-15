package com.ww.lp.base.data.user;

import android.os.Parcel;

import com.ww.lp.base.data.BaseResult;

/**
 * Created by LinkedME06 on 26/12/2016.
 */

public class LoginResult extends BaseResult {
    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    private UserInfo data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.data, flags);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        super(in);
        this.data = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Creator<LoginResult> CREATOR = new Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel source) {
            return new LoginResult(source);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
}
