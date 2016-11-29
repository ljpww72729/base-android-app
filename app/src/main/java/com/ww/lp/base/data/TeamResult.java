package com.ww.lp.base.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/29.
 */

public class TeamResult implements Parcelable {

    private String teamName;
    private ArrayList<TeamDetail> TeamDetail;


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<com.ww.lp.base.data.TeamDetail> getTeamDetail() {
        return TeamDetail;
    }

    public void setTeamDetail(ArrayList<com.ww.lp.base.data.TeamDetail> teamDetail) {
        TeamDetail = teamDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teamName);
        dest.writeTypedList(this.TeamDetail);
    }

    public TeamResult() {
    }

    protected TeamResult(Parcel in) {
        this.teamName = in.readString();
        this.TeamDetail = in.createTypedArrayList(com.ww.lp.base.data.TeamDetail.CREATOR);
    }

    public static final Creator<TeamResult> CREATOR = new Creator<TeamResult>() {
        @Override
        public TeamResult createFromParcel(Parcel source) {
            return new TeamResult(source);
        }

        @Override
        public TeamResult[] newArray(int size) {
            return new TeamResult[size];
        }
    };
}
