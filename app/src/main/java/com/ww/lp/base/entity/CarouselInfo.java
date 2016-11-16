package com.ww.lp.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class CarouselInfo implements Parcelable {
    //图片编号
    private String id;
    //图片地址
    private String imgUrl;
    //图片绑定点击时的跳转地址
    private String imgRedirectUrl;

    public String getImgRedirectUrl() {
        return imgRedirectUrl;
    }

    public void setImgRedirectUrl(String imgRedirectUrl) {
        this.imgRedirectUrl = imgRedirectUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public CarouselInfo(String id, String imgUrl, String imgRedirectUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.imgRedirectUrl = imgRedirectUrl;
    }

    private CarouselInfo(Parcel in) {
        id = in.readString();
        imgUrl = in.readString();
        imgRedirectUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imgUrl);
        dest.writeString(imgRedirectUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarouselInfo> CREATOR = new Creator<CarouselInfo>() {
        @Override
        public CarouselInfo createFromParcel(Parcel in) {
            return new CarouselInfo(in);
        }

        @Override
        public CarouselInfo[] newArray(int size) {
            return new CarouselInfo[size];
        }
    };
}
