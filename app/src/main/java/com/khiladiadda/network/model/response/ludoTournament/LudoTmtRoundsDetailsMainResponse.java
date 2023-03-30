package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoTmtRoundsDetailsMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LudoTmtRoundsDetailsResponse> response = null;
    @SerializedName("tournamentDetails")
    @Expose
    private LudoTmtTournamentDetailsResponse tournamentDetails;
    @SerializedName("isExist")
    @Expose
    private boolean isExist;

    @SerializedName("isParticipantsFull")
    @Expose
    private boolean isParticipantsFull;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public LudoTmtTournamentDetailsResponse getTournamentDetails() {
        return tournamentDetails;
    }

    public void setTournamentDetails(LudoTmtTournamentDetailsResponse tournamentDetails) {
        this.tournamentDetails = tournamentDetails;
    }

    public List<LudoTmtRoundsDetailsResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LudoTmtRoundsDetailsResponse> response) {
        this.response = response;
    }

    public boolean isParticipantsFull() {
        return isParticipantsFull;
    }

    public void setParticipantsFull(boolean participantsFull) {
        isParticipantsFull = participantsFull;
    }
}
