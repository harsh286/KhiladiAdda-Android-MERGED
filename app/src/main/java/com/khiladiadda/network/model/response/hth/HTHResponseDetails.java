package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HTHResponseDetails implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("endDateTime")
    @Expose
    private String endDateTime;
    @SerializedName("series")
    @Expose
    private SeriesHTH series;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("venue")
    @Expose
    private VenueHTH venue;
    @SerializedName("ka_players")
    @Expose
    private List<KaPlayerHTH> kaPlayers = null;
    @SerializedName("players")
    @Expose
    private PlayersHTH players;
    @SerializedName("is_approved")
    @Expose
    private boolean isApproved;
    @SerializedName("is_clash_live")
    @Expose
    private boolean isLive;
    @SerializedName("is_closed")
    @Expose
    private boolean isClosed;
    @SerializedName("is_in_review")
    @Expose
    private boolean isInReview;
    @SerializedName("is_result_declared")
    @Expose
    private boolean isResultDecalred;
    @SerializedName("highestBattle")
    @Expose
    private double HighestBattle;
    @SerializedName("is_lines_up")
    @Expose
    private boolean is_lines_up;
    @SerializedName("is_cancelled")
    private boolean is_cancelled;
    @SerializedName("battle_type")
    private boolean battle_type;
    @SerializedName("match_type")
    private int match_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public SeriesHTH getSeries() {
        return series;
    }

    public void setSeries(SeriesHTH series) {
        this.series = series;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public VenueHTH getVenue() {
        return venue;
    }

    public void setVenue(VenueHTH venue) {
        this.venue = venue;
    }

    public List<KaPlayerHTH> getKaPlayers() {
        return kaPlayers;
    }

    public void setKaPlayers(List<KaPlayerHTH> kaPlayers) {
        this.kaPlayers = kaPlayers;
    }

    public PlayersHTH getPlayers() {
        return players;
    }

    public void setPlayers(PlayersHTH players) {
        this.players = players;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isInReview() {
        return isInReview;
    }

    public void setInReview(boolean inReview) {
        isInReview = inReview;
    }

    public boolean isResultDecalred() {
        return isResultDecalred;
    }

    public void setResultDecalred(boolean resultDecalred) {
        isResultDecalred = resultDecalred;
    }

    public double getHighestBattle() {
        return HighestBattle;
    }

    public void setHighestBattle(double highestBattle) {
        HighestBattle = highestBattle;
    }

    public boolean isIs_lines_up() {
        return is_lines_up;
    }

    public void setIs_lines_up(boolean is_lines_up) {
        this.is_lines_up = is_lines_up;
    }

    public boolean isIs_cancelled() {
        return is_cancelled;
    }

    public void setIs_cancelled(boolean is_cancelled) {
        this.is_cancelled = is_cancelled;
    }

    public boolean isBattle_type() {
        return battle_type;
    }

    public void setBattle_type(boolean battle_type) {
        this.battle_type = battle_type;
    }

    public int getMatch_type() {
        return match_type;
    }

    public void setMatch_type(int match_type) {
        this.match_type = match_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.endDateTime);
        dest.writeParcelable(this.series, flags);
        dest.writeString(this.startDateTime);
        dest.writeParcelable(this.venue, flags);
        dest.writeTypedList(this.kaPlayers);
        dest.writeParcelable(this.players, flags);
        dest.writeByte(this.isApproved ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isClosed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isInReview ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isResultDecalred ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.HighestBattle);
        dest.writeByte(this.is_lines_up ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_cancelled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.battle_type ? (byte) 1 : (byte) 0);
        dest.writeInt(this.match_type);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.endDateTime = source.readString();
        this.series = source.readParcelable(SeriesHTH.class.getClassLoader());
        this.startDateTime = source.readString();
        this.venue = source.readParcelable(VenueHTH.class.getClassLoader());
        this.kaPlayers = source.createTypedArrayList(KaPlayerHTH.CREATOR);
        this.players = source.readParcelable(PlayersHTH.class.getClassLoader());
        this.isApproved = source.readByte() != 0;
        this.isLive = source.readByte() != 0;
        this.isClosed = source.readByte() != 0;
        this.isInReview = source.readByte() != 0;
        this.isResultDecalred = source.readByte() != 0;
        this.HighestBattle = source.readDouble();
        this.is_lines_up = source.readByte() != 0;
        this.is_cancelled = source.readByte() != 0;
        this.battle_type = source.readByte() != 0;
        this.match_type = source.readInt();
    }

    public HTHResponseDetails() {
    }

    protected HTHResponseDetails(Parcel in) {
        this.id = in.readString();
        this.endDateTime = in.readString();
        this.series = in.readParcelable(SeriesHTH.class.getClassLoader());
        this.startDateTime = in.readString();
        this.venue = in.readParcelable(VenueHTH.class.getClassLoader());
        this.kaPlayers = in.createTypedArrayList(KaPlayerHTH.CREATOR);
        this.players = in.readParcelable(PlayersHTH.class.getClassLoader());
        this.isApproved = in.readByte() != 0;
        this.isLive = in.readByte() != 0;
        this.isClosed = in.readByte() != 0;
        this.isInReview = in.readByte() != 0;
        this.isResultDecalred = in.readByte() != 0;
        this.HighestBattle = in.readDouble();
        this.is_lines_up = in.readByte() != 0;
        this.is_cancelled = in.readByte() != 0;
        this.battle_type = in.readByte() != 0;
        this.match_type = in.readInt();
    }

    public static final Creator<HTHResponseDetails> CREATOR = new Creator<HTHResponseDetails>() {
        @Override
        public HTHResponseDetails createFromParcel(Parcel source) {
            return new HTHResponseDetails(source);
        }

        @Override
        public HTHResponseDetails[] newArray(int size) {
            return new HTHResponseDetails[size];
        }
    };
}