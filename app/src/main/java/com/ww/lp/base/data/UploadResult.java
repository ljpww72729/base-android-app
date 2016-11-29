package com.ww.lp.base.data;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/28.
 */

public class UploadResult implements Parcelable {
    private String status;

    public CarouselInfo getData() {
        return data;
    }

    public void setData(CarouselInfo data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("data")
    private CarouselInfo data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public UploadResult() {
    }

    protected UploadResult(Parcel in) {
        this.status = in.readString();
        this.data = in.readParcelable(CarouselInfo.class.getClassLoader());
    }

    public static final Creator<UploadResult> CREATOR = new Creator<UploadResult>() {
        @Override
        public UploadResult createFromParcel(Parcel source) {
            return new UploadResult(source);
        }

        @Override
        public UploadResult[] newArray(int size) {
            return new UploadResult[size];
        }
    };
}
