package com.khiladiadda.battle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.battle.model.BattleDetails;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BattleAdapter extends RecyclerView.Adapter<BattleAdapter.PersonViewHolder> {

    private int mFrom;
    private boolean mInReview;
    private List<BattleDetails> mBattleList;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public BattleAdapter(List<BattleDetails> eventList, int from, boolean inReview) {
        this.mBattleList = eventList;
        this.mFrom = from;
        this.mInReview = inReview;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private BattleDetails getItemAt(int index) {
        return mBattleList.get(index);
    }

    @Override
    public int getItemCount() {
        return mBattleList.size();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_battle, viewGroup, false);
        return new PersonViewHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        BattleDetails details = getItemAt(position);
        if (!TextUtils.isEmpty(details.getImg()) && details.getImg().startsWith("https")) {
            Glide.with(personViewHolder.mProfileIV.getContext()).load(details.getImg()).placeholder(R.drawable.profile).into(personViewHolder.mProfileIV);
        } else {
            Glide.with(personViewHolder.mProfileIV.getContext()).clear(personViewHolder.mProfileIV);
            personViewHolder.mProfileIV.setImageResource(R.drawable.splash_logo);
        }
        personViewHolder.mGroupTV.setText(details.getNGroups() + " Combos");
        if (details.isFree()) {
            personViewHolder.mNameTV.setText(details.getTitle() + " : Free Battle");
            personViewHolder.mLayoutRL.setBackgroundResource(R.color.light_red_white);
            personViewHolder.mParticipantTV.setText("Max Participant " + details.getTotalParticipants());
            personViewHolder.mAmountTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getPrize()));
        } else {
            if (details.getBonusCode() == 0) {
                personViewHolder.mNameTV.setText(details.getTitle());
            } else {
                personViewHolder.mNameTV.setText(details.getTitle() + " Use (" + details.getBonusCode() + "% from Bonus)");
            }
            if (details.getNParticipants() > 0) {
                personViewHolder.mParticipantTV.setText(details.getNParticipants() + " Players participated");
            } else {
                personViewHolder.mParticipantTV.setText(R.string.text_first_participate);
            }
            if (details.isContest()) {
                personViewHolder.mAmountTV.setText("\u20B9" + new DecimalFormat("##.##").format(details.getLivePrizePool()));
            } else {
                personViewHolder.mAmountTV.setText("\u20B9" + new DecimalFormat("##.##").format(AppUtilityMethods.getBattleEstimatedWinning(details.getInvestedAmount(), details.getInvestedAmount(), details.getnGroupJoined())));
            }
            personViewHolder.mLayoutRL.setBackgroundResource(R.color.colorWhite);
        }
        if (mFrom == AppConstant.FROM_FANBATTLE_LIVE) {
            if (mInReview) {
                personViewHolder.mPlayBTN.setText(R.string.text_in_review);
            } else {
                personViewHolder.mPlayBTN.setText(R.string.text_live_stat);
            }
        } else if (mFrom == AppConstant.FROM_FANBATTLE_PAST) {
            personViewHolder.mPlayBTN.setText(R.string.text_view_result);
        } else if (details.isPlayed()) {
            personViewHolder.mPlayBTN.setText(R.string.text_invest_more);
        } else {
            personViewHolder.mPlayBTN.setText(R.string.text_play_now);
        }
        if (details.isDeleted()) {
            personViewHolder.mPlayBTN.setText(R.string.txt_canceled);
            personViewHolder.mPlayBTN.setClickable(false);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.layout_1)
        RelativeLayout mLayoutRL;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_group)
        TextView mGroupTV;
        @BindView(R.id.tv_participant)
        TextView mParticipantTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.btn_play)
        Button mPlayBTN;
        @BindView(R.id.pb_profile_progress)
        ProgressBar mProgressPB;

        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            mPlayBTN.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_play) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onPlayClicked(getBindingAdapterPosition());
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onPlayClicked(int position);
    }

}