package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class BattleResponseHTH extends BaseResponse {
    @SerializedName("response") @Expose private List<BattlesDeatilsHTH> response = null;
    @SerializedName("highestAmountBattle") @Expose private long highestAmountBattle;

    public List<BattlesDeatilsHTH> getResponse() {
        return response;
    }

    public void setResponse(List<BattlesDeatilsHTH> response) {
        this.response = response;
    }

    public long getHighestAmountBattle() {
        return highestAmountBattle;
    }

    public void setHighestAmountBattle(long highestAmountBattle) {
        this.highestAmountBattle = highestAmountBattle;
    }

    @SerializedName("is_lines_up")
    @Expose
    private boolean is_lines_up;

    public boolean isIs_lines_up() {
        return is_lines_up;
    }

    public void setIs_lines_up(boolean is_lines_up) {
        this.is_lines_up = is_lines_up;
    }
}
