package com.khiladiadda.dialogs.interfaces;

public interface IOnCreateTeamPaymentListener {

    void onPayment(String userId, String characterId, String teamName, String gameLevel, boolean mapDownload);
}
