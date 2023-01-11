package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanResonse {
    @SerializedName("verified_by")
    @Expose
    private String verifiedBy;
    @SerializedName("response_status")
    @Expose
    private PanResponseDetails responseStatus;
    @SerializedName("verified_data")
    @Expose
    private String verifiedData;
    @SerializedName("verification_method")
    @Expose
    private String verificationMethod;
    @SerializedName("stan")
    @Expose
    private String stan;
    @SerializedName("verification_status")
    @Expose
    private String verificationStatus;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("verified_using")
    @Expose
    private String verifiedUsing;

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public PanResponseDetails getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(PanResponseDetails responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getVerifiedData() {
        return verifiedData;
    }

    public void setVerifiedData(String verifiedData) {
        this.verifiedData = verifiedData;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerifiedUsing() {
        return verifiedUsing;
    }

    public void setVerifiedUsing(String verifiedUsing) {
        this.verifiedUsing = verifiedUsing;
    }
}
