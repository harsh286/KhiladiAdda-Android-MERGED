package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class GameParticipantsDataResponse extends BaseResponse {

    @SerializedName("response")
    private List<ResponseItem> response = null;

    public List<ResponseItem> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseItem> response) {
        this.response = response;
    }
}

//class Response {
//    @SerializedName("user_info")
//    private UserInfo userInfo;
//
//    public UserInfo getUserInfo() {
//        return userInfo;
//    }
//
//    public void setUserInfo(UserInfo userInfo) {
//        this.userInfo = userInfo;
//    }
//
//
//}
//
//class UserInfo {
//    @SerializedName("name")
//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}
