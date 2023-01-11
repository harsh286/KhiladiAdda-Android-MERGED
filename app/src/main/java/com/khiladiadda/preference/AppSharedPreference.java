package com.khiladiadda.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.DashboardDetailResponse;
import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.TopUsersDetails;
import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.network.model.response.VersionDetails;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.WithdrawComissionDetails;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

public class AppSharedPreference {

    private SharedPreferences sharedPreferences;
    private Editor editor;
    private static AppSharedPreference appSharedPrefrence;

    public interface PreferenceKeys {
        String KEY_RECEIPT_TYPE = "key_receipt_type";
    }

    public static AppSharedPreference initialize(Context context) {
        if (appSharedPrefrence == null) {
            synchronized (AppSharedPreference.class) {
                appSharedPrefrence = new AppSharedPreference(context);
            }
        }
        return appSharedPrefrence;
    }

    public static AppSharedPreference getInstance() {
        if (appSharedPrefrence == null) {
            throw new IllegalStateException("You must call initialize() before calling getInstance()");
        }
        return appSharedPrefrence;
    }

    @SuppressLint("CommitPrefEdits")
    private AppSharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences("khiladi_adda_prefs", Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public Editor getEditor() {
        return editor;
    }

    public void commitEditor() {
        editor.commit();
    }

    public void clearEditor() {
        editor.clear();
        editor.commit();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean("isLogin", false);
    }

    public void setIsLogin(boolean isLogin) {
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    public boolean getIsProfile() {
        return sharedPreferences.getBoolean("isProfile", false);
    }

    public void setIsProfile(boolean isProfile) {
        editor.putBoolean("isProfile", isProfile);
        editor.commit();
    }


    public boolean getIsCategory() {
        return sharedPreferences.getBoolean("isCategory", false);
    }

    public void setIsCategory(boolean isCategory) {
        editor.putBoolean("isCategory", isCategory);
        editor.commit();
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void setString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setDeviceToken(String deviceToken) {
        editor.putString("deviceToken", deviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString("deviceToken", null);
    }

    public void setSessionToken(String sessionToken) {
        editor.putString("sessionToken", sessionToken);
        editor.commit();
    }

    public String getSessionToken() {
        return sharedPreferences.getString("sessionToken", "");
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString("name", null);
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }

    public void setMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public String getMobile() {
        return sharedPreferences.getString("mobile", null);
    }

    public void setUrl(String url) {
        editor.putString("url", url);
        editor.commit();
    }

    public String getUrl() {
        return sharedPreferences.getString("url", null);
    }

    public void setInviteCode(String inviteCode) {
        editor.putString("inviteCode", inviteCode);
        editor.commit();
    }

    public String getInviteCode() {
        return sharedPreferences.getString("inviteCode", null);
    }

    public String getPicture() {
        return sharedPreferences.getString("picture", null);
    }

    public void setPicture(String picture) {
        editor.putString("picture", picture);
        editor.commit();
    }

    public String getGaming() {
        return sharedPreferences.getString("gaming", null);
    }

    public void setGaming(String gaming) {
        editor.putString("gaming", gaming);
        editor.commit();
    }

    public String getWebSeries() {
        return sharedPreferences.getString("webSeries", null);
    }

    public void setWebSeries(String webSeries) {
        editor.putString("webSeries", webSeries);
        editor.commit();
    }

    public String getLogo() {
        return sharedPreferences.getString("logo", null);
    }

    public void setLogo(String logo) {
        editor.putString("logo", logo);
        editor.commit();
    }

    public String getAudio() {
        return sharedPreferences.getString("audio", null);
    }

    public void setAudio(String audio) {
        editor.putString("audio", audio);
        editor.commit();
    }

    public String getVideo() {
        return sharedPreferences.getString("video", null);
    }

    public void setVideo(String video) {
        editor.putString("video", video);
        editor.commit();
    }

    public String getSports() {
        return sharedPreferences.getString("sports", null);
    }

    public void setSports(String sports) {
        editor.putString("sports", sports);
        editor.commit();
    }

    public String getTechnology() {
        return sharedPreferences.getString("technology", null);
    }

    public void setTechnology(String technology) {
        editor.putString("technology", technology);
        editor.commit();
    }

    public String getScience() {
        return sharedPreferences.getString("science", null);
    }

    public void setScience(String science) {
        editor.putString("science", science);
        editor.commit();
    }

    public String getPrediction() {
        return sharedPreferences.getString("prediction", null);
    }

    public void setPrediction(String prediction) {
        editor.putString("prediction", prediction);
        editor.commit();
    }

    public String getMath() {
        return sharedPreferences.getString("math", null);
    }

    public void setMath(String math) {
        editor.putString("math", math);
        editor.commit();
    }

    public String getGK() {
        return sharedPreferences.getString("gk", null);
    }

    public void setGK(String gk) {
        editor.putString("gk", gk);
        editor.commit();
    }

    public String getMovie() {
        return sharedPreferences.getString("movie", null);
    }

    public void setMovie(String movie) {
        editor.putString("movie", movie);
        editor.commit();
    }

    public void setCategoryId(String categoryId) {
        editor.putString("categoryId", categoryId);
        editor.commit();
    }

    public String getCategoryId() {
        return sharedPreferences.getString("categoryId", null);
    }

    public void setProfileData(ProfileDetails profile) {
        String profileJson = new Gson().toJson(profile);
        editor.putString("profileJson", profileJson);
        editor.commit();
    }

    public ProfileDetails getProfileData() {
        String profileJson = sharedPreferences.getString("profileJson", null);
        if (profileJson == null) {
            return new ProfileDetails();
        }
        return new Gson().fromJson(profileJson, ProfileDetails.class);
    }

    public void setIsVersionUpdated(boolean isVersion) {
        editor.putBoolean("isVersion", isVersion);
        editor.commit();
    }

    public boolean getIsVersionUpdated() {
        return sharedPreferences.getBoolean("isVersion", false);
    }

    public void setIsUUID(boolean isUUID) {
        editor.putBoolean("isUUID", isUUID);
        editor.commit();
    }

    public boolean getIsUUID() {
        return sharedPreferences.getBoolean("isUUID", false);
    }


    public void setNotificationPending(boolean notificationPending) {
        editor.putBoolean("notificationPending", notificationPending);
        editor.commit();
    }

    public boolean getNotificationPending() {
        return sharedPreferences.getBoolean("notificationPending", false);
    }

    public void setNotificationId(int notificationId) {
        editor.putInt("notificationId", notificationId);
        editor.commit();
    }

    public int getNotificationId() {
        return sharedPreferences.getInt("notificationId", 0);
    }

    public void setNotificationCount(int notificationCount) {
        editor.putInt("notificationCount", notificationCount);
        editor.commit();
    }

    public int getNotificationCount() {
        return sharedPreferences.getInt("notificationCount", 0);
    }

    public void setIsVideoSeen(boolean isVideoSeen) {
        editor.putBoolean("isVideoSeen", isVideoSeen);
        editor.commit();
    }

    public boolean getIsVideoSeen() {
        return sharedPreferences.getBoolean("isVideoSeen", false);
    }


    public void setIsCODVideoSeen(boolean isCODVideoSeen) {
        editor.putBoolean("isCODVideoSeen", isCODVideoSeen);
        editor.commit();
    }

    public boolean getIsCODVideoSeen() {
        return sharedPreferences.getBoolean("isCODVideoSeen", false);
    }

    public void setIsTutorialSeen(boolean isTutorialSeen) {
        editor.putBoolean("isTutorialSeen", isTutorialSeen);
        editor.commit();
    }

    public boolean getIsTutorialSeen() {
        return sharedPreferences.getBoolean("isTutorialSeen", false);
    }

    public void setMasterData(MasterResponse masterData) {
        String masterJson = new Gson().toJson(masterData);
        editor.putString("masterJson", masterJson);
        editor.commit();
    }

    public MasterResponse getMasterData() {
        String masterJson = sharedPreferences.getString("masterJson", null);
        if (masterJson == null) {
            return new MasterResponse();
        }
        return new Gson().fromJson(masterJson, MasterResponse.class);
    }

    public void setDateSaveMaster(long masterDate) {
        editor.putLong("masterDate", masterDate);
        editor.commit();
    }

    public long getDateSaveMaster() {
        return sharedPreferences.getLong("masterDate", 0);
    }

    public void setFAQCategoryData(FaqCategoryResponse categoryData) {
        String categoryJson = new Gson().toJson(categoryData);
        editor.putString("faqCategoryJson", categoryJson);
        editor.commit();
    }

    public FaqCategoryResponse getFaqCategoryData() {
        String categoryJson = sharedPreferences.getString("faqCategoryJson", null);
        if (categoryJson == null) {
            return new FaqCategoryResponse();
        }
        return new Gson().fromJson(categoryJson, FaqCategoryResponse.class);
    }

    public void setIsDeepLinking(boolean isDeepLinking) {
        editor.putBoolean("isDeepLinking", isDeepLinking);
        editor.commit();
    }

    public boolean getIsDeepLinking() {
        return sharedPreferences.getBoolean("isDeepLinking", false);
    }

    public void setSupportNumber(String supportNumber) {
        editor.putString("supportNumber", supportNumber);
        editor.commit();
    }

    public String getSupportNumber() {
        return sharedPreferences.getString("supportNumber", null);
    }

    public void setIsPaytmUpi(boolean ispaytmupi) {
        editor.putBoolean("ispaytmupi", ispaytmupi);
        editor.commit();
    }

    public boolean getIsPaytmUpi() {
        return sharedPreferences.getBoolean("ispaytmupi", false);
    }

    public void setWithdrawComission(VersionDetails comissionData) {
        String masterJson = new Gson().toJson(comissionData);
        editor.putString("comissionData", masterJson);
        editor.commit();
    }

    public VersionDetails getWithdrawComission() {
        String masterJson = sharedPreferences.getString("comissionData", null);
        if (masterJson == null) {
            return new VersionDetails();
        }
        return new Gson().fromJson(masterJson, VersionDetails.class);
    }


    public void setVersion(VersionResponse version) {
        String versionJson = new Gson().toJson(version);
        editor.putString("versionJson", versionJson);
        editor.commit();
    }

    public VersionResponse getVersion() {
        String versionJson = sharedPreferences.getString("versionJson", null);
        if (versionJson == null) {
            return new VersionResponse();
        }
        return new Gson().fromJson(versionJson, VersionResponse.class);
    }

    public void setDashboardData(DashboardDetailResponse categoryData) {
        String categoryJson = new Gson().toJson(categoryData);
        editor.putString("dashboardJson", categoryJson);
        editor.commit();
    }

    public DashboardDetailResponse getDashboardData() {
        String categoryJson = sharedPreferences.getString("dashboardJson", null);
        if (categoryJson == null) {
            return new DashboardDetailResponse();
        }
        return new Gson().fromJson(categoryJson, DashboardDetailResponse.class);
    }


    public boolean getIsCoinSync() {
        return sharedPreferences.getBoolean("isCoinsSync", false);
    }

    public void setIsCoinSync(boolean isCoinSync) {
        editor.putBoolean("isCoinSync", isCoinSync);
        editor.commit();
    }

    public void setIsMoEngage(boolean isMoEngage) {
        editor.putBoolean("ismoengage", isMoEngage);
        editor.commit();
    }

    public boolean getIsMoEngage() {
        return sharedPreferences.getBoolean("ismoengage", false);
    }

    public boolean getIsGamerCashLinked() {
        return sharedPreferences.getBoolean("isLinked", false);
    }

    public void setIsGamerCashLinked(boolean isGamerCashLinked) {
        editor.putBoolean("isLinked", isGamerCashLinked);
        editor.commit();
    }
}