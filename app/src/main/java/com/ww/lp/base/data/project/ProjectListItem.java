package com.ww.lp.base.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 26/12/2016.
 */

public class ProjectListItem implements Parcelable {
    private int pageCount;
    private int pageIndex;
    private int pageSize;
    private ArrayList<ProjectInfo> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<ProjectInfo> getList() {
        return list;
    }

    public void setList(ArrayList<ProjectInfo> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageCount);
        dest.writeInt(this.pageIndex);
        dest.writeInt(this.pageSize);
        dest.writeTypedList(this.list);
    }

    public ProjectListItem() {
    }

    protected ProjectListItem(Parcel in) {
        this.pageCount = in.readInt();
        this.pageIndex = in.readInt();
        this.pageSize = in.readInt();
        this.list = in.createTypedArrayList(ProjectInfo.CREATOR);
    }

    public static final Creator<ProjectListItem> CREATOR = new Creator<ProjectListItem>() {
        @Override
        public ProjectListItem createFromParcel(Parcel source) {
            return new ProjectListItem(source);
        }

        @Override
        public ProjectListItem[] newArray(int size) {
            return new ProjectListItem[size];
        }
    };
}
