package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CxKbPoints implements Parcelable {
    @SerializedName("success_raid")
    @Expose
    private Integer successRaid;
    @SerializedName("success_tackle")
    @Expose
    private Integer successTackle;
    @SerializedName("raid_bonus")
    @Expose
    private Integer raidBonus;
    @SerializedName("super_tackle")
    @Expose
    private Integer superTackle;
    @SerializedName("super_raid")
    @Expose
    private Integer superRaid;
    @SerializedName("super_ten_raid")
    @Expose
    private Integer superTenRaid;
    @SerializedName("high_five_defends")
    @Expose
    private Integer highFiveDefends;
    @SerializedName("playing_seven")
    @Expose
    private Integer playingSeven;
    @SerializedName("substitue")
    @Expose
    private Integer substitue;
    @SerializedName("unsuccess_raid")
    @Expose
    private Integer unsuccessRaid;
    @SerializedName("unsuccess_tackle")
    @Expose
    private Integer unsuccessTackle;
    @SerializedName("opposition_all_out")
    @Expose
    private Integer oppositionAllOut;
    @SerializedName("green_card")
    @Expose
    private Integer greenCard;
    @SerializedName("yellow_card")
    @Expose
    private Integer yellowCard;
    @SerializedName("red_card")
    @Expose
    private Integer redCard;

    public Integer getSuccessRaid() {
        return successRaid;
    }

    public void setSuccessRaid(Integer successRaid) {
        this.successRaid = successRaid;
    }

    public Integer getSuccessTackle() {
        return successTackle;
    }

    public void setSuccessTackle(Integer successTackle) {
        this.successTackle = successTackle;
    }

    public Integer getRaidBonus() {
        return raidBonus;
    }

    public void setRaidBonus(Integer raidBonus) {
        this.raidBonus = raidBonus;
    }

    public Integer getSuperTackle() {
        return superTackle;
    }

    public void setSuperTackle(Integer superTackle) {
        this.superTackle = superTackle;
    }

    public Integer getSuperRaid() {
        return superRaid;
    }

    public void setSuperRaid(Integer superRaid) {
        this.superRaid = superRaid;
    }

    public Integer getSuperTenRaid() {
        return superTenRaid;
    }

    public void setSuperTenRaid(Integer superTenRaid) {
        this.superTenRaid = superTenRaid;
    }

    public Integer getHighFiveDefends() {
        return highFiveDefends;
    }

    public void setHighFiveDefends(Integer highFiveDefends) {
        this.highFiveDefends = highFiveDefends;
    }

    public Integer getPlayingSeven() {
        return playingSeven;
    }

    public void setPlayingSeven(Integer playingSeven) {
        this.playingSeven = playingSeven;
    }

    public Integer getSubstitue() {
        return substitue;
    }

    public void setSubstitue(Integer substitue) {
        this.substitue = substitue;
    }

    public Integer getUnsuccessRaid() {
        return unsuccessRaid;
    }

    public void setUnsuccessRaid(Integer unsuccessRaid) {
        this.unsuccessRaid = unsuccessRaid;
    }

    public Integer getUnsuccessTackle() {
        return unsuccessTackle;
    }

    public void setUnsuccessTackle(Integer unsuccessTackle) {
        this.unsuccessTackle = unsuccessTackle;
    }

    public Integer getOppositionAllOut() {
        return oppositionAllOut;
    }

    public void setOppositionAllOut(Integer oppositionAllOut) {
        this.oppositionAllOut = oppositionAllOut;
    }

    public Integer getGreenCard() {
        return greenCard;
    }

    public void setGreenCard(Integer greenCard) {
        this.greenCard = greenCard;
    }

    public Integer getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(Integer yellowCard) {
        this.yellowCard = yellowCard;
    }

    public Integer getRedCard() {
        return redCard;
    }

    public void setRedCard(Integer redCard) {
        this.redCard = redCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.successRaid);
        dest.writeValue(this.successTackle);
        dest.writeValue(this.raidBonus);
        dest.writeValue(this.superTackle);
        dest.writeValue(this.superRaid);
        dest.writeValue(this.superTenRaid);
        dest.writeValue(this.highFiveDefends);
        dest.writeValue(this.playingSeven);
        dest.writeValue(this.substitue);
        dest.writeValue(this.unsuccessRaid);
        dest.writeValue(this.unsuccessTackle);
        dest.writeValue(this.oppositionAllOut);
        dest.writeValue(this.greenCard);
        dest.writeValue(this.yellowCard);
        dest.writeValue(this.redCard);
    }

    public void readFromParcel(Parcel source) {
        this.successRaid = (Integer) source.readValue(Integer.class.getClassLoader());
        this.successTackle = (Integer) source.readValue(Integer.class.getClassLoader());
        this.raidBonus = (Integer) source.readValue(Integer.class.getClassLoader());
        this.superTackle = (Integer) source.readValue(Integer.class.getClassLoader());
        this.superRaid = (Integer) source.readValue(Integer.class.getClassLoader());
        this.superTenRaid = (Integer) source.readValue(Integer.class.getClassLoader());
        this.highFiveDefends = (Integer) source.readValue(Integer.class.getClassLoader());
        this.playingSeven = (Integer) source.readValue(Integer.class.getClassLoader());
        this.substitue = (Integer) source.readValue(Integer.class.getClassLoader());
        this.unsuccessRaid = (Integer) source.readValue(Integer.class.getClassLoader());
        this.unsuccessTackle = (Integer) source.readValue(Integer.class.getClassLoader());
        this.oppositionAllOut = (Integer) source.readValue(Integer.class.getClassLoader());
        this.greenCard = (Integer) source.readValue(Integer.class.getClassLoader());
        this.yellowCard = (Integer) source.readValue(Integer.class.getClassLoader());
        this.redCard = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public CxKbPoints() {
    }

    protected CxKbPoints(Parcel in) {
        this.successRaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.successTackle = (Integer) in.readValue(Integer.class.getClassLoader());
        this.raidBonus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.superTackle = (Integer) in.readValue(Integer.class.getClassLoader());
        this.superRaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.superTenRaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.highFiveDefends = (Integer) in.readValue(Integer.class.getClassLoader());
        this.playingSeven = (Integer) in.readValue(Integer.class.getClassLoader());
        this.substitue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.unsuccessRaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.unsuccessTackle = (Integer) in.readValue(Integer.class.getClassLoader());
        this.oppositionAllOut = (Integer) in.readValue(Integer.class.getClassLoader());
        this.greenCard = (Integer) in.readValue(Integer.class.getClassLoader());
        this.yellowCard = (Integer) in.readValue(Integer.class.getClassLoader());
        this.redCard = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<CxKbPoints> CREATOR = new Creator<CxKbPoints>() {
        @Override
        public CxKbPoints createFromParcel(Parcel source) {
            return new CxKbPoints(source);
        }

        @Override
        public CxKbPoints[] newArray(int size) {
            return new CxKbPoints[size];
        }
    };
}
