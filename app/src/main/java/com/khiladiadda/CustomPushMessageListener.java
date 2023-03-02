package com.khiladiadda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.khiladiadda.league.LeagueActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.moengage.pushbase.push.PushMessageListener;

public class CustomPushMessageListener extends PushMessageListener {

    @Override
    public void onNotificationClick(@NonNull Activity activity, @NonNull Bundle payload) {
        if (!payload.containsKey(AppConstant.TDMSQUAD) || !payload.containsKey(AppConstant.TDMDUO) || !payload.containsKey(AppConstant.TDMSOLO) || !payload.containsKey(AppConstant.FREEFIRESCSQUAD) || !payload.containsKey(AppConstant.FREEFIRECSSOLO) || !payload.containsKey(AppConstant.FREEFIRECSDUO) || !payload.containsKey(AppConstant.FREEFIRESQUAD) || !payload.containsKey(AppConstant.FREEFIRESOLO) || !payload.containsKey(AppConstant.FREEFIREDUO) || !payload.containsKey(AppConstant.BGMISQUAD) || !payload.containsKey(AppConstant.BGMISOLO) || !payload.containsKey(AppConstant.BGMIDUO) || !payload.containsKey(AppConstant.BGMIDUO) || !payload.containsKey(AppConstant.FFMAXSOLO) || !payload.containsKey(AppConstant.FFMAXDUO) || !payload.containsKey(AppConstant.FFMAXSQUAD) || !payload.containsKey(AppConstant.FFMAXSQUAD) || !payload.containsKey(AppConstant.PESOLO) || !payload.containsKey(AppConstant.PEDUO) || !payload.containsKey(AppConstant.PESQUAD) || !payload.containsKey(AppConstant.PGSOLO) || !payload.containsKey(AppConstant.PGDUO) || !payload.containsKey(AppConstant.PGSQUAD) || !payload.containsKey(AppConstant.PGNSSOLO) || !payload.containsKey(AppConstant.PGNSSQUAD)||!payload.containsKey(AppConstant.MYLEAGUE)||!payload.containsKey(AppConstant.MYLEAGUETDM)||!payload.containsKey(AppConstant.MYLEAGUEBGMI)||!payload.containsKey(AppConstant.MYLEAGUEFCS)||!payload.containsKey(AppConstant.MYLEAGUEFFMAX)||!payload.containsKey(AppConstant.MYLEAGUEPE)||!payload.containsKey(AppConstant.MYLEAGUEPG)||!payload.containsKey(AppConstant.MYLEAGUEPN))
            super.onNotificationClick(activity, payload);
        if (payload.containsKey(AppConstant.TDMSQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_TDM, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.TDMDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_TDM, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.TDMSOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_TDM, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.FREEFIRESCSQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.FREEFIRECSDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.FREEFIRECSSOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.FREEFIRESQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.FREEFIRESOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.FREEFIREDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.BGMISQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_BGMI, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.BGMISOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_BGMI, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.BGMIDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_BGMI, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.FFMAXSOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_MAX, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.FFMAXDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_MAX, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.FFMAXSQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_FF_MAX, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.PESOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.PEDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.PESQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.PGSOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.PGDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.DUO);
        } else if (payload.containsKey(AppConstant.PGSQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.PGNSSOLO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.SOLO);
        } else if (payload.containsKey(AppConstant.PGNSSQUAD)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.SQUAD);
        } else if (payload.containsKey(AppConstant.PGNSDUO)) {
            setRedirectionActivity(activity, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.DUO);
        }else if (payload.containsKey(AppConstant.MYLEAGUEPN)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUEPGNS);
        }else if (payload.containsKey(AppConstant.MYLEAGUEPG)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEAGUESPG);
        }else if (payload.containsKey(AppConstant.MYLEAGUEPE)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESPE);
        }else if (payload.containsKey(AppConstant.MYLEAGUEFFMAX)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESFFMAX);
        }else if (payload.containsKey(AppConstant.MYLEAGUEFCS)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESFCS);
        }else if (payload.containsKey(AppConstant.MYLEAGUEBGMI)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESBGMI);
        }else if (payload.containsKey(AppConstant.MYLEAGUETDM)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESTDM);
        }else if (payload.containsKey(AppConstant.MYLEAGUE)){
            setRedirectionActivity(activity, AppConstant.FROM_APPFLYER_FORMYLEGUESFF);
        } else {
            super.onNotificationClick(activity, payload);
        }
    }

    private void setRedirectionActivity(Activity activity, String mFrom, String mCategory) {
        AppSharedPreference.initialize(activity);
        AppSharedPreference mAppPreference = AppSharedPreference.getInstance();
        mAppPreference.setIsDeepLinking(true);
        Intent i;
        i = new Intent(activity, LeagueActivity.class);
        i.putExtra(AppConstant.FROM, mFrom);
        i.putExtra(AppConstant.CATEGORY, mCategory);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(i);
    }
    private void setRedirectionActivity(Activity activity, int mFrom) {
        AppSharedPreference.initialize(activity);
        AppSharedPreference mAppPreference = AppSharedPreference.getInstance();
        mAppPreference.setIsDeepLinking(true);
        Intent i;
        i = new Intent(activity, MyLeagueActivity.class);
        i.putExtra(AppConstant.FROM, mFrom);
        i.putExtra(AppConstant.CATEGORY, (String) null);
       activity.startActivity(i);
    }

}