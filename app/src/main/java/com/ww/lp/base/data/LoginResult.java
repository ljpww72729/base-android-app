package com.ww.lp.base.data;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/10/29.
 */

public class LoginResult implements Parcelable {

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Token getData() {
        return data;
    }

    public void setData(Token data) {
        this.data = data;
    }

    private int status;


    @SerializedName("data")
    private Token data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(Token.class.getClassLoader());
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

    /**
     * Created by LinkedME06 on 16/11/24.
     */
    public static class Token extends ErrorResult {
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.token);
        }

        public Token() {
        }

        protected Token(Parcel in) {
            this.token = in.readString();
        }

        public static final Creator<Token> CREATOR = new Creator<Token>() {
            @Override
            public Token createFromParcel(Parcel source) {
                return new Token(source);
            }

            @Override
            public Token[] newArray(int size) {
                return new Token[size];
            }
        };
    }
}
