package com.khiladiadda.network.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RummyGameModel {
        @SerializedName("redirectionType")
        @Expose
        private String redirectionType;
        @SerializedName("redirectionParams")
        @Expose
        private RummyGameRedirectionParams redirectionParams;
        public String getRedirectionType() {
            return redirectionType;
        }
        public void setRedirectionType(String redirectionType) {
            this.redirectionType = redirectionType;
        }
        public RummyGameRedirectionParams getRedirectionParams() {
            return redirectionParams;
        }
        public void setRedirectionParams(RummyGameRedirectionParams redirectionParams) {
            this.redirectionParams = redirectionParams;
        }

}
