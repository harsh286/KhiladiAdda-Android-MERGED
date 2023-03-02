package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CxFbScore implements Parcelable {
    @SerializedName("attack_goal")
    @Expose
    private Integer attackGoal;
    @SerializedName("attack_assist")
    @Expose
    private Integer attackAssist;
    @SerializedName("attack_five_pass")
    @Expose
    private Integer attackFivePass;
    @SerializedName("attack_two_shots")
    @Expose
    private Integer attackTwoShots;
    @SerializedName("defence_tackle_won")
    @Expose
    private Integer defenceTackleWon;
    @SerializedName("defence_interception_won")
    @Expose
    private Integer defenceInterceptionWon;
    @SerializedName("defence_save_goalkeeper")
    @Expose
    private Integer defenceSaveGoalkeeper;
    @SerializedName("defence_penality_saved")
    @Expose
    private Integer defencePenalitySaved;
    @SerializedName("bonus_point_super_sixty")
    @Expose
    private Integer bonusPointSuperSixty;
    @SerializedName("penalities_yellow_card")
    @Expose
    private Integer penalitiesYellowCard;
    @SerializedName("penalities_red_card")
    @Expose
    private Integer penalitiesRedCard;
    @SerializedName("penalities_own_goal")
    @Expose
    private Integer penalitiesOwnGoal;

    public Integer getAttackGoal() {
        return attackGoal;
    }

    public void setAttackGoal(Integer attackGoal) {
        this.attackGoal = attackGoal;
    }

    public Integer getAttackAssist() {
        return attackAssist;
    }

    public void setAttackAssist(Integer attackAssist) {
        this.attackAssist = attackAssist;
    }

    public Integer getAttackFivePass() {
        return attackFivePass;
    }

    public void setAttackFivePass(Integer attackFivePass) {
        this.attackFivePass = attackFivePass;
    }

    public Integer getAttackTwoShots() {
        return attackTwoShots;
    }

    public void setAttackTwoShots(Integer attackTwoShots) {
        this.attackTwoShots = attackTwoShots;
    }

    public Integer getDefenceTackleWon() {
        return defenceTackleWon;
    }

    public void setDefenceTackleWon(Integer defenceTackleWon) {
        this.defenceTackleWon = defenceTackleWon;
    }

    public Integer getDefenceInterceptionWon() {
        return defenceInterceptionWon;
    }

    public void setDefenceInterceptionWon(Integer defenceInterceptionWon) {
        this.defenceInterceptionWon = defenceInterceptionWon;
    }

    public Integer getDefenceSaveGoalkeeper() {
        return defenceSaveGoalkeeper;
    }

    public void setDefenceSaveGoalkeeper(Integer defenceSaveGoalkeeper) {
        this.defenceSaveGoalkeeper = defenceSaveGoalkeeper;
    }

    public Integer getDefencePenalitySaved() {
        return defencePenalitySaved;
    }

    public void setDefencePenalitySaved(Integer defencePenalitySaved) {
        this.defencePenalitySaved = defencePenalitySaved;
    }

    public Integer getBonusPointSuperSixty() {
        return bonusPointSuperSixty;
    }

    public void setBonusPointSuperSixty(Integer bonusPointSuperSixty) {
        this.bonusPointSuperSixty = bonusPointSuperSixty;
    }

    public Integer getPenalitiesYellowCard() {
        return penalitiesYellowCard;
    }

    public void setPenalitiesYellowCard(Integer penalitiesYellowCard) {
        this.penalitiesYellowCard = penalitiesYellowCard;
    }

    public Integer getPenalitiesRedCard() {
        return penalitiesRedCard;
    }

    public void setPenalitiesRedCard(Integer penalitiesRedCard) {
        this.penalitiesRedCard = penalitiesRedCard;
    }

    public Integer getPenalitiesOwnGoal() {
        return penalitiesOwnGoal;
    }

    public void setPenalitiesOwnGoal(Integer penalitiesOwnGoal) {
        this.penalitiesOwnGoal = penalitiesOwnGoal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.attackGoal);
        dest.writeValue(this.attackAssist);
        dest.writeValue(this.attackFivePass);
        dest.writeValue(this.attackTwoShots);
        dest.writeValue(this.defenceTackleWon);
        dest.writeValue(this.defenceInterceptionWon);
        dest.writeValue(this.defenceSaveGoalkeeper);
        dest.writeValue(this.defencePenalitySaved);
        dest.writeValue(this.bonusPointSuperSixty);
        dest.writeValue(this.penalitiesYellowCard);
        dest.writeValue(this.penalitiesRedCard);
        dest.writeValue(this.penalitiesOwnGoal);
    }

    public void readFromParcel(Parcel source) {
        this.attackGoal = (Integer) source.readValue(Integer.class.getClassLoader());
        this.attackAssist = (Integer) source.readValue(Integer.class.getClassLoader());
        this.attackFivePass = (Integer) source.readValue(Integer.class.getClassLoader());
        this.attackTwoShots = (Integer) source.readValue(Integer.class.getClassLoader());
        this.defenceTackleWon = (Integer) source.readValue(Integer.class.getClassLoader());
        this.defenceInterceptionWon = (Integer) source.readValue(Integer.class.getClassLoader());
        this.defenceSaveGoalkeeper = (Integer) source.readValue(Integer.class.getClassLoader());
        this.defencePenalitySaved = (Integer) source.readValue(Integer.class.getClassLoader());
        this.bonusPointSuperSixty = (Integer) source.readValue(Integer.class.getClassLoader());
        this.penalitiesYellowCard = (Integer) source.readValue(Integer.class.getClassLoader());
        this.penalitiesRedCard = (Integer) source.readValue(Integer.class.getClassLoader());
        this.penalitiesOwnGoal = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public CxFbScore() {
    }

    protected CxFbScore(Parcel in) {
        this.attackGoal = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attackAssist = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attackFivePass = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attackTwoShots = (Integer) in.readValue(Integer.class.getClassLoader());
        this.defenceTackleWon = (Integer) in.readValue(Integer.class.getClassLoader());
        this.defenceInterceptionWon = (Integer) in.readValue(Integer.class.getClassLoader());
        this.defenceSaveGoalkeeper = (Integer) in.readValue(Integer.class.getClassLoader());
        this.defencePenalitySaved = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bonusPointSuperSixty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.penalitiesYellowCard = (Integer) in.readValue(Integer.class.getClassLoader());
        this.penalitiesRedCard = (Integer) in.readValue(Integer.class.getClassLoader());
        this.penalitiesOwnGoal = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<CxFbScore> CREATOR = new Creator<CxFbScore>() {
        @Override
        public CxFbScore createFromParcel(Parcel source) {
            return new CxFbScore(source);
        }

        @Override
        public CxFbScore[] newArray(int size) {
            return new CxFbScore[size];
        }
    };
}
