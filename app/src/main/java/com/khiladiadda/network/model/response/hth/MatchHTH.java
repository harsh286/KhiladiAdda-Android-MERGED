package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchHTH implements Parcelable {
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
    @SerializedName("highestBattle")
    @Expose
    private Integer highestBattle;

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

    public Integer getHighestBattle() {
        return highestBattle;
    }

    public void setHighestBattle(Integer highestBattle) {
        this.highestBattle = highestBattle;
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
        dest.writeValue(this.highestBattle);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.endDateTime = source.readString();
        this.series = source.readParcelable(SeriesHTH.class.getClassLoader());
        this.startDateTime = source.readString();
        this.venue = source.readParcelable(VenueHTH.class.getClassLoader());
        this.kaPlayers = source.createTypedArrayList(KaPlayerHTH.CREATOR);
        this.players = source.readParcelable(PlayersHTH.class.getClassLoader());
        this.highestBattle = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public MatchHTH() {
    }

    protected MatchHTH(Parcel in) {
        this.id = in.readString();
        this.endDateTime = in.readString();
        this.series = in.readParcelable(SeriesHTH.class.getClassLoader());
        this.startDateTime = in.readString();
        this.venue = in.readParcelable(VenueHTH.class.getClassLoader());
        this.kaPlayers = in.createTypedArrayList(KaPlayerHTH.CREATOR);
        this.players = in.readParcelable(PlayersHTH.class.getClassLoader());
        this.highestBattle = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MatchHTH> CREATOR = new Parcelable.Creator<MatchHTH>() {
        @Override
        public MatchHTH createFromParcel(Parcel source) {
            return new MatchHTH(source);
        }

        @Override
        public MatchHTH[] newArray(int size) {
            return new MatchHTH[size];
        }
    };
}
