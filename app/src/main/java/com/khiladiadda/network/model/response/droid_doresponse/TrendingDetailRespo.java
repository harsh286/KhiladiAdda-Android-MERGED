package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingDetailRespo {
        @SerializedName("n_attempts")
        @Expose
        private Integer nAttempts;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("tournament_id")
        @Expose
        private String tournamentId;
        @SerializedName("ref_no")
        @Expose
        private String refNo;

        public Integer getnAttempts() {
            return nAttempts;
        }

        public void setnAttempts(Integer nAttempts) {
            this.nAttempts = nAttempts;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTournamentId() {
            return tournamentId;
        }

        public void setTournamentId(String tournamentId) {
            this.tournamentId = tournamentId;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }


}
