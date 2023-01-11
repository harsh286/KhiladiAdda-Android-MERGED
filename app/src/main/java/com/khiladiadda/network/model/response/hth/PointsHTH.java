package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsHTH implements Parcelable {
    @SerializedName("one_run")
    @Expose
    private Integer oneRun;
    @SerializedName("six_run")
    @Expose
    private Integer sixRun;
    @SerializedName("four_run")
    @Expose
    private Integer fourRun;
    @SerializedName("fifty")
    @Expose
    private Integer fifty;
    @SerializedName("hundred")
    @Expose
    private Integer hundred;
    @SerializedName("direct_hit")
    @Expose
    private Integer directHit;
    @SerializedName("run_out")
    @Expose
    private Integer runOut;
    @SerializedName("lbw_out")
    @Expose
    private Integer lbwOut;
    @SerializedName("bowled_out")
    @Expose
    private Integer bowledOut;
    @SerializedName("catch")
    @Expose
    private Integer _catch;
    @SerializedName("field_catch")
    @Expose
    private Integer fieldCatch;
    @SerializedName("stump_out")
    @Expose
    private Integer stumpOut;
    @SerializedName("wicket")
    @Expose
    private Integer wicket;
    @SerializedName("three_wickets")
    @Expose
    private Integer threeWickets;
    @SerializedName("five_wickets")
    @Expose
    private Integer fiveWickets;

    public Integer getOneRun() {
        return oneRun;
    }

    public void setOneRun(Integer oneRun) {
        this.oneRun = oneRun;
    }

    public Integer getSixRun() {
        return sixRun;
    }

    public void setSixRun(Integer sixRun) {
        this.sixRun = sixRun;
    }

    public Integer getFourRun() {
        return fourRun;
    }

    public void setFourRun(Integer fourRun) {
        this.fourRun = fourRun;
    }

    public Integer getFifty() {
        return fifty;
    }

    public void setFifty(Integer fifty) {
        this.fifty = fifty;
    }

    public Integer getHundred() {
        return hundred;
    }

    public void setHundred(Integer hundred) {
        this.hundred = hundred;
    }

    public Integer getDirectHit() {
        return directHit;
    }

    public void setDirectHit(Integer directHit) {
        this.directHit = directHit;
    }

    public Integer getRunOut() {
        return runOut;
    }

    public void setRunOut(Integer runOut) {
        this.runOut = runOut;
    }

    public Integer getLbwOut() {
        return lbwOut;
    }

    public void setLbwOut(Integer lbwOut) {
        this.lbwOut = lbwOut;
    }

    public Integer getBowledOut() {
        return bowledOut;
    }

    public void setBowledOut(Integer bowledOut) {
        this.bowledOut = bowledOut;
    }

    public Integer getCatch() {
        return _catch;
    }

    public void setCatch(Integer _catch) {
        this._catch = _catch;
    }

    public Integer getFieldCatch() {
        return fieldCatch;
    }

    public void setFieldCatch(Integer fieldCatch) {
        this.fieldCatch = fieldCatch;
    }

    public Integer getStumpOut() {
        return stumpOut;
    }

    public void setStumpOut(Integer stumpOut) {
        this.stumpOut = stumpOut;
    }

    public Integer getWicket() {
        return wicket;
    }

    public void setWicket(Integer wicket) {
        this.wicket = wicket;
    }

    public Integer getThreeWickets() {
        return threeWickets;
    }

    public void setThreeWickets(Integer threeWickets) {
        this.threeWickets = threeWickets;
    }

    public Integer getFiveWickets() {
        return fiveWickets;
    }

    public void setFiveWickets(Integer fiveWickets) {
        this.fiveWickets = fiveWickets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.oneRun);
        dest.writeValue(this.sixRun);
        dest.writeValue(this.fourRun);
        dest.writeValue(this.fifty);
        dest.writeValue(this.hundred);
        dest.writeValue(this.directHit);
        dest.writeValue(this.runOut);
        dest.writeValue(this.lbwOut);
        dest.writeValue(this.bowledOut);
        dest.writeValue(this._catch);
        dest.writeValue(this.fieldCatch);
        dest.writeValue(this.stumpOut);
        dest.writeValue(this.wicket);
        dest.writeValue(this.threeWickets);
        dest.writeValue(this.fiveWickets);
    }

    public void readFromParcel(Parcel source) {
        this.oneRun = (Integer) source.readValue(Integer.class.getClassLoader());
        this.sixRun = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fourRun = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fifty = (Integer) source.readValue(Integer.class.getClassLoader());
        this.hundred = (Integer) source.readValue(Integer.class.getClassLoader());
        this.directHit = (Integer) source.readValue(Integer.class.getClassLoader());
        this.runOut = (Integer) source.readValue(Integer.class.getClassLoader());
        this.lbwOut = (Integer) source.readValue(Integer.class.getClassLoader());
        this.bowledOut = (Integer) source.readValue(Integer.class.getClassLoader());
        this._catch = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fieldCatch = (Integer) source.readValue(Integer.class.getClassLoader());
        this.stumpOut = (Integer) source.readValue(Integer.class.getClassLoader());
        this.wicket = (Integer) source.readValue(Integer.class.getClassLoader());
        this.threeWickets = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fiveWickets = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public PointsHTH() {
    }

    protected PointsHTH(Parcel in) {
        this.oneRun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sixRun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fourRun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fifty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hundred = (Integer) in.readValue(Integer.class.getClassLoader());
        this.directHit = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runOut = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lbwOut = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bowledOut = (Integer) in.readValue(Integer.class.getClassLoader());
        this._catch = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fieldCatch = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stumpOut = (Integer) in.readValue(Integer.class.getClassLoader());
        this.wicket = (Integer) in.readValue(Integer.class.getClassLoader());
        this.threeWickets = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fiveWickets = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<PointsHTH> CREATOR = new Parcelable.Creator<PointsHTH>() {
        @Override
        public PointsHTH createFromParcel(Parcel source) {
            return new PointsHTH(source);
        }

        @Override
        public PointsHTH[] newArray(int size) {
            return new PointsHTH[size];
        }
    };
}
