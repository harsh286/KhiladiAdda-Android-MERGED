package com.khiladiadda.battle.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.battle.model.JoinGroupRequest;
import com.khiladiadda.battle.model.JoinGroupSubstituteRequest;

public interface IBattlePresenter extends IBasePresenter {

    void getBattleList(String id, boolean isPlayed, boolean live, boolean past);

    void getGroupList(String id, boolean is_reverse,boolean profile);

    void joinBattleGroup(JoinGroupRequest request, String id);

    void getCalculationBanner(int bannerType);

    void cancelGroup(String groupId);

    void joinGroupForSubstitute(JoinGroupSubstituteRequest request);
}