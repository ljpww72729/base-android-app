package com.ww.lp.base.entity;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import com.ww.lp.base.data.CarouselInfo;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/27.
 */

public class ProjectPostInfo implements Parcelable{
    @SerializedName("imgs")
    private ArrayList<CarouselInfo> imgs;
    private String userEmail;
    private String flag;
    private String token;
    private String projectId;
    private String title;
    private String price;
    private String status;
    private String phoneNum;
    private String describe;
    private String img;

    public ArrayList<CarouselInfo> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<CarouselInfo> imgs) {
        this.imgs = imgs;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.imgs);
        dest.writeString(this.userEmail);
        dest.writeString(this.flag);
        dest.writeString(this.token);
        dest.writeString(this.projectId);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.status);
        dest.writeString(this.phoneNum);
        dest.writeString(this.describe);
        dest.writeString(this.img);
    }

    public ProjectPostInfo() {
    }

    protected ProjectPostInfo(Parcel in) {
        this.imgs = in.createTypedArrayList(CarouselInfo.CREATOR);
        this.userEmail = in.readString();
        this.flag = in.readString();
        this.token = in.readString();
        this.projectId = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.status = in.readString();
        this.phoneNum = in.readString();
        this.describe = in.readString();
        this.img = in.readString();
    }

    public static final Creator<ProjectPostInfo> CREATOR = new Creator<ProjectPostInfo>() {
        @Override
        public ProjectPostInfo createFromParcel(Parcel source) {
            return new ProjectPostInfo(source);
        }

        @Override
        public ProjectPostInfo[] newArray(int size) {
            return new ProjectPostInfo[size];
        }
    };
}
