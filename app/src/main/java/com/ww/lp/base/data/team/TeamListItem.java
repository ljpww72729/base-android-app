package com.ww.lp.base.data.team;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class TeamListItem implements Parcelable {

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

    private int pageCount;
    private int pageIndex;
    private int pageSize;

    public ArrayList<TeamInfo> getList() {
        return list;
    }

    public void setList(ArrayList<TeamInfo> list) {
        this.list = list;
    }

    private ArrayList<TeamInfo> list;

    public TeamListItem() {
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

    protected TeamListItem(Parcel in) {
        this.pageCount = in.readInt();
        this.pageIndex = in.readInt();
        this.pageSize = in.readInt();
        this.list = in.createTypedArrayList(TeamInfo.CREATOR);
    }

    public static final Creator<TeamListItem> CREATOR = new Creator<TeamListItem>() {
        @Override
        public TeamListItem createFromParcel(Parcel source) {
            return new TeamListItem(source);
        }

        @Override
        public TeamListItem[] newArray(int size) {
            return new TeamListItem[size];
        }
    };
}
