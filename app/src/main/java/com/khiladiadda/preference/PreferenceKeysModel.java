package com.khiladiadda.preference;

public class PreferenceKeysModel {
    private String mIdKey;
    private String mSoloKey;
    private String mDuoKey;
    private String mSquadKey;

    public PreferenceKeysModel() {
    }

    public PreferenceKeysModel(String idKey) {
        this.mIdKey = idKey;
    }

    public PreferenceKeysModel(String idKey, String soloKey, String duoKey, String squadKey) {
        this.mIdKey = idKey;
        this.mSoloKey = soloKey;
        this.mDuoKey = duoKey;
        this.mSquadKey = squadKey;
    }

    public String getIdKey() {
        return mIdKey;
    }

    public void setIdKey(String idKey) {
        this.mIdKey = idKey;
    }

    public String getSoloKey() {
        return mSoloKey;
    }

    public void setSoloKey(String soloKey) {
        this.mSoloKey = soloKey;
    }

    public String getDuoKey() {
        return mDuoKey;
    }

    public void setDuoKey(String duoKey) {
        this.mDuoKey = duoKey;
    }

    public String getSquadKey() {
        return mSquadKey;
    }

    public void setSquadKey(String squadKey) {
        this.mSquadKey = squadKey;
    }
}