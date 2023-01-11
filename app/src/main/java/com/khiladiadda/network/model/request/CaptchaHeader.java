package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptchaHeader {

    @SerializedName("client_code")
    @Expose
    private String clientCode;
    @SerializedName("sub_client_code")
    @Expose
    private String subClientCode;
    @SerializedName("channel_code")
    @Expose
    private String channelCode;
    @SerializedName("channel_version")
    @Expose
    private String channelVersion;
    @SerializedName("stan")
    @Expose
    private String stan;
    @SerializedName("client_ip")
    @Expose
    private String clientIp;
    @SerializedName("transmission_datetime")
    @Expose
    private String transmissionDatetime;
    @SerializedName("operation_mode")
    @Expose
    private String operationMode;
    @SerializedName("run_mode")
    @Expose
    private String runMode;
    @SerializedName("actor_type")
    @Expose
    private String actorType;
    @SerializedName("user_handle_type")
    @Expose
    private String userHandleType;
    @SerializedName("user_handle_value")
    @Expose
    private String userHandleValue;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("function_code")
    @Expose
    private String functionCode;
    @SerializedName("function_sub_code")
    @Expose
    private String functionSubCode;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getSubClientCode() {
        return subClientCode;
    }

    public void setSubClientCode(String subClientCode) {
        this.subClientCode = subClientCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelVersion() {
        return channelVersion;
    }

    public void setChannelVersion(String channelVersion) {
        this.channelVersion = channelVersion;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getTransmissionDatetime() {
        return transmissionDatetime;
    }

    public void setTransmissionDatetime(String transmissionDatetime) {
        this.transmissionDatetime = transmissionDatetime;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public String getUserHandleType() {
        return userHandleType;
    }

    public void setUserHandleType(String userHandleType) {
        this.userHandleType = userHandleType;
    }

    public String getUserHandleValue() {
        return userHandleValue;
    }

    public void setUserHandleValue(String userHandleValue) {
        this.userHandleValue = userHandleValue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionSubCode() {
        return functionSubCode;
    }

    public void setFunctionSubCode(String functionSubCode) {
        this.functionSubCode = functionSubCode;
    }

    public CaptchaHeader(String clientCode, String subClientCode, String channelCode, String channelVersion, String stan, String clientIp, String transmissionDatetime, String operationMode, String runMode, String actorType, String userHandleType, String userHandleValue, String location, String functionCode, String functionSubCode) {
        this.clientCode = clientCode;
        this.subClientCode = subClientCode;
        this.channelCode = channelCode;
        this.channelVersion = channelVersion;
        this.stan = stan;
        this.clientIp = clientIp;
        this.transmissionDatetime = transmissionDatetime;
        this.operationMode = operationMode;
        this.runMode = runMode;
        this.actorType = actorType;
        this.userHandleType = userHandleType;
        this.userHandleValue = userHandleValue;
        this.location = location;
        this.functionCode = functionCode;
        this.functionSubCode = functionSubCode;
    }
}
