package com.ww.lp.base.data.team;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 25/12/2016.
 */

public class TeamInfo implements Parcelable {
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public ArrayList<TeamImg> getTeamImgs() {
        return teamImgs;
    }

    public void setTeamImgs(ArrayList<TeamImg> teamImgs) {
        this.teamImgs = teamImgs;
    }

    public ArrayList<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    private String teamName;
    private String teamId;
    private int star;
    private ArrayList<TeamImg> teamImgs;
    private ArrayList<TeamMember> teamMembers;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teamName);
        dest.writeString(this.teamId);
        dest.writeInt(this.star);
        dest.writeTypedList(this.teamImgs);
        dest.writeTypedList(this.teamMembers);
    }

    public TeamInfo() {
    }

    protected TeamInfo(Parcel in) {
        this.teamName = in.readString();
        this.teamId = in.readString();
        this.star = in.readInt();
        this.teamImgs = in.createTypedArrayList(TeamImg.CREATOR);
        this.teamMembers = in.createTypedArrayList(TeamMember.CREATOR);
    }

    public static final Creator<TeamInfo> CREATOR = new Creator<TeamInfo>() {
        @Override
        public TeamInfo createFromParcel(Parcel source) {
            return new TeamInfo(source);
        }

        @Override
        public TeamInfo[] newArray(int size) {
            return new TeamInfo[size];
        }
    };
}
