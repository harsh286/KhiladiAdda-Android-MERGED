package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class RemainingLimitResponse extends BaseResponse {

    @SerializedName("remaining_add_limit")
    @Expose
    private Integer remainingAddLimit;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banners;
    @SerializedName("bajajAccessTocken")
    @Expose
    private String bajajAccessTocken;
    @SerializedName("bajajUserTocken")
    @Expose
    private String bajajUserTocken;
    @SerializedName("bajajWallet")
    @Expose
    private BajajWallet bajajWallet;
    @SerializedName("bajaWalletHeader")
    @Expose
    private String bajaWalletHeader;
    @SerializedName("bajaWalletDetail")
    @Expose
    private String bajaWalletDetail;
    @SerializedName("bajaUPIHeader")
    @Expose
    private String bajaUPIHeader;
    @SerializedName("bajaUPIDetail")
    @Expose
    private String bajaUPIDetail;

    public Integer getRemainingAddLimit() {
        return remainingAddLimit;
    }

    public void setRemainingAddLimit(Integer remainingAddLimit) {
        this.remainingAddLimit = remainingAddLimit;
    }

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }

    public String getBajajAccessTocken() {
        return bajajAccessTocken;
    }

    public void setBajajAccessTocken(String bajajAccessTocken) {
        this.bajajAccessTocken = bajajAccessTocken;
    }

    public String getBajajUserTocken() {
        return bajajUserTocken;
    }

    public void setBajajUserTocken(String bajajUserTocken) {
        this.bajajUserTocken = bajajUserTocken;
    }

    public BajajWallet getBajajWallet() {
        return bajajWallet;
    }

    public void setBajajWallet(BajajWallet bajajWallet) {
        this.bajajWallet = bajajWallet;
    }

    public String getBajaWalletHeader() {
        return bajaWalletHeader;
    }

    public void setBajaWalletHeader(String bajaWalletHeader) {
        this.bajaWalletHeader = bajaWalletHeader;
    }

    public String getBajaWalletDetail() {
        return bajaWalletDetail;
    }

    public void setBajaWalletDetail(String bajaWalletDetail) {
        this.bajaWalletDetail = bajaWalletDetail;
    }

    public String getBajaUPIHeader() {
        return bajaUPIHeader;
    }

    public void setBajaUPIHeader(String bajaUPIHeader) {
        this.bajaUPIHeader = bajaUPIHeader;
    }

    public String getBajaUPIDetail() {
        return bajaUPIDetail;
    }

    public void setBajaUPIDetail(String bajaUPIDetail) {
        this.bajaUPIDetail = bajaUPIDetail;
    }
}