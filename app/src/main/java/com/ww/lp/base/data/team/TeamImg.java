package com.ww.lp.base.data.team;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class TeamImg implements Parcelable {
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    private String imgId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.imgId);
        dest.writeString(this.url);
    }

    public TeamImg() {
    }

    protected TeamImg(Parcel in) {
        this.img = in.readString();
        this.imgId = in.readString();
        this.url = in.readString();
    }

    public static final Creator<TeamImg> CREATOR = new Creator<TeamImg>() {
        @Override
        public TeamImg createFromParcel(Parcel source) {
            return new TeamImg(source);
        }

        @Override
        public TeamImg[] newArray(int size) {
            return new TeamImg[size];
        }
    };
}
