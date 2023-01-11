package com.khiladiadda.playerStats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T20Is {
    @SerializedName("10")
    @Expose
    private String _10;
    @SerializedName("5w")
    @Expose
    private String _5w;
    @SerializedName("4w")
    @Expose
    private String _4w;
    @SerializedName("SR")
    @Expose
    private String sR;
    @SerializedName("Econ")
    @Expose
    private String econ;
    @SerializedName("Ave")
    @Expose
    private String ave;
    @SerializedName("BBM")
    @Expose
    private String bBM;
    @SerializedName("BBI")
    @Expose
    private String bBI;
    @SerializedName("Wkts")
    @Expose
    private String wkts;
    @SerializedName("Runs")
    @Expose
    private String runs;
    @SerializedName("Balls")
    @Expose
    private String balls;
    @SerializedName("Inns")
    @Expose
    private String inns;
    @SerializedName("Mat")
    @Expose
    private String mat;

    public String get10() {
        return _10;
    }

    public void set10(String _10) {
        this._10 = _10;
    }

    public String get5w() {
        return _5w;
    }

    public void set5w(String _5w) {
        this._5w = _5w;
    }

    public String get4w() {
        return _4w;
    }

    public void set4w(String _4w) {
        this._4w = _4w;
    }

    public String getSR() {
        return sR;
    }

    public void setSR(String sR) {
        this.sR = sR;
    }

    public String getEcon() {
        return econ;
    }

    public void setEcon(String econ) {
        this.econ = econ;
    }

    public String getAve() {
        return ave;
    }

    public void setAve(String ave) {
        this.ave = ave;
    }

    public String getBBM() {
        return bBM;
    }

    public void setBBM(String bBM) {
        this.bBM = bBM;
    }

    public String getBBI() {
        return bBI;
    }

    public void setBBI(String bBI) {
        this.bBI = bBI;
    }

    public String getWkts() {
        return wkts;
    }

    public void setWkts(String wkts) {
        this.wkts = wkts;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getInns() {
        return inns;
    }

    public void setInns(String inns) {
        this.inns = inns;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

}
