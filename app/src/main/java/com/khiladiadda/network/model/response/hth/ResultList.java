package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultList implements Parcelable {
    @SerializedName("captain")
    @Expose
    private List<CaptainResultHTH> captain = null;
    @SerializedName("opponent")
    @Expose
    private List<CaptainResultHTH> opponent = null;
    @SerializedName("captainPoints")
    @Expose
    private int captainPoints;
    @SerializedName("opponentPoints")
    @Expose
    private int opponentPoints;

    public List<CaptainResultHTH> getCaptain() {
        return captain;
    }

    public void setCaptain(List<CaptainResultHTH> captain) {
        this.captain = captain;
    }

    public List<CaptainResultHTH> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<CaptainResultHTH> opponent) {
        this.opponent = opponent;
    }

    public int getCaptainPoints() {
        return captainPoints;
    }

    public void setCaptainPoints(int captainPoints) {
        this.captainPoints = captainPoints;
    }

    public int getOpponentPoints() {
        return opponentPoints;
    }

    public void setOpponentPoints(int opponentPoints) {
        this.opponentPoints = opponentPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.captain);
        dest.writeList(this.opponent);
        dest.writeInt(this.captainPoints);
        dest.writeInt(this.opponentPoints);
    }

    public void readFromParcel(Parcel source) {
        this.captain = new ArrayList<CaptainResultHTH>();
        source.readList(this.captain, CaptainResultHTH.class.getClassLoader());
        this.opponent = new ArrayList<CaptainResultHTH>();
        source.readList(this.opponent, CaptainResultHTH.class.getClassLoader());
        this.captainPoints = source.readInt();
        this.opponentPoints = source.readInt();
    }

    public ResultList() {
    }

    protected ResultList(Parcel in) {
        this.captain = new ArrayList<CaptainResultHTH>();
        in.readList(this.captain, CaptainResultHTH.class.getClassLoader());
        this.opponent = new ArrayList<CaptainResultHTH>();
        in.readList(this.opponent, CaptainResultHTH.class.getClassLoader());
        this.captainPoints = in.readInt();
        this.opponentPoints = in.readInt();
    }

    public static final Parcelable.Creator<ResultList> CREATOR = new Parcelable.Creator<ResultList>() {
        @Override
        public ResultList createFromParcel(Parcel source) {
            return new ResultList(source);
        }

        @Override
        public ResultList[] newArray(int size) {
            return new ResultList[size];
        }
    };
}
