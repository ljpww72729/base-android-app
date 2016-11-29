package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/29.
 */

public class TeamDetail implements Parcelable {
    private String img;
    private String Introduction;
    private String yearNum;
    private String positon;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getYearNum() {
        return yearNum;
    }

    public void setYearNum(String yearNum) {
        this.yearNum = yearNum;
    }

    public String getPositon() {
        return positon;
    }

    public void setPositon(String positon) {
        this.positon = positon;
    }

    private String username;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.Introduction);
        dest.writeString(this.yearNum);
        dest.writeString(this.positon);
        dest.writeString(this.username);
    }

    public TeamDetail() {
    }

    protected TeamDetail(Parcel in) {
        this.img = in.readString();
        this.Introduction = in.readString();
        this.yearNum = in.readString();
        this.positon = in.readString();
        this.username = in.readString();
    }

    public static final Creator<TeamDetail> CREATOR = new Creator<TeamDetail>() {
        @Override
        public TeamDetail createFromParcel(Parcel source) {
            return new TeamDetail(source);
        }

        @Override
        public TeamDetail[] newArray(int size) {
            return new TeamDetail[size];
        }
    };
}
