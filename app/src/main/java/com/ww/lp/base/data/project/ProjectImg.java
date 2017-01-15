package com.ww.lp.base.data.project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class ProjectImg implements Parcelable {
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.url);
    }

    public ProjectImg() {
    }

    protected ProjectImg(Parcel in) {
        this.img = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ProjectImg> CREATOR = new Creator<ProjectImg>() {
        @Override
        public ProjectImg createFromParcel(Parcel source) {
            return new ProjectImg(source);
        }

        @Override
        public ProjectImg[] newArray(int size) {
            return new ProjectImg[size];
        }
    };
}
