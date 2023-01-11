package com.khiladiadda.clashx2.football.createbattle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.hth.CaptainResultHTH;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballPlayerStatusAdapter extends RecyclerView.Adapter<FootballPlayerStatusAdapter.PersonViewHolder> {

    private List<CaptainResultHTH> mPlayerList;

    public FootballPlayerStatusAdapter(List<CaptainResultHTH> playerList) {
        this.mPlayerList = playerList;
    }

    private CaptainResultHTH getItemAt(int index) {
        return mPlayerList.get(index);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_football_player_points, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        CaptainResultHTH details = mPlayerList.get(position);
        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(personViewHolder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(personViewHolder.mProfileIV);
        } else {
            Glide.with(personViewHolder.mProfileIV.getContext()).clear(personViewHolder.mProfileIV);
            personViewHolder.mProfileIV.setImageResource(R.drawable.splash_logo);
        }
        personViewHolder.mNameTV.setText(details.getTitle());
        personViewHolder.mTvGoalScored.setText(String.valueOf(details.getFbScore().getAttackGoal()));
        personViewHolder.mTvAssists.setText(String.valueOf(details.getFbScore().getAttackAssist()));
        personViewHolder.mTvForEvery5PassesCompleted.setText(String.valueOf(details.getFbScore().getAttackFivePass()));
        personViewHolder.mTvTackleWon.setText(String.valueOf(details.getFbScore().getDefenceTackleWon()));
        personViewHolder.mTvInterceptionWon.setText(String.valueOf(details.getFbScore().getDefenceInterceptionWon()));
        personViewHolder.mTvSavesByGoalkeeper.setText(String.valueOf(details.getFbScore().getDefenceSaveGoalkeeper()));
        personViewHolder.mTvPenaltySaved.setText(String.valueOf(details.getFbScore().getDefencePenalitySaved()));
        personViewHolder.mTvSuper60.setText(String.valueOf(details.getFbScore().getBonusPointSuperSixty()));
        personViewHolder.mTvYellowCardScore.setText(String.valueOf(details.getFbScore().getPenalitiesYellowCard()));
        personViewHolder.mTvRedCardScore.setText(String.valueOf(details.getFbScore().getPenalitiesRedCard()));
        personViewHolder.mTvOwnGoalScore.setText(String.valueOf(details.getFbScore().getPenalitiesOwnGoal()));
//        personViewHolder.mFiveWicketScoreTV.setText(String.valueOf(details.getScore().getFiveWickets()));
//        personViewHolder.mCatchOnFieldScoreTV.setText(String.valueOf(details.getScore().getFieldCatch()));
        personViewHolder.mTvGoalScoredPoints.setText(String.valueOf(details.getFbPoints().getAttackGoal()));
        personViewHolder.mTvAssistsPoints.setText(String.valueOf(details.getFbPoints().getAttackAssist()));
        personViewHolder.mTvForEvery5PassesCompletedPoints.setText(String.valueOf(details.getFbPoints().getAttackFivePass()));
        personViewHolder.mTvTackleWonPoints.setText(String.valueOf(details.getFbPoints().getDefenceTackleWon()));
        personViewHolder.mTvInterceptionWonPoints.setText(String.valueOf(details.getFbPoints().getDefenceInterceptionWon()));
        personViewHolder.mTvSavesByGoalkeeperPoints.setText(String.valueOf(details.getFbPoints().getDefenceSaveGoalkeeper()));
        personViewHolder.mTvPenaltySavedPoints.setText(String.valueOf(details.getFbPoints().getDefencePenalitySaved()));
        personViewHolder.mTvSuper60Points.setText(String.valueOf(details.getFbPoints().getBonusPointSuperSixty()));
        personViewHolder.mTvYellowCardFootballPoints.setText(String.valueOf(details.getFbPoints().getPenalitiesYellowCard()));
        personViewHolder.mTvRedCardFootballPoints.setText(String.valueOf(details.getFbPoints().getPenalitiesRedCard()));
        personViewHolder.mTvOwnGoalScorePoints.setText(String.valueOf(details.getFbPoints().getPenalitiesOwnGoal()));
//        personViewHolder.mFiveWicketTV.setText(String.valueOf(details.getPoints().getFiveWickets()));
//        personViewHolder.mCatchOnfieldTV.setText(String.valueOf(details.getPoints().getFieldCatch()));
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_goal_scored)
        TextView mTvGoalScored;
        @BindView(R.id.tv_assists)
        TextView mTvAssists;
        @BindView(R.id.tv_for_every_5_passes_completed)
        TextView mTvForEvery5PassesCompleted;
        @BindView(R.id.tv_tackle_won)
        TextView mTvTackleWon;
        @BindView(R.id.tv_interception_won)
        TextView mTvInterceptionWon;
        @BindView(R.id.tv_saves_by_goalkeeper)
        TextView mTvSavesByGoalkeeper;
        @BindView(R.id.tv_penalty_saved)
        TextView mTvPenaltySaved;
        @BindView(R.id.tv_super_60)
        TextView mTvSuper60;
        @BindView(R.id.tv_yellow_card_score)
        TextView mTvYellowCardScore;
        @BindView(R.id.tv_red_card_score)
        TextView mTvRedCardScore;
        @BindView(R.id.tv_own_goal_score)
        TextView mTvOwnGoalScore;
//        @BindView(R.id.tv_opposition_all_out)
//        TextView mFiveWicketScoreTV;
//        @BindView(R.id.tv_green_card)
//        TextView mCatchOnFieldScoreTV;
//        @BindView(R.id.tv_yellow_card)
//        TextView mCatchOnFieeldYellowTV;
//        @BindView(R.id.tv_red_card)
//        TextView mCatchOnFieldRedTV;


        @BindView(R.id.tv_goal_scored_points)
        TextView mTvGoalScoredPoints;
        @BindView(R.id.tv_assists_points)
        TextView mTvAssistsPoints;
        @BindView(R.id.tv_for_every_5_passes_completed_points)
        TextView mTvForEvery5PassesCompletedPoints;
        @BindView(R.id.tv_tackle_won_points)
        TextView mTvTackleWonPoints;
        @BindView(R.id.tv_interception_won_points)
        TextView mTvInterceptionWonPoints;
        @BindView(R.id.tv_saves_by_goalkeeper_points)
        TextView mTvSavesByGoalkeeperPoints;
        @BindView(R.id.tv_penalty_saved_points)
        TextView mTvPenaltySavedPoints;
        @BindView(R.id.tv_super_60_points)
        TextView mTvSuper60Points;
        @BindView(R.id.tv_yellow_card_football_points)
        TextView mTvYellowCardFootballPoints;
        @BindView(R.id.tv_red_card_football_points)
        TextView mTvRedCardFootballPoints;
        @BindView(R.id.tv_own_goal_score_points)
        TextView mTvOwnGoalScorePoints;
//        @BindView(R.id.tv_opposition_all_out_points)
//        TextView mFiveWicketTV;
//        @BindView(R.id.tv_green_card_points)
//        TextView mCatchOnfieldTV;
//        @BindView(R.id.tv_yellow_card_points)
//        TextView mCatchOnYellowTV;
//        @BindView(R.id.tv_red_card_points)
//        TextView mCatchOnfieldRedTV;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}