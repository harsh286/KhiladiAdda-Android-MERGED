package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VersionDetails {

    @SerializedName("app_version") @Expose private String appVersion;
    @SerializedName("apk_link") @Expose private String apkLink;
    @SerializedName("apk_size") @Expose private long apkSize;
    @SerializedName("update_description") @Expose private String updateDescription;
    @SerializedName("cashfree_enabled") @Expose private boolean cashfreeEnable;
    @SerializedName("paytm_enabled") @Expose private boolean paytmEnable;
    @SerializedName("payu_enabled") @Expose private boolean payuEnable;
    @SerializedName("razorpay_enabled") @Expose private boolean razorpayEnable;
    @SerializedName("paykun_enabled") @Expose private boolean paykunEnable;
    @SerializedName("is_facebook") @Expose private boolean isFB;
    @SerializedName("is_gmail") @Expose private boolean isGmail;
    @SerializedName("support_number")@Expose private String support_number;
    @SerializedName("withdraw_commission") @Expose private List<WithdrawComissionDetails> withdrawCommission = null;
    @SerializedName("easebuzz_enabled") @Expose private boolean easebuzzEnable;
    @SerializedName("neokred_enabled") @Expose private boolean neokredEnable;
    @SerializedName("upi_enable") @Expose private int upiEnable;
    @SerializedName("phonepe_enabled") @Expose private boolean phonepeEnabled;
    @SerializedName("phonepe_upi") @Expose private boolean phonepeUpi;
    @SerializedName("gamercash_enabled") @Expose private boolean gamerCashEnabled;
    @SerializedName("ludoadda_apk_version") @Expose private String ludoAddaVersion;
    @SerializedName("ludo_apk_link") @Expose private String ludoApkLink;
    @SerializedName("truecaller_enabled") @Expose private boolean truecallerEnabled;
    @SerializedName("location_enabled") @Expose private boolean locationEnabled;
    @SerializedName("rummy_link") @Expose private String rummyLink;
    @SerializedName("isBajajWallet") @Expose private boolean isBajajWallet;
    @SerializedName("isBajajUPI") @Expose private boolean isBajajUPI;
    @SerializedName("otherUpi") @Expose private int otherUpi;

    public String getRummyLink() {
        return rummyLink;
    }

    public void setRummyLink(String rummyLink) {
        this.rummyLink = rummyLink;
    }

    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(boolean locationEnabled) {
        this.locationEnabled = locationEnabled;
    }

    public boolean isTruecallerEnabled() {
        return truecallerEnabled;
    }

    public void setTruecallerEnabled(boolean truecallerEnabled) {
        this.truecallerEnabled = truecallerEnabled;
    }

    public String getLudoAddaVersion() {
        return ludoAddaVersion;
    }

    public void setLudoAddaVersion(String ludoAddaVersion) {
        this.ludoAddaVersion = ludoAddaVersion;
    }

    public String getLudoApkLink() {
        return ludoApkLink;
    }

    public void setLudoApkLink(String ludoApkLink) {
        this.ludoApkLink = ludoApkLink;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public long getApkSize() {
        return apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    public boolean isCashfreeEnable() {
        return cashfreeEnable;
    }

    public void setCashfreeEnable(boolean cashfreeEnable) {
        this.cashfreeEnable = cashfreeEnable;
    }

    public boolean isPaytmEnable() {
        return paytmEnable;
    }

    public void setPaytmEnable(boolean paytmEnable) {
        this.paytmEnable = paytmEnable;
    }

    public boolean isPayuEnable() {
        return payuEnable;
    }

    public void setPayuEnable(boolean payuEnable) {
        this.payuEnable = payuEnable;
    }

    public boolean isRazorpayEnable() {
        return razorpayEnable;
    }

    public void setRazorpayEnable(boolean razorpayEnable) {
        this.razorpayEnable = razorpayEnable;
    }

    public boolean isPaykunEnable() {
        return paykunEnable;
    }

    public void setPaykunEnable(boolean paykunEnable) {
        this.paykunEnable = paykunEnable;
    }

    public boolean isFB() {
        return isFB;
    }

    public void setFB(boolean FB) {
        isFB = FB;
    }

    public boolean isGmail() {
        return isGmail;
    }

    public void setGmail(boolean gmail) {
        isGmail = gmail;
    }
    public String getSupport_number() {
        return support_number;
    }

    public void setSupport_number(String support_number) {
        this.support_number = support_number;
    }

    public List<WithdrawComissionDetails> getWithdrawCommission() {
        return withdrawCommission;
    }

    public void setWithdrawCommission(List<WithdrawComissionDetails> withdrawCommission) {
        this.withdrawCommission = withdrawCommission;
    }

    public boolean isEasebuzzEnable() {
        return easebuzzEnable;
    }

    public void setEasebuzzEnable(boolean easebuzzEnable) {
        this.easebuzzEnable = easebuzzEnable;
    }

    public int getUpiEnable() {
        return upiEnable;
    }

    public void setUpiEnable(int upiEnable) {
        this.upiEnable = upiEnable;
    }

    public boolean isNeokredEnable() {
        return neokredEnable;
    }

    public void setNeokredEnable(boolean neokredEnable) {
        this.neokredEnable = neokredEnable;
    }

    public boolean isPhonepeEnabled() {
        return phonepeEnabled;
    }

    public void setPhonepeEnabled(boolean phonepeEnabled) {
        this.phonepeEnabled = phonepeEnabled;
    }

    public boolean isPhonepeUpi() {
        return phonepeUpi;
    }

    public void setPhonepeUpi(boolean phonepeUpi) {
        this.phonepeUpi = phonepeUpi;
    }

    public boolean isGamerCashEnabled() {
        return gamerCashEnabled;
    }

    public void setGamerCashEnabled(boolean gamerCashEnabled) {
        this.gamerCashEnabled = gamerCashEnabled;
    }

    public boolean isBajajWallet() {
        return isBajajWallet;
    }

    public void setBajajWallet(boolean bajajWallet) {
        isBajajWallet = bajajWallet;
    }

    public boolean isBajajUPI() {
        return isBajajUPI;
    }

    public void setBajajUPI(boolean bajajUPI) {
        isBajajUPI = bajajUPI;
    }

    public int getOtherUpi() {
        return otherUpi;
    }

    public void setOtherUpi(int otherUpi) {
        this.otherUpi = otherUpi;
    }
    
}