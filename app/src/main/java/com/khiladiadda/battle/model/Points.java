package com.khiladiadda.battle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Points implements Parcelable {
    @SerializedName("one_run") @Expose private long oneRun;
    @SerializedName("six_run") @Expose private long sixRun;
    @SerializedName("four_run") @Expose private long fourRun;
    @SerializedName("fifty") @Expose private long fifty;
    @SerializedName("hundred") @Expose private long hundred;
    @SerializedName("catch") @Expose private long catches;
    @SerializedName("wicket") @Expose private long wicket;
    @SerializedName("three_wickets") @Expose private long threeWickets;
    @SerializedName("five_wickets") @Expose private long fiveWickets;
    @SerializedName("direct_hit") @Expose private long directhit;
    @SerializedName("run_out") @Expose private long runOut;
    @SerializedName("lbw_out") @Expose private long lbwOut;
    @SerializedName("bowled_out") @Expose private long bowledOut;
    @SerializedName("stump_out") @Expose private long stumpOut;
    @SerializedName("field_catch") @Expose private long fieldCatch;


    protected Points(Parcel in) {
        oneRun = in.readLong();
        sixRun = in.readLong();
        fourRun = in.readLong();
        fifty = in.readLong();
        hundred = in.readLong();
        catches = in.readLong();
        wicket = in.readLong();
        threeWickets = in.readLong();
        fiveWickets = in.readLong();
        directhit = in.readLong();
        runOut = in.readLong();
        lbwOut = in.readLong();
        stumpOut = in.readLong();
        bowledOut = in.readLong();
        fieldCatch = in.readLong();

    }

    public static final Creator<Points> CREATOR = new Creator<Points>() {
        @Override public Points createFromParcel(Parcel in) {
            return new Points(in);
        }

        @Override public Points[] newArray(int size) {
            return new Points[size];
        }
    };

    public long getOneRun() {
        return oneRun;
    }

    public void setOneRun(long oneRun) {
        this.oneRun = oneRun;
    }

    public long getSixRun() {
        return sixRun;
    }

    public void setSixRun(long sixRun) {
        this.sixRun = sixRun;
    }

    public long getFourRun() {
        return fourRun;
    }

    public void setFourRun(long fourRun) {
        this.fourRun = fourRun;
    }

    public long getFifty() {
        return fifty;
    }

    public void setFifty(long fifty) {
        this.fifty = fifty;
    }

    public long getHundred() {
        return hundred;
    }

    public void setHundred(long hundred) {
        this.hundred = hundred;
    }

    public long getCatch() {
        return catches;
    }

    public void setCatch(long catches) {
        this.catches = catches;
    }

    public long getWicket() {
        return wicket;
    }

    public void setWicket(long wicket) {
        this.wicket = wicket;
    }

    public long getThreeWickets() {
        return threeWickets;
    }

    public void setThreeWickets(long threeWickets) {
        this.threeWickets = threeWickets;
    }

    public long getFiveWickets() {
        return fiveWickets;
    }

    public void setFiveWickets(long fiveWickets) {
        this.fiveWickets = fiveWickets;
    }

    public long getCatches() {
        return catches;
    }

    public void setCatches(long catches) {
        this.catches = catches;
    }

    public long getDirecthit() {
        return directhit;
    }

    public void setDirecthit(long directhit) {
        this.directhit = directhit;
    }

    public long getRunOut() {
        return runOut;
    }

    public void setRunOut(long runOut) {
        this.runOut = runOut;
    }

    public long getLbwOut() {
        return lbwOut;
    }

    public void setLbwOut(long lbwOut) {
        this.lbwOut = lbwOut;
    }

    public long getBowledOut() {
        return bowledOut;
    }

    public void setBowledOut(long bowledOut) {
        this.bowledOut = bowledOut;
    }

    public long getStumpOut() {
        return stumpOut;
    }

    public void setStumpOut(long stumpOut) {
        this.stumpOut = stumpOut;
    }

    public long getFieldCatch() {
        return fieldCatch;
    }

    public void setFieldCatch(long fieldCatch) {
        this.fieldCatch = fieldCatch;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(oneRun);
        dest.writeLong(sixRun);
        dest.writeLong(fourRun);
        dest.writeLong(fifty);
        dest.writeLong(hundred);
        dest.writeLong(catches);
        dest.writeLong(wicket);
        dest.writeLong(threeWickets);
        dest.writeLong(fiveWickets);
        dest.writeLong(directhit);
        dest.writeLong(runOut);
        dest.writeLong(lbwOut);
        dest.writeLong(stumpOut);
        dest.writeLong(bowledOut);
        dest.writeLong(fieldCatch);
    }
}
