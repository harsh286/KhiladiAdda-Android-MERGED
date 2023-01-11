package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanVerifyRequest {

    @SerializedName("pan_details")
    @Expose
    private PanVerifyDetails panDetails;
    @SerializedName("consent")
    @Expose
    private String consent;
    @SerializedName("consent_message")
    @Expose
    private String consentMessage;

    public PanVerifyDetails getPanDetails() {
        return panDetails;
    }

    public void setPanDetails(PanVerifyDetails panDetails) {
        this.panDetails = panDetails;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public String getConsentMessage() {
        return consentMessage;
    }

    public void setConsentMessage(String consentMessage) {
        this.consentMessage = consentMessage;
    }

    public PanVerifyRequest(PanVerifyDetails panDetails, String consent, String consentMessage) {
        this.panDetails = panDetails;
        this.consent = consent;
        this.consentMessage = consentMessage;
    }
}