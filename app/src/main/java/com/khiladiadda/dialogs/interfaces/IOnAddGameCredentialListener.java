package com.khiladiadda.dialogs.interfaces;

public interface IOnAddGameCredentialListener {

    void onCredentialAdd(String userId, String characterId, String teamCode, String gameLevel, boolean mapDownload);
}