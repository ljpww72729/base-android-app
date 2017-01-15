package com.ww.lp.base.data.ads;

import android.os.Parcel;

import com.ww.lp.base.data.BaseResult;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 27/12/2016.
 */

public class AdsListResult extends BaseResult {
    public ArrayList<CarouselInfo> getData() {
        return data;
    }

    public void setData(ArrayList<CarouselInfo> data) {
        this.data = data;
    }

    private ArrayList<CarouselInfo> data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.data);
    }

    public AdsListResult() {
    }

    protected AdsListResult(Parcel in) {
        super(in);
        this.data = in.createTypedArrayList(CarouselInfo.CREATOR);
    }

    public static final Creator<AdsListResult> CREATOR = new Creator<AdsListResult>() {
        @Override
        public AdsListResult createFromParcel(Parcel source) {
            return new AdsListResult(source);
        }

        @Override
        public AdsListResult[] newArray(int size) {
            return new AdsListResult[size];
        }
    };
}
