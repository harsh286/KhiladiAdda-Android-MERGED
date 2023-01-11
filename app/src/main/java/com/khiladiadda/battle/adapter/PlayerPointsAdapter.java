package com.khiladiadda.battle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.battle.model.Player;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerPointsAdapter extends RecyclerView.Adapter<PlayerPointsAdapter.PersonViewHolder> {

    private List<Player> mPlayerList;

    public PlayerPointsAdapter(List<Player> playerList) {
        this.mPlayerList = playerList;
    }

    private Player getItemAt(int index) {
        return mPlayerList.get(index);
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player_points, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        Player details = getItemAt(position);
        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(personViewHolder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(personViewHolder.mProfileIV);
        } else {
            Glide.with(personViewHolder.mProfileIV.getContext()).clear(personViewHolder.mProfileIV);
            personViewHolder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        personViewHolder.mNameTV.setText(details.getTitle());
        personViewHolder.mOneRunScoreTV.setText(String.valueOf(details.getScore().getOneRun()));
        personViewHolder.mFourScoreTV.setText(String.valueOf(details.getScore().getFourRun()));
        personViewHolder.mSixScoreTV.setText(String.valueOf(details.getScore().getSixRun()));
        personViewHolder.mFiftyScoreTV.setText(String.valueOf(details.getScore().getFifty()));
        personViewHolder.mHundredScoreTV.setText(String.valueOf(details.getScore().getHundred()));
        personViewHolder.mStumpScoreTV.setText(String.valueOf(details.getScore().getStumpOut()));
        personViewHolder.mDirectHitScoreTV.setText(String.valueOf(details.getScore().getDirecthit()));
        personViewHolder.mRunoutScoreTV.setText(String.valueOf(details.getScore().getRunOut()));
        personViewHolder.mLbwScoreTV.setText(String.valueOf(details.getScore().getLbwOut()));
        personViewHolder.mCatchStumpScoreTV.setText(String.valueOf(details.getScore().getCatch()));
        personViewHolder.mThreeWicketScoreTV.setText(String.valueOf(details.getScore().getThreeWickets()));
        personViewHolder.mFiveWicketScoreTV.setText(String.valueOf(details.getScore().getFiveWickets()));
        personViewHolder.mCatchOnFieldScoreTV.setText(String.valueOf(details.getScore().getFieldCatch()));
        personViewHolder.mOneRunTV.setText(String.valueOf(details.getPoints().getOneRun()));
        personViewHolder.mFourTV.setText(String.valueOf(details.getPoints().getFourRun()));
        personViewHolder.mSixTV.setText(String.valueOf(details.getPoints().getSixRun()));
        personViewHolder.mFiftyTV.setText(String.valueOf(details.getPoints().getFifty()));
        personViewHolder.mHundredTV.setText(String.valueOf(details.getPoints().getHundred()));
        personViewHolder.mStumpTV.setText(String.valueOf(details.getPoints().getStumpOut()));
        personViewHolder.mDirectHitTV.setText(String.valueOf(details.getPoints().getDirecthit()));
        personViewHolder.mRunOutTV.setText(String.valueOf(details.getPoints().getRunOut()));
        personViewHolder.mLbwBowledTV.setText(String.valueOf(details.getPoints().getLbwOut()));
        personViewHolder.mCatchStumpTV.setText(String.valueOf(details.getPoints().getCatch()));
        personViewHolder.mThreeWicketTV.setText(String.valueOf(details.getPoints().getThreeWickets()));
        personViewHolder.mFiveWicketTV.setText(String.valueOf(details.getPoints().getFiveWickets()));
        personViewHolder.mCatchOnfieldTV.setText(String.valueOf(details.getPoints().getFieldCatch()));
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_one_run_score)
        TextView mOneRunScoreTV;
        @BindView(R.id.tv_four_score)
        TextView mFourScoreTV;
        @BindView(R.id.tv_six_score)
        TextView mSixScoreTV;
        @BindView(R.id.tv_fifty_score)
        TextView mFiftyScoreTV;
        @BindView(R.id.tv_hundred_score)
        TextView mHundredScoreTV;
        @BindView(R.id.tv_stump_score)
        TextView mStumpScoreTV;
        @BindView(R.id.tv_directhit_score)
        TextView mDirectHitScoreTV;
        @BindView(R.id.tv_runout_score)
        TextView mRunoutScoreTV;
        @BindView(R.id.tv_lbw_score)
        TextView mLbwScoreTV;
        @BindView(R.id.tv_catchstump_score)
        TextView mCatchStumpScoreTV;
        @BindView(R.id.tv_three_wicket_score)
        TextView mThreeWicketScoreTV;
        @BindView(R.id.tv_five_wicket_score)
        TextView mFiveWicketScoreTV;
        @BindView(R.id.tv_catchonfield_score)
        TextView mCatchOnFieldScoreTV;
        @BindView(R.id.tv_one_run)
        TextView mOneRunTV;
        @BindView(R.id.tv_four)
        TextView mFourTV;
        @BindView(R.id.tv_six)
        TextView mSixTV;
        @BindView(R.id.tv_fifty)
        TextView mFiftyTV;
        @BindView(R.id.tv_hundred)
        TextView mHundredTV;
        @BindView(R.id.tv_stump)
        TextView mStumpTV;
        @BindView(R.id.tv_directhit)
        TextView mDirectHitTV;
        @BindView(R.id.tv_rumout)
        TextView mRunOutTV;
        @BindView(R.id.tv_lbw_bowled)
        TextView mLbwBowledTV;
        @BindView(R.id.tv_catch_stumpout)
        TextView mCatchStumpTV;
        @BindView(R.id.tv_three_wicket)
        TextView mThreeWicketTV;
        @BindView(R.id.tv_five_wicket)
        TextView mFiveWicketTV;
        @BindView(R.id.tv_catch)
        TextView mCatchOnfieldTV;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}