package com.ww.lp.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 轮播图列表
 * Created by LinkedME06 on 16/11/13.
 */

public class CarouselList implements Parcelable {
    private ArrayList<CarouselInfo> arrayList;

    public ArrayList<CarouselInfo> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<CarouselInfo> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.arrayList);
    }

    public CarouselList() {
    }

    protected CarouselList(Parcel in) {
        this.arrayList = in.createTypedArrayList(CarouselInfo.CREATOR);
    }

    public static final Creator<CarouselList> CREATOR = new Creator<CarouselList>() {
        @Override
        public CarouselList createFromParcel(Parcel source) {
            return new CarouselList(source);
        }

        @Override
        public CarouselList[] newArray(int size) {
            return new CarouselList[size];
        }
    };
}
