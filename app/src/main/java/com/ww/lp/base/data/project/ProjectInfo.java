package com.ww.lp.base.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 26/12/2016.
 */

public class ProjectInfo implements Parcelable {
    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ArrayList<ProjectImg> getProjectImgs() {
        return projectImgs;
    }

    public void setProjectImgs(ArrayList<ProjectImg> projectImgs) {
        this.projectImgs = projectImgs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    private String describes;
    private String img;
    private String phoneNum;
    private String price;
    private String projectId;
    private ArrayList<ProjectImg> projectImgs;
    private int status;
    private String tittle;

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    private int classify = 4;

    public ProjectInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.describes);
        dest.writeString(this.img);
        dest.writeString(this.phoneNum);
        dest.writeString(this.price);
        dest.writeString(this.projectId);
        dest.writeTypedList(this.projectImgs);
        dest.writeInt(this.status);
        dest.writeString(this.tittle);
        dest.writeInt(this.classify);
    }

    protected ProjectInfo(Parcel in) {
        this.describes = in.readString();
        this.img = in.readString();
        this.phoneNum = in.readString();
        this.price = in.readString();
        this.projectId = in.readString();
        this.projectImgs = in.createTypedArrayList(ProjectImg.CREATOR);
        this.status = in.readInt();
        this.tittle = in.readString();
        this.classify = in.readInt();
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
