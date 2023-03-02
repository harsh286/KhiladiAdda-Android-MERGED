package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhonePeObjResponse {
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("merchantTransactionId")
    @Expose
    private String merchantTransactionId;
    @SerializedName("merchantUserId")
    @Expose
    private String merchantUserId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("callbackUrl")
    @Expose
    private String callbackUrl;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("deviceContext")
    @Expose
    private DeviceContextResponse deviceContext;
    @SerializedName("paymentInstrument")
    @Expose
    private PaymentInstrumentResponse paymentInstrument;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public DeviceContextResponse getDeviceContext() {
        return deviceContext;
    }

    public void setDeviceContext(DeviceContextResponse deviceContext) {
        this.deviceContext = deviceContext;
    }

    public PaymentInstrumentResponse getPaymentInstrument() {
        return paymentInstrument;
    }

    public void setPaymentInstrument(PaymentInstrumentResponse paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }
}