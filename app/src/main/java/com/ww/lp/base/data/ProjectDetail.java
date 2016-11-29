package com.ww.lp.base.data;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/26.
 */

public class ProjectDetail implements Parcelable {

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public ArrayList<CarouselInfo> getCarouselInfo() {
        return carouselInfo;
    }

    public void setCarouselInfo(ArrayList<CarouselInfo> carouselInfo) {
        this.carouselInfo = carouselInfo;
    }

    @SerializedName("project_info")
    private ProjectInfo projectInfo;
    @SerializedName("project_img")
    private ArrayList<CarouselInfo> carouselInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.projectInfo, flags);
        dest.writeTypedList(this.carouselInfo);
    }

    public ProjectDetail() {
    }

    protected ProjectDetail(Parcel in) {
        this.projectInfo = in.readParcelable(ProjectInfo.class.getClassLoader());
        this.carouselInfo = in.createTypedArrayList(CarouselInfo.CREATOR);
    }

    public static final Creator<ProjectDetail> CREATOR = new Creator<ProjectDetail>() {
        @Override
        public ProjectDetail createFromParcel(Parcel source) {
            return new ProjectDetail(source);
        }

        @Override
        public ProjectDetail[] newArray(int size) {
            return new ProjectDetail[size];
        }
    };
}
