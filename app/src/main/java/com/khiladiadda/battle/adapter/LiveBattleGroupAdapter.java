package com.khiladiadda.battle.adapter;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.battle.model.GroupDetails;
import com.khiladiadda.battle.model.Player;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveBattleGroupAdapter extends RecyclerView.Adapter<LiveBattleGroupAdapter.PersonViewHolder> {

    private List<GroupDetails> mGroupList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private double mBattleInvestment, mFreeBattlePrizePool;
    private long mGroupJoined;
    private boolean mIsFreeBattle;

    public LiveBattleGroupAdapter(List<GroupDetails> mGroupList, double battleInvestment, long groupJoined, double freeBattlePrizePool, boolean isFreeBattle) {
        this.mGroupList = mGroupList;
        this.mBattleInvestment = battleInvestment;
        this.mGroupJoined = groupJoined;
        this.mIsFreeBattle = isFreeBattle;
        this.mFreeBattlePrizePool = freeBattlePrizePool;
    }

    public void setTopScore() {
        if (mGroupList.size() > 0) {
            long mTopScore = mGroupList.get(0).getPoints();
        }
    }

    public void setSelectedIndex(int index) {
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private GroupDetails getItemAt(int index) {
        return mGroupList.get(index);
    }

    @Override public int getItemCount() {
        return mGroupList.size();
    }

    @NonNull
    @Override public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live_group, viewGroup, false);
        return new PersonViewHolder(v, mBattleInvestment, mGroupJoined, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        GroupDetails details = getItemAt(position);
        updateColors(personViewHolder, details, position);
        for (int playerIndex = 0; playerIndex < details.getPlayers().size(); playerIndex++) {
            Player player = details.getPlayers().get(playerIndex);
            personViewHolder.setPlayerVisibility(playerIndex, View.VISIBLE);
            personViewHolder.setPlayerName(player, playerIndex);
            personViewHolder.setPlayerImage(player, playerIndex);
        }
        for (int playerIndex = details.getPlayers().size(); playerIndex < 4; playerIndex++) {
            personViewHolder.setPlayerVisibility(playerIndex, View.GONE);
        }
        if (details.isPlayed()) {
            personViewHolder.mInvestmentRL.setVisibility(View.VISIBLE);
            personViewHolder.setInvestedAmount(details);
            personViewHolder.setEstimatedProfit(details, mFreeBattlePrizePool, mIsFreeBattle);
        } else {
            personViewHolder.mInvestmentRL.setVisibility(View.GONE);
        }
        if (details.getPoints() != 0) {
            personViewHolder.mPointsTV.setText(String.valueOf(details.getPoints()));
        } else {
            personViewHolder.mPointsTV.setText(R.string.text_zero);
        }
    }

    private void updateColors(PersonViewHolder personViewHolder, GroupDetails details, int position) {
        if (details.isPlayed()) {
            if (details.isWinner()) {
                personViewHolder.mGroupFL.setBackgroundResource(R.drawable.live_group_bg_green);
                personViewHolder.mLiveGroupView.setBackgroundResource(R.color.colorLiveLead);
                personViewHolder.mParticipantTV.setText("You and " + (details.getNParticipants() - 1) + " others are winning");
                setUnderLineOnParticipant(personViewHolder);
            } else {
                personViewHolder.mGroupFL.setBackgroundResource(R.drawable.live_group_bg_yellow);
                personViewHolder.mLiveGroupView.setBackgroundResource(R.color.colorLivePlayed);
                personViewHolder.mParticipantTV.setText("You and " + (details.getNParticipants() - 1) + " others are competing");
                setUnderLineOnParticipant(personViewHolder);
            }
        } else {
            personViewHolder.mGroupFL.setBackgroundResource(R.drawable.live_group_bg_default);
            personViewHolder.mLiveGroupView.setBackgroundResource(R.color.light_background);
            personViewHolder.layout.setVisibility(View.GONE);
            personViewHolder.mInvestmentRL.setVisibility(View.GONE);
        }
    }

    private void setUnderLineOnParticipant(PersonViewHolder personViewHolder) {
        SpannableString spannableStringObject = new SpannableString(personViewHolder.mParticipantTV.getText().toString().trim());
        spannableStringObject.setSpan(new UnderlineSpan(), 0, spannableStringObject.length(), 0);
        personViewHolder.mParticipantTV.setText(spannableStringObject);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fl_group) FrameLayout mGroupFL;
        @BindView(R.id.ll_item_group_1) LinearLayout mGroupOneLL;
        @BindView(R.id.ll_item_group_2) LinearLayout mGroupTwoLL;
        @BindView(R.id.ll_item_group_3) LinearLayout mGroupThreeLL;
        @BindView(R.id.ll_item_group_4) LinearLayout mGroupFourLL;
        @BindView(R.id.rl_investment) RelativeLayout mInvestmentRL;
        @BindView(R.id.tv_invested_amount) TextView mInvestedAmountTV;
        @BindView(R.id.tv_estimated_profit) TextView mEstimatedProfitTV;
        @BindView(R.id.tv_total_participant) TextView mParticipantTV;
        @BindView(R.id.tv_points) TextView mPointsTV;
        @BindView(R.id.view_live_group_div) View mLiveGroupView;
        @BindView(R.id.parent) RelativeLayout layout;
        LinearLayout[] mIds;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;
        private double mBattleInvestment;
        private long mGroupJoined;

        public PersonViewHolder(View itemView, double battleInvestment, long groupJoined, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBattleInvestment = battleInvestment;
            mGroupJoined = groupJoined;
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            mIds = new LinearLayout[]{mGroupOneLL, mGroupTwoLL, mGroupThreeLL, mGroupFourLL};
            mInvestedAmountTV.setOnClickListener(this);
            mEstimatedProfitTV.setOnClickListener(this);
            mParticipantTV.setOnClickListener(this);
            mPointsTV.setOnClickListener(this);
        }

        void setPlayerVisibility(int position, int visibility) {
            mIds[position].setVisibility(visibility);
        }

        void setPlayerName(Player player, int position) {
            ((TextView) mIds[position].findViewById(R.id.tv_player)).setText(player.getTitle());
        }

        void setPlayerImage(Player player, int position) {
            String playerImage = player.getImg();
            ImageView playerIV = mIds[position].findViewById(R.id.iv_image);
            if (!TextUtils.isEmpty(playerImage) && playerImage.startsWith("https")) {
                Glide.with(playerIV.getContext()).load(playerImage).placeholder(R.drawable.profile).into(playerIV);
            } else {
                Glide.with(playerIV.getContext()).clear(playerIV);
                playerIV.setImageResource(R.mipmap.ic_launcher);
            }
        }

        void setInvestedAmount(GroupDetails details) {
            mInvestedAmountTV.setText("Invested Amount ₹" + new DecimalFormat("##.##").format(details.getPlayed().getAmount()));
            SpannableString ss1 = new SpannableString(mInvestedAmountTV.getText().toString().trim());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 16, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(mInvestedAmountTV.getContext().getResources().getColor(R.color.colorWhite)), 16, ss1.length(), 0);// set color
            mInvestedAmountTV.setText(ss1);
        }

        void setEstimatedProfit(GroupDetails details, double mFreeBattlePrizePool, boolean mIsFreeBattle) {
            if(mIsFreeBattle){
                mEstimatedProfitTV.setText("Exact Winning ₹" + new DecimalFormat("##.##").format(mFreeBattlePrizePool/details.getNParticipants()));
            }else{
                mEstimatedProfitTV.setText("Exact Winning ₹" + new DecimalFormat("##.##").format(AppUtilityMethods.getGroupEstimatedWinning(mBattleInvestment, details.getInvestedAmount(), mGroupJoined, details.getPlayed().getAmount())));
            }
            SpannableString ss2 = new SpannableString(mEstimatedProfitTV.getText().toString().trim());
            ss2.setSpan(new StyleSpan(Typeface.BOLD), 15, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(mEstimatedProfitTV.getContext().getResources().getColor(R.color.colorWhite)), 15, ss2.length(), 0);// set color
            mEstimatedProfitTV.setText(ss2);
        }

        void setVisibility(View view, int visibility) {
            view.setVisibility(visibility);
        }

        @Override public void onClick(View v) {
            int position = getBindingAdapterPosition();
            switch (v.getId()) {
                case R.id.tv_points:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onPointsClicked(position);
                    }
                    break;
                case R.id.tv_invested_amount:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onInvestedAmountClicked(position);
                    }
                    break;
                case R.id.tv_estimated_profit:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onEstimatedProfitClicked(position);
                    }
                    break;
                case R.id.tv_total_participant:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onParticipantClicked(position);
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position, 0);
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onPointsClicked(int position);

        void onInvestedAmountClicked(int position);

        void onEstimatedProfitClicked(int position);

        void onParticipantClicked(int position);

    }

}