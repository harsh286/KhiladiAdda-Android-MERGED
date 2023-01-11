package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.clashx2.cricket.createbattle.KaPlayer;

import java.util.List;

public class MatchDetails implements Parcelable {

    @SerializedName("is_approved")
    @Expose
    private boolean isApproved;
    @SerializedName("is_live")
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
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("matchTypeId")
    @Expose
    private long matchTypeId;
    @SerializedName("series")
    @Expose
    private Series series;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("homeTeam")
    @Expose
    private Teams homeTeam;
    @SerializedName("awayTeam")
    @Expose
    private Teams awayTeam;
    @SerializedName("currentMatchState")
    @Expose
    private String currentMatchState;
    @SerializedName("isMultiDay")
    @Expose
    private boolean isMultiDay;
    @SerializedName("matchSummaryText")
    @Expose
    private String matchSummaryText;
    @SerializedName("isMatchDrawn")
    @Expose
    private boolean isMatchDrawn;
    @SerializedName("isMatchAbandoned")
    @Expose
    private boolean isMatchAbandoned;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("endDateTime")
    @Expose
    private String endDateTime;
    @SerializedName("isWomensMatch")
    @Expose
    private boolean isWomensMatch;
    @SerializedName("players")
    @Expose
    private TeamPlayers players;

    @SerializedName("ka_players")
    @Expose
    private List<KaPlayer> kaPlayers = null;
    @SerializedName("winnings")
    @Expose
    private double winning;
    @SerializedName("matchId")
    @Expose int matchId;
    private String score;

    protected MatchDetails(Parcel in) {
        isApproved = in.readByte() != 0;
        isLive = in.readByte() != 0;
        isClosed = in.readByte() != 0;
        isInReview = in.readByte() != 0;
        isResultDecalred = in.readByte() != 0;
        id = in.readString();
        matchTypeId = in.readLong();
        series = in.readParcelable(Series.class.getClassLoader());
        name = in.readString();
        status = in.readString();
        homeTeam = in.readParcelable(Teams.class.getClassLoader());
        awayTeam = in.readParcelable(Teams.class.getClassLoader());
        currentMatchState = in.readString();
        isMultiDay = in.readByte() != 0;
        matchSummaryText = in.readString();
        isMatchDrawn = in.readByte() != 0;
        isMatchAbandoned = in.readByte() != 0;
        startDateTime = in.readString();
        endDateTime = in.readString();
        isWomensMatch = in.readByte() != 0;
        players = in.readParcelable(TeamPlayers.class.getClassLoader());
        kaPlayers = in.createTypedArrayList(KaPlayer.CREATOR);
        winning = in.readDouble();
        matchId = in.readInt();
        score = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isApproved ? 1 : 0));
        dest.writeByte((byte) (isLive ? 1 : 0));
        dest.writeByte((byte) (isClosed ? 1 : 0));
        dest.writeByte((byte) (isInReview ? 1 : 0));
        dest.writeByte((byte) (isResultDecalred ? 1 : 0));
        dest.writeString(id);
        dest.writeLong(matchTypeId);
        dest.writeParcelable(series, flags);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeParcelable(homeTeam, flags);
        dest.writeParcelable(awayTeam, flags);
        dest.writeString(currentMatchState);
        dest.writeByte((byte) (isMultiDay ? 1 : 0));
        dest.writeString(matchSummaryText);
        dest.writeByte((byte) (isMatchDrawn ? 1 : 0));
        dest.writeByte((byte) (isMatchAbandoned ? 1 : 0));
        dest.writeString(startDateTime);
        dest.writeString(endDateTime);
        dest.writeByte((byte) (isWomensMatch ? 1 : 0));
        dest.writeParcelable(players, flags);
        dest.writeTypedList(kaPlayers);
        dest.writeDouble(winning);
        dest.writeInt(matchId);
        dest.writeString(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchDetails> CREATOR = new Creator<MatchDetails>() {
        @Override
        public MatchDetails createFromParcel(Parcel in) {
            return new MatchDetails(in);
        }

        @Override
        public MatchDetails[] newArray(int size) {
            return new MatchDetails[size];
        }
    };

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMatchTypeId() {
        return matchTypeId;
    }

    public void setMatchTypeId(long matchTypeId) {
        this.matchTypeId = matchTypeId;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Teams getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Teams homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Teams getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Teams awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getCurrentMatchState() {
        return currentMatchState;
    }

    public void setCurrentMatchState(String currentMatchState) {
        this.currentMatchState = currentMatchState;
    }

    public boolean isIsMultiDay() {
        return isMultiDay;
    }

    public void setIsMultiDay(boolean isMultiDay) {
        this.isMultiDay = isMultiDay;
    }

    public String getMatchSummaryText() {
        return matchSummaryText;
    }

    public void setMatchSummaryText(String matchSummaryText) {
        this.matchSummaryText = matchSummaryText;
    }

    public boolean isIsMatchDrawn() {
        return isMatchDrawn;
    }

    public void setIsMatchDrawn(boolean isMatchDrawn) {
        this.isMatchDrawn = isMatchDrawn;
    }

    public boolean isIsMatchAbandoned() {
        return isMatchAbandoned;
    }

    public void setIsMatchAbandoned(boolean isMatchAbandoned) {
        this.isMatchAbandoned = isMatchAbandoned;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isIsWomensMatch() {
        return isWomensMatch;
    }

    public void setIsWomensMatch(boolean isWomensMatch) {
        this.isWomensMatch = isWomensMatch;
    }

    public TeamPlayers getPlayers() {
        return players;
    }

    public void setPlayers(TeamPlayers players) {
        this.players = players;
    }

    public List<KaPlayer> getKaPlayers() {
        return kaPlayers;
    }

    public void setKaPlayers(List<KaPlayer> kaPlayers) {
        this.kaPlayers = kaPlayers;
    }

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}