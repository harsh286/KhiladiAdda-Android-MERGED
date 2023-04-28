package com.khiladiadda;

import android.app.Application;
import android.content.Intent;
import android.util.Log;


import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.deeplink.DeepLinkResult;
import com.khiladiadda.battle.BattleGroupActivity;
import com.khiladiadda.fanbattle.FanBattleActivity;
import com.khiladiadda.fcm.PushMessageListener;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.leaderboard.NewLeaderboardActivity;
import com.khiladiadda.league.LeagueActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.ludo.LudoChallengeActivity;
import com.khiladiadda.ludo.luodhelp.LudoHelpActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.quiz.QuizDetailsActivity;
import com.khiladiadda.referhistory.ReferHelpActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.wallet.WalletActivity;
import com.khiladiadda.wallet.WalletCashbackActivity;
import com.moengage.core.DataCenter;
import com.moengage.core.LogLevel;
import com.moengage.core.MoEngage;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.core.config.LogConfig;
import com.moengage.core.config.NotificationConfig;
import com.moengage.core.model.AppStatus;
import com.moengage.firebase.MoEFireBaseHelper;
import com.moengage.pushbase.MoEPushHelper;

import java.util.Map;
import java.util.Objects;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class KhiladiAddaApp extends Application {

    private static KhiladiAddaApp sInstance;
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    private AppSharedPreference mAppPreference;

    public KhiladiAddaApp() {
        sInstance = this;
    }

    public static KhiladiAddaApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/ProductSans_Regular.ttf").setFontAttrId(R.attr.fontPath).build())).build());
        AppSharedPreference.initialize(this);
        mAppPreference = AppSharedPreference.getInstance();
        mAppPreference.setIsDeepLinking(false);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME).schemaVersion(0).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm.getInstance(realmConfiguration).close();
        /*Mo Engage */
        MoEngage moEngage = new MoEngage.Builder(this, AppConstant.MOENGAGEAPPSKEY)
                .setDataCenter(DataCenter.DATA_CENTER_3)
                .configureNotificationMetaData(new NotificationConfig(R.mipmap.ic_launcher, R.mipmap.ic_launcher))
                .configureLogs(new LogConfig(LogLevel.VERBOSE, false))
                .build();
        MoEngage.initialiseDefaultInstance(moEngage);
        MoEPushHelper.getInstance().registerMessageListener(new CustomPushMessageListener());
        MoEFireBaseHelper.getInstance().addNonMoEngagePushListener(new PushMessageListener(this));
        MoEAnalyticsHelper.INSTANCE.trackDeviceLocale(this);
        if (mAppPreference.getProfileData() != null && mAppPreference.getProfileData().getId() != null) {
            MoEAnalyticsHelper.INSTANCE.setUniqueId(this, mAppPreference.getProfileData().getId());
        }
        trackInstall();
        /*Apps Flyer * */
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                for (String attrName : conversionDataMap.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
                String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
            }
        };
        AppsFlyerLib.getInstance().start(getApplicationContext(), AppConstant.APPSKEY, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, @NonNull String s) {
            }
        });
        AppsFlyerLib.getInstance().setCustomerIdAndLogSession(mAppPreference.getProfileData().getId(), this);
        AppsFlyerLib.getInstance().init(AppConstant.APPSKEY, conversionListener, this);
        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().start(this);
        AppsFlyerLib.getInstance().subscribeForDeepLink(deepLinkResult -> {
            DeepLinkResult.Status dlStatus = deepLinkResult.getStatus();
            if (dlStatus == DeepLinkResult.Status.FOUND) {
            } else if (dlStatus == DeepLinkResult.Status.NOT_FOUND) {
                mAppPreference.setIsDeepLinking(false);
                return;
            } else {
                DeepLinkResult.Error dlError = deepLinkResult.getError();
                return;
            }
            DeepLink deepLinkObj = deepLinkResult.getDeepLink();
            String fruitName = "";
            try {
                fruitName = deepLinkObj.getDeepLinkValue();
                if (fruitName == null) {
                    // Log.d(LOG_TAG, "Deeplink value returned null");
                    mAppPreference.setIsDeepLinking(false);
                    return;
                }  //  Log.d(LOG_TAG, "The DeepLink will route to: " + fruitName);
            } catch (Exception e) {
                mAppPreference.setIsDeepLinking(false);
                return;
            }
            if (mAppPreference.getIsLogin()) {
                goToFruit(fruitName, deepLinkObj);
            }
        });
    }

    private void goToFruit(String fruitName, DeepLink dlData) {
        String id = null;
        if (dlData != null) {
            id = dlData.getStringValue("id");
        }
        switch (fruitName) {
            case AppConstant.LEADERBOARD:
                setBoolString(true, NewLeaderboardActivity.class, AppConstant.FROM_MAIN, null);
                break;
            case AppConstant.TDMSQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_TDM, AppConstant.SQUAD);
                break;
            case AppConstant.TDMDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_TDM, AppConstant.DUO);
                break;
            case AppConstant.TDMSOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_TDM, AppConstant.SOLO);
                break;
            case AppConstant.FREEFIRESCSQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.SQUAD);
                break;
            case AppConstant.FREEFIRECSDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.DUO);
                break;
            case AppConstant.FREEFIRECSSOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_CLASH, AppConstant.SOLO);
                break;
            case AppConstant.FREEFIRESQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.SQUAD);
                break;
            case AppConstant.FREEFIREDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.DUO);
                break;
            case AppConstant.FREEFIRESOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FREEFIRE, AppConstant.SOLO);
                break;
            case AppConstant.BGMISQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_BGMI, AppConstant.SQUAD);
                break;
            case AppConstant.BGMIDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_BGMI, AppConstant.DUO);
                break;
            case AppConstant.BGMISOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_BGMI, AppConstant.SOLO);
                break;
            case AppConstant.FFMAXSOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_MAX, AppConstant.SOLO);
                break;
            case AppConstant.FFMAXDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_MAX, AppConstant.DUO);
                break;
            case AppConstant.FFMAXSQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_FF_MAX, AppConstant.SQUAD);
                break;
            case AppConstant.PESOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.SOLO);
                break;
            case AppConstant.PEDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.DUO);
                break;
            case AppConstant.PESQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PREMIUM_ESPORTS, AppConstant.SQUAD);
                break;
            case AppConstant.PGSOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.SOLO);
                break;
            case AppConstant.PGDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.DUO);
                break;
            case AppConstant.PGSQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_GLOBAL, AppConstant.SQUAD);
                break;
            case AppConstant.PGNSSOLO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.SOLO);
                break;
            case AppConstant.PGNSDUO:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.DUO);
                break;
            case AppConstant.PGNSSQUAD:
                setBoolString(true, LeagueActivity.class, AppConstant.FROM_VIEW_PUBG_NEWSTATE, AppConstant.SQUAD);
                break;
            case AppConstant.REFERANDEARN:
                setBool(true, ReferHelpActivity.class, 0, null, null);
                break;
            case AppConstant.HELPSECTION:
                setBool(true, HelpActivity.class, 0, null, null);
                break;
            case AppConstant.ADDCOINS:
                setBool(true, WalletCashbackActivity.class, 0, null, null);
                break;
            case AppConstant.WALLET:
                setBool(true, WalletActivity.class, 0, null, null);
                break;
            case AppConstant.PROFILE:
                setBool(true, ProfileActivity.class, 0, null, null);
                break;
            case AppConstant.LUDODIALOGSCREEN:
                if (!mAppPreference.getBoolean(AppConstant.LUDO_VIDEO_SEEN, false)) {
                    setBool(true, LudoHelpActivity.class, 0, null, null);
                }
                setBool(true, LudoChallengeActivity.class, 0, null, "Dialog");
                break;
            case AppConstant.FANBATTLECOMBO:
                setBool(true, BattleGroupActivity.class, 0, null, id);
                break;
            case AppConstant.LUDOHOME:
                if (!mAppPreference.getBoolean(AppConstant.LUDO_VIDEO_SEEN, false)) {
                    setBool(true, LudoHelpActivity.class, 0, null, null);
                } else {
                    setBool(true, LudoChallengeActivity.class, 0, null, null);
                }
                break;
            case AppConstant.FANMATCHCREEN:
                setBool(true, FanBattleActivity.class, 0, null, null);
                break;
            case AppConstant.MYLEAGUE:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESFF, null, null);
                break;
            case AppConstant.MYLEAGUETDM:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESTDM, null, null);
                break;
            case AppConstant.MYLEAGUEBGMI:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESBGMI, null, null);
                break;
            case AppConstant.MYLEAGUEFCS:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESFCS, null, null);
                break;
            case AppConstant.MYLEAGUEFFMAX:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESFFMAX, null, null);
                break;
            case AppConstant.MYLEAGUEPE:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUESPE, null, null);
                break;
            case AppConstant.MYLEAGUEPG:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEAGUESPG, null, null);
                break;
            case AppConstant.MYLEAGUEPN:
                setBool(true, MyLeagueActivity.class, AppConstant.FROM_APPFLYER_FORMYLEGUEPGNS, null, null);
                break;
            case AppConstant.QUIZONLY:
                setBool(true, QuizDetailsActivity.class, 0, null, null);
                break;
            default:
                setBool(false, null, 0, null, null);
                setBoolString(false, null, null, null);
                break;
        }
    }

    private void setBool(Boolean mBool, Class mActivity, int mFrom, String mCategory, String mGameid) {
        Intent i;
        mAppPreference.setIsDeepLinking(mBool);
        i = new Intent(getApplicationContext(), mActivity);
        i.putExtra(AppConstant.FROM, mFrom);
        i.putExtra(AppConstant.CATEGORY, mCategory);
        i.putExtra(AppConstant.GAMEID, mGameid);
        i.putExtra(AppConstant.CONTEST_TYPE, AppConstant.TYPE_LUDO);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void setBoolString(Boolean mBool, Class mActivity, String mFrom, String mCategory) {
        Intent i;
        mAppPreference.setIsDeepLinking(mBool);
        i = new Intent(getApplicationContext(), mActivity);
        i.putExtra(AppConstant.FROM, mFrom);
        i.putExtra(AppConstant.CATEGORY, mCategory);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void trackInstall() {
        if (!mAppPreference.getIsMoEngage()) {
            MoEAnalyticsHelper.INSTANCE.setAppStatus(this, AppStatus.INSTALL);
            mAppPreference.setIsMoEngage(true);
        }
    }

}