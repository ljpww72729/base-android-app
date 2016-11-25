package com.ww.lp.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class CarouselInfo implements Parcelable {
    public CarouselInfo(String s, String s1, String s2) {
        this.id = s;
        this.img = s1;
        this.url = s2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    //图片编号
    private String id;
    //图片地址
    private String img;
    //图片绑定点击时的跳转地址
    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeString(this.url);
    }

    public CarouselInfo() {
    }

    protected CarouselInfo(Parcel in) {
        this.id = in.readString();
        this.img = in.readString();
        this.url = in.readString();
    }

    public static final Creator<CarouselInfo> CREATOR = new Creator<CarouselInfo>() {
        @Override
        public CarouselInfo createFromParcel(Parcel source) {
            return new CarouselInfo(source);
        }

        @Override
        public CarouselInfo[] newArray(int size) {
            return new CarouselInfo[size];
        }
    };
}
