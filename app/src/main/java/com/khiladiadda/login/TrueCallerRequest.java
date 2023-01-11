package com.khiladiadda.login;

import com.google.gson.annotations.SerializedName;

public class TrueCallerRequest {
    @SerializedName("payload")
    String payload;
    @SerializedName("signature")
    String signature;

    @SerializedName("signatureAlgorithm")
    String signatureAlgorithm;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }
}
