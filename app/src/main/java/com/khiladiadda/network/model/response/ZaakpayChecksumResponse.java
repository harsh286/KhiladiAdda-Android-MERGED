package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class ZaakpayChecksumResponse extends BaseResponse{

    @SerializedName("checksum")
    @Expose
    private String checksum;
    @SerializedName("checksumPo")
    @Expose
    private String checksumPo;
    @SerializedName("order")
    @Expose
    private String orderId;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getChecksumPo() {
        return checksumPo;
    }

    public void setChecksumPo(String checksumPo) {
        this.checksumPo = checksumPo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}