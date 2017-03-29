package com.ww.lp.base.data.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 26/12/2016.
 */

public class UserInfo implements Parcelable {
    private int isAdmin;
    private int isDeveloper;
    private String userId;
    private String token;
    private String email;
    private String avatarImg;
    private String password;
    private String phoneNum;
    private String teamId;
    private String verificationCode;

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    private String adminEmail;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getIsDeveloper() {
        return isDeveloper;
    }

    public void setIsDeveloper(int isDeveloper) {
        this.isDeveloper = isDeveloper;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    public UserInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isAdmin);
        dest.writeInt(this.isDeveloper);
        dest.writeString(this.userId);
        dest.writeString(this.token);
        dest.writeString(this.email);
        dest.writeString(this.avatarImg);
        dest.writeString(this.password);
        dest.writeString(this.phoneNum);
        dest.writeString(this.teamId);
        dest.writeString(this.verificationCode);
        dest.writeString(this.adminEmail);
    }

    protected UserInfo(Parcel in) {
        this.isAdmin = in.readInt();
        this.isDeveloper = in.readInt();
        this.userId = in.readString();
        this.token = in.readString();
        this.email = in.readString();
        this.avatarImg = in.readString();
        this.password = in.readString();
        this.phoneNum = in.readString();
        this.teamId = in.readString();
        this.verificationCode = in.readString();
        this.adminEmail = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
