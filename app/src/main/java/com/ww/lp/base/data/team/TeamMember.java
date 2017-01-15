package com.ww.lp.base.data.team;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class TeamMember implements Parcelable {

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYearNum() {
        return yearNum;
    }

    public void setYearNum(String yearNum) {
        this.yearNum = yearNum;
    }

    private String img;
    private String introduction;
    private String memberId;
    private String position;
    private String username;
    private String yearNum;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.introduction);
        dest.writeString(this.memberId);
        dest.writeString(this.position);
        dest.writeString(this.username);
        dest.writeString(this.yearNum);
    }

    public TeamMember() {
    }

    protected TeamMember(Parcel in) {
        this.img = in.readString();
        this.introduction = in.readString();
        this.memberId = in.readString();
        this.position = in.readString();
        this.username = in.readString();
        this.yearNum = in.readString();
    }

    public static final Creator<TeamMember> CREATOR = new Creator<TeamMember>() {
        @Override
        public TeamMember createFromParcel(Parcel source) {
            return new TeamMember(source);
        }

        @Override
        public TeamMember[] newArray(int size) {
            return new TeamMember[size];
        }
    };
}
