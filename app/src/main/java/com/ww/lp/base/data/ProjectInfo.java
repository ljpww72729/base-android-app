package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目列表
 *
 * Created by LinkedME06 on 16/11/26.
 */

public class ProjectInfo implements Parcelable {

    private String projectId;
    private String title;
    private String price;
    private String status;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    private String phoneNum;
    private String describe;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String img;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectId);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.status);
        dest.writeString(this.phoneNum);
        dest.writeString(this.describe);
        dest.writeString(this.img);
    }

    public ProjectInfo() {
    }

    protected ProjectInfo(Parcel in) {
        this.projectId = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.status = in.readString();
        this.phoneNum = in.readString();
        this.describe = in.readString();
        this.img = in.readString();
    }

    public static final Creator<ProjectInfo> CREATOR = new Creator<ProjectInfo>() {
        @Override
        public ProjectInfo createFromParcel(Parcel source) {
            return new ProjectInfo(source);
        }

        @Override
        public ProjectInfo[] newArray(int size) {
            return new ProjectInfo[size];
        }
    };
}
