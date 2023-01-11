package com.khiladiadda.dialogs.interfaces;

import com.khiladiadda.network.model.response.hth.KaPlayerHTH;

import java.util.List;

public interface IOnCreateBattleListener {

    void onAddBattleListener(double amount, List<KaPlayerHTH> kaplayerList);

    void onCancelBattleListener();
}