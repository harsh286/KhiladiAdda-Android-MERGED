package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class UpdateBalanceResponse extends BaseResponse {

    @SerializedName("is_coupon_use")
    @Expose
    String is_coupon_use;

    public String getIs_coupon_use() {
        return is_coupon_use;
    }

    public void setIs_coupon_use(String is_coupon_use) {
        this.is_coupon_use = is_coupon_use;
    }
}
