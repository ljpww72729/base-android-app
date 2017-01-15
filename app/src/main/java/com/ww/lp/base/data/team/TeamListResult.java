package com.ww.lp.base.data.team;

import android.os.Parcel;

import com.ww.lp.base.data.BaseResult;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class TeamListResult extends BaseResult {
    public TeamListItem getData() {
        return data;
    }

    public void setData(TeamListItem data) {
        this.data = data;
    }

    private TeamListItem data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.data, flags);
    }

    public TeamListResult() {
    }

    protected TeamListResult(Parcel in) {
        super(in);
        this.data = in.readParcelable(TeamListItem.class.getClassLoader());
    }

    public static final Creator<TeamListResult> CREATOR = new Creator<TeamListResult>() {
        @Override
        public TeamListResult createFromParcel(Parcel source) {
            return new TeamListResult(source);
        }

        @Override
        public TeamListResult[] newArray(int size) {
            return new TeamListResult[size];
        }
    };
}
