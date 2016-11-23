package com.ww.lp.base.data;

import android.os.Parcel;

/**
 * Created by LinkedME06 on 16/10/29.
 */

public class LoginResult extends BaseResult {
 private String abc;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.abc);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        super(in);
        this.abc = in.readString();
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
