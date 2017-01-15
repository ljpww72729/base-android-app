package com.ww.lp.base.data.project;

import android.os.Parcel;

import com.ww.lp.base.data.BaseResult;

/**
 * Created by LinkedME06 on 26/12/2016.
 */

public class ProjectListResult extends BaseResult {
    public ProjectListItem getData() {
        return data;
    }

    public void setData(ProjectListItem data) {
        this.data = data;
    }

    private ProjectListItem data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.data, flags);
    }

    public ProjectListResult() {
    }

    protected ProjectListResult(Parcel in) {
        super(in);
        this.data = in.readParcelable(ProjectListItem.class.getClassLoader());
    }

    public static final Creator<ProjectListResult> CREATOR = new Creator<ProjectListResult>() {
        @Override
        public ProjectListResult createFromParcel(Parcel source) {
            return new ProjectListResult(source);
        }

        @Override
        public ProjectListResult[] newArray(int size) {
            return new ProjectListResult[size];
        }
    };
}
