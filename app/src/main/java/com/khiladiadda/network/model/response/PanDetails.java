package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanDetails {

    @SerializedName("name") @Expose private String name;
    @SerializedName("number") @Expose private String number;
    @SerializedName("url") @Expose private String url;
    @SerializedName("pending_number") @Expose private String pendingNumber;
    @SerializedName("pending_name") @Expose private String pendingName;
    @SerializedName("pending_url") @Expose private String pendingUrl;
    @SerializedName("pan_updated_at") @Expose private String panUpdatedAt;
    @SerializedName("pan_approved_at") @Expose private String panApprovedAt;
    @SerializedName("reject_reason") @Expose private String rejectReason;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPendingNumber() {
        return pendingNumber;
    }

    public void setPendingNumber(String pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    public String getPendingName() {
        return pendingName;
    }

    public void setPendingName(String pendingName) {
        this.pendingName = pendingName;
    }

    public String getPendingUrl() {
        return pendingUrl;
    }

    public void setPendingUrl(String pendingUrl) {
        this.pendingUrl = pendingUrl;
    }

    public String getPanUpdatedAt() {
        return panUpdatedAt;
    }

    public void setPanUpdatedAt(String panUpdatedAt) {
        this.panUpdatedAt = panUpdatedAt;
    }

    public String getPanApprovedAt() {
        return panApprovedAt;
    }

    public void setPanApprovedAt(String panApprovedAt) {
        this.panApprovedAt = panApprovedAt;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

}