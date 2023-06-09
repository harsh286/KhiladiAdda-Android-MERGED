package com.khiladiadda.network.model.response.ludoTournament;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtMyMatchResponse implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nParticipants")
    @Expose
    private Integer nParticipants;
    @SerializedName("nParticipated")
    @Expose
    private Integer nParticipated;
    @SerializedName("tMode")
    @Expose
    private Integer tMode;
    @SerializedName("ttLevel")
    @Expose
    private Integer ttLevel;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("entryFees")
    @Expose
    private Integer entryFees;
    @SerializedName("prize")
    @Expose
    private Integer prize;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("tStatus")
    @Expose
    private int tStatus;
    @SerializedName("tType")
    @Expose
    private Integer tType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getnParticipants() {
        return nParticipants;
    }

    public void setnParticipants(Integer nParticipants) {
        this.nParticipants = nParticipants;
    }

    public Integer getnParticipated() {
        return nParticipated;
    }

    public void setnParticipated(Integer nParticipated) {
        this.nParticipated = nParticipated;
    }

    public Integer gettMode() {
        return tMode;
    }

    public void settMode(Integer tMode) {
        this.tMode = tMode;
    }

    public Integer getTtLevel() {
        return ttLevel;
    }

    public void setTtLevel(Integer ttLevel) {
        this.ttLevel = ttLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int gettStatus() {
        return tStatus;
    }

    public void settStatus(int tStatus) {
        this.tStatus = tStatus;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.nParticipants);
        dest.writeValue(this.nParticipated);
        dest.writeValue(this.tMode);
        dest.writeValue(this.ttLevel);
        dest.writeString(this.name);
        dest.writeValue(this.entryFees);
        dest.writeValue(this.prize);
        dest.writeString(this.startDate);
        dest.writeInt(this.tStatus);
        dest.writeValue(this.tType);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.nParticipants = (Integer) source.readValue(Integer.class.getClassLoader());
        this.nParticipated = (Integer) source.readValue(Integer.class.getClassLoader());
        this.tMode = (Integer) source.readValue(Integer.class.getClassLoader());
        this.ttLevel = (Integer) source.readValue(Integer.class.getClassLoader());
        this.name = source.readString();
        this.entryFees = (Integer) source.readValue(Integer.class.getClassLoader());
        this.prize = (Integer) source.readValue(Integer.class.getClassLoader());
        this.startDate = source.readString();
        this.tStatus = source.readInt();
        this.tType = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public LudoTmtMyMatchResponse() {
    }

    protected LudoTmtMyMatchResponse(Parcel in) {
        this.id = in.readString();
        this.nParticipants = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nParticipated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tMode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ttLevel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.entryFees = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startDate = in.readString();
        this.tStatus = in.readInt();
        this.tType = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<LudoTmtMyMatchResponse> CREATOR = new Creator<LudoTmtMyMatchResponse>() {
        @Override
        public LudoTmtMyMatchResponse createFromParcel(Parcel source) {
            return new LudoTmtMyMatchResponse(source);
        }

        @Override
        public LudoTmtMyMatchResponse[] newArray(int size) {
            return new LudoTmtMyMatchResponse[size];
        }
    };
}