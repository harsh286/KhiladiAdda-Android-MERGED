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
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BattleGroupAdapter extends RecyclerView.Adapter<BattleGroupAdapter.PersonViewHolder> {

    private List<GroupDetails> mGroupList;
    private IOnItemChildClickListener mOnItemChildClickListener;
    private double mBattleInvestment, mFreeBattlePrizePool;
    private long mGroupJoined;
    private int mSelectedIndex = -1;
    private boolean mIsFreeBattle;

    public BattleGroupAdapter(List<GroupDetails> mGroupList, double battleInvestment, long groupJoined, boolean freeBattle, double battlePrizePool) {
        this.mGroupList = mGroupList;
        this.mBattleInvestment = battleInvestment;
        this.mGroupJoined = groupJoined;
        this.mIsFreeBattle = freeBattle;
        this.mFreeBattlePrizePool = battlePrizePool;
    }

    public void setSelectedIndex(int index) {
        this.mSelectedIndex = index;
    }

    public void updateData(double investedAmount, long groupJoined) {
        mBattleInvestment = investedAmount;
        mGroupJoined = groupJoined;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private GroupDetails getItemAt(int index) {
        return mGroupList.get(index);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, viewGroup, false);
        return new PersonViewHolder(v, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        GroupDetails details = getItemAt(position);
        personViewHolder.mGroupFL.setSelected(position == mSelectedIndex || details.isPlayed());
        for (int playerIndex = 0; playerIndex < details.getPlayers().size(); playerIndex++) {
            Player player = details.getPlayers().get(playerIndex);
            personViewHolder.setPlayerVisibility(playerIndex, View.VISIBLE);
            personViewHolder.setPlayerName(player, playerIndex);
            personViewHolder.setPlayerImage(player, playerIndex);
            personViewHolder.setPlayerViewTag(playerIndex);
        }
        for (int playerIndex = details.getPlayers().size(); playerIndex < 4; playerIndex++) {
            personViewHolder.setPlayerVisibility(playerIndex, View.GONE);
        }
        if (details.isPlayed() && mGroupList.get(position).isSubstitute()) {
            personViewHolder.mChangeTV.setVisibility(View.VISIBLE);
            personViewHolder.mInvestTV.setVisibility(View.VISIBLE);
            personViewHolder.mInvestmentRL.setVisibility(View.VISIBLE);
            personViewHolder.mInvestedAmountTV.setText(String.format(Locale.getDefault(), "Invested Amount ₹%s", new DecimalFormat("##.##").format(details.getPlayed().getAmount())));
            SpannableString ss1 = new SpannableString(personViewHolder.mInvestedAmountTV.getText().toString().trim());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 16, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(personViewHolder.mInvestedAmountTV.getContext().getResources().getColor(R.color.light_gray)), 17, ss1.length(), 0);// set color
            personViewHolder.mInvestedAmountTV.setText(ss1);
            if (mIsFreeBattle) {
                personViewHolder.mEstimatedProfitTV.setText(String.format(Locale.getDefault(), "Estimated Earnings ₹%s", new DecimalFormat("##.##").format(mFreeBattlePrizePool / details.getNParticipants())));
            } else {
                personViewHolder.mEstimatedProfitTV.setText(String.format(Locale.getDefault(), "Estimated Earnings ₹%s", new DecimalFormat("##.##").format(AppUtilityMethods.getGroupEstimatedWinning(mBattleInvestment, details.getInvestedAmount(), mGroupJoined, details.getPlayed().getAmount()))));
            }
            SpannableString ss2 = new SpannableString(personViewHolder.mEstimatedProfitTV.getText().toString().trim());
            ss2.setSpan(new StyleSpan(Typeface.BOLD), 15, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(personViewHolder.mEstimatedProfitTV.getContext().getResources().getColor(R.color.light_gray)), 19, ss2.length(), 0);// set color
            personViewHolder.mEstimatedProfitTV.setText(ss2);
            personViewHolder.mParticipantTV.setVisibility(View.VISIBLE);
            personViewHolder.mSelectedbyTv.setVisibility(View.VISIBLE);
            personViewHolder.mParticipantTV.setText(String.format(Locale.getDefault(), "%d Players", details.getNParticipants()));
            setUnderLineOnParticipant(personViewHolder);
        } else if (details.isPlayed() && mIsFreeBattle) {
            personViewHolder.mChangeTV.setVisibility(View.GONE);
            personViewHolder.mInvestTV.setVisibility(View.GONE);
            personViewHolder.mInvestmentRL.setVisibility(View.VISIBLE);
            personViewHolder.mParticipantTV.setVisibility(View.VISIBLE);
            personViewHolder.mSelectedbyTv.setVisibility(View.VISIBLE);
            personViewHolder.mEstimatedProfitTV.setText(String.format(Locale.getDefault(), "Estimated Earnings ₹%s", new DecimalFormat("##.##").format(mFreeBattlePrizePool / details.getNParticipants())));
            SpannableString ss2 = new SpannableString(personViewHolder.mEstimatedProfitTV.getText().toString().trim());
            ss2.setSpan(new StyleSpan(Typeface.BOLD), 15, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(personViewHolder.mEstimatedProfitTV.getContext().getResources().getColor(R.color.light_gray)), 19, ss2.length(), 0);// set color
            personViewHolder.mEstimatedProfitTV.setText(ss2);
            personViewHolder.mInvestedAmountTV.setText(String.format(Locale.getDefault(), "Invested Amount ₹%s", new DecimalFormat("##.##").format(details.getPlayed().getAmount())));
            SpannableString ss1 = new SpannableString(personViewHolder.mInvestedAmountTV.getText().toString().trim());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 16, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(personViewHolder.mInvestedAmountTV.getContext().getResources().getColor(R.color.light_gray)), 17, ss1.length(), 0);// set color
            personViewHolder.mInvestedAmountTV.setText(ss1);
            personViewHolder.mParticipantTV.setText(String.format(Locale.getDefault(), "%d Players", details.getNParticipants()));
            setUnderLineOnParticipant(personViewHolder);
        } else if (details.isPlayed()) {
            personViewHolder.mChangeTV.setVisibility(View.GONE);
            personViewHolder.mInvestTV.setVisibility(View.VISIBLE);
            personViewHolder.mInvestmentRL.setVisibility(View.VISIBLE);
            personViewHolder.mInvestedAmountTV.setText(String.format(Locale.getDefault(), "Invested Amount ₹%s", new DecimalFormat("##.##").format(details.getPlayed().getAmount())));
            SpannableString ss1 = new SpannableString(personViewHolder.mInvestedAmountTV.getText().toString().trim());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 16, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(personViewHolder.mInvestedAmountTV.getContext().getResources().getColor(R.color.light_gray)), 17, ss1.length(), 0);// set color
            personViewHolder.mInvestedAmountTV.setText(ss1);
            personViewHolder.mEstimatedProfitTV.setText(String.format(Locale.getDefault(), "Estimated Earnings ₹%s", new DecimalFormat("##.##").format(AppUtilityMethods.getGroupEstimatedWinning(mBattleInvestment, details.getInvestedAmount(), mGroupJoined, details.getPlayed().getAmount()))));
            SpannableString ss2 = new SpannableString(personViewHolder.mEstimatedProfitTV.getText().toString().trim());
            ss2.setSpan(new StyleSpan(Typeface.BOLD), 15, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(personViewHolder.mEstimatedProfitTV.getContext().getResources().getColor(R.color.light_gray)), 19, ss2.length(), 0);// set color
            personViewHolder.mEstimatedProfitTV.setText(ss2);
            personViewHolder.mParticipantTV.setVisibility(View.VISIBLE);
            personViewHolder.mSelectedbyTv.setVisibility(View.VISIBLE);
            personViewHolder.mParticipantTV.setText(String.format(Locale.getDefault(), "%d Players", details.getNParticipants()));
            setUnderLineOnParticipant(personViewHolder);
        } else if (details.getNParticipants() > 0) {
            personViewHolder.mChangeTV.setVisibility(View.GONE);
            personViewHolder.mInvestTV.setVisibility(View.VISIBLE);
            personViewHolder.mInvestmentRL.setVisibility(View.GONE);
            personViewHolder.mParticipantTV.setVisibility(View.VISIBLE);
            personViewHolder.mSelectedbyTv.setVisibility(View.VISIBLE);
            personViewHolder.mParticipantTV.setText(String.format(Locale.getDefault(), "%d Players", details.getNParticipants()));
            setUnderLineOnParticipant(personViewHolder);
        } else {
            personViewHolder.mChangeTV.setVisibility(View.GONE);
            personViewHolder.mInvestTV.setVisibility(View.VISIBLE);
            personViewHolder.mInvestmentRL.setVisibility(View.GONE);
            personViewHolder.mParticipantTV.setVisibility(View.GONE);
            personViewHolder.mSelectedbyTv.setVisibility(View.GONE);
        }
    }

    private void setUnderLineOnParticipant(PersonViewHolder personViewHolder) {
        SpannableString spannableStringObject = new SpannableString(personViewHolder.mParticipantTV.getText().toString().trim());
        spannableStringObject.setSpan(new UnderlineSpan(), 0, spannableStringObject.length(), 0);
        personViewHolder.mParticipantTV.setText(spannableStringObject);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fl_group)
        FrameLayout mGroupFL;
        @BindView(R.id.ll_item_group_1)
        LinearLayout mGroupOneLL;
        @BindView(R.id.ll_item_group_2)
        LinearLayout mGroupTwoLL;
        @BindView(R.id.ll_item_group_3)
        LinearLayout mGroupThreeLL;
        @BindView(R.id.ll_item_group_4)
        LinearLayout mGroupFourLL;
        @BindView(R.id.rl_investment)
        RelativeLayout mInvestmentRL;
        @BindView(R.id.tv_invested_amount)
        TextView mInvestedAmountTV;
        @BindView(R.id.tv_estimated_profit)
        TextView mEstimatedProfitTV;
        @BindView(R.id.tv_total_participant)
        TextView mParticipantTV;
        @BindView(R.id.tv_total_selected)
        TextView mSelectedbyTv;
        LinearLayout[] mPlayerLayouts;
        @BindView(R.id.tv_cancel)
        TextView mChangeTV;
        @BindView(R.id.tv_invest)
        TextView mInvestTV;

        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            mPlayerLayouts = new LinearLayout[]{mGroupOneLL, mGroupTwoLL, mGroupThreeLL, mGroupFourLL};
            mInvestedAmountTV.setOnClickListener(this);
            mEstimatedProfitTV.setOnClickListener(this);
            mChangeTV.setOnClickListener(this);
            mParticipantTV.setOnClickListener(this);
            mInvestTV.setOnClickListener(this);
            setPlayerOnClickListener();
        }

        void setPlayerOnClickListener() {
            for (LinearLayout playerView : mPlayerLayouts) {
                playerView.setOnClickListener(this);
            }
        }

        void setPlayerViewTag(int position) {
            mPlayerLayouts[position].setTag(R.id.tag_position, position);
        }

        void setPlayerVisibility(int position, int visibility) {
            mPlayerLayouts[position].setVisibility(visibility);
        }

        void setPlayerName(Player player, int position) {
            ((TextView) mPlayerLayouts[position].findViewById(R.id.tv_player)).setText(player.getTitle());
        }

        void setPlayerImage(Player player, int position) {
            String playerImage = player.getImg();
            ImageView playerIV = mPlayerLayouts[position].findViewById(R.id.iv_image);
            if (!TextUtils.isEmpty(playerImage) && playerImage.startsWith("https")) {
                Glide.with(playerIV.getContext()).load(playerImage).placeholder(R.drawable.profile).into(playerIV);
            } else {
                Glide.with(playerIV.getContext()).clear(playerIV);
                playerIV.setImageResource(R.mipmap.ic_launcher);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            switch (v.getId()) {
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
                case R.id.tv_cancel:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onCancelClicked(position);
                    }
                    break;
                case R.id.tv_total_participant:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onParticipantClicked(position);
                    }
                    break;
                case R.id.tv_invest:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onInvestClicked(position);
                    }
                    break;
                case R.id.ll_item_group_1:
                case R.id.ll_item_group_2:
                case R.id.ll_item_group_3:
                case R.id.ll_item_group_4:
                    int playerPosition = (int) v.getTag(R.id.tag_position);
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onPlayerProfileClicked(mGroupList.get(position).getPlayers().get(playerPosition).getpId());
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onInvestedAmountClicked(int position);

        void onEstimatedProfitClicked(int position);

        void onCancelClicked(int position);

        void onParticipantClicked(int position);

        void onInvestClicked(int position);

        void onPlayerProfileClicked(String playerId);
    }

}