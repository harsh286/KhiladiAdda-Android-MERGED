package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class RemainingLimitResponse extends BaseResponse {

    @SerializedName("remaining_add_limit") @Expose private long remaining_add_limit;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;
    @SerializedName("bajaWalletHeader") @Expose private String bajaWalletHeader;
    @SerializedName("bajaUPIHeader") @Expose private String bajaUPIHeader;
    @SerializedName("bajaWalletDetail") @Expose private String bajaWalletDetail;
    @SerializedName("bajaUPIDetail") @Expose private String bajaUPIDetail;

    public long getRemaining_add_limit() {
        return remaining_add_limit;
    }

    public void setRemaining_add_limit(long remaining_add_limit) {
        this.remaining_add_limit = remaining_add_limit;
    }

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }

    public String getBajaWalletHeader() {
        return bajaWalletHeader;
    }

    public void setBajaWalletHeader(String bajaWalletHeader) {
        this.bajaWalletHeader = bajaWalletHeader;
    }

    public String getBajaUPIHeader() {
        return bajaUPIHeader;
    }

    public void setBajaUPIHeader(String bajaUPIHeader) {
        this.bajaUPIHeader = bajaUPIHeader;
    }

    public String getBajaWalletDetail() {
        return bajaWalletDetail;
    }

    public void setBajaWalletDetail(String bajaWalletDetail) {
        this.bajaWalletDetail = bajaWalletDetail;
    }

    public String getBajaUPIDetail() {
        return bajaUPIDetail;
    }

    public void setBajaUPIDetail(String bajaUPIDetail) {
        this.bajaUPIDetail = bajaUPIDetail;
    }
}