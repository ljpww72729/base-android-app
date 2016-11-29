package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinkedME06 on 16/11/28.
 */

public class ProjectAddResult implements Parcelable {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
    }

    public ProjectAddResult() {
    }

    protected ProjectAddResult(Parcel in) {
        this.status = in.readString();
    }

    public static final Creator<ProjectAddResult> CREATOR = new Creator<ProjectAddResult>() {
        @Override
        public ProjectAddResult createFromParcel(Parcel source) {
            return new ProjectAddResult(source);
        }

        @Override
        public ProjectAddResult[] newArray(int size) {
            return new ProjectAddResult[size];
        }
    };
}
