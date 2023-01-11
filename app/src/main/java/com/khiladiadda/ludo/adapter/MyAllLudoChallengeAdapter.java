package com.khiladiadda.ludo.adapter;

import android.content.Context;
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
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAllLudoChallengeAdapter extends RecyclerView.Adapter<MyAllLudoChallengeAdapter.LudoContestHolder> {

    private Context mContext;
    private List<LudoContest> mLudoChallengeList;

    public MyAllLudoChallengeAdapter(Context context, List<LudoContest> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    @NonNull
    @Override
    public LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge, parent, false);
        return new LudoContestHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LudoContestHolder holder, int position) {
        String userId = AppSharedPreference.getInstance().getString(AppConstant.USER_ID, "");
        LudoContest ludoContestBean = mLudoChallengeList.get(position);
        boolean mIsCaptain = userId.equals(ludoContestBean.getCaptainId());
        if (ludoContestBean.getMode() == 1) {
            holder.mModeTV.setText("CLASSIC");
        } else if (ludoContestBean.getMode() == 2) {
            holder.mModeTV.setText("POPULAR");
        }
        holder.mContestNameTV.setText(ludoContestBean.getContestCode());
        holder.mTitleTV.setText("Challenged played for");
        holder.mAmountTV.setText(ludoContestBean.getEntryFees() + " Coins");
        holder.mDateTV.setText(AppUtilityMethods.getConvertDateQuiz(ludoContestBean.getCreatedAt()));
        holder.mWinningAmountTV.setText("Winning: " + ludoContestBean.getWinningAmount() + " Coins");

        if (ludoContestBean.isCancelled()) {
            if (mIsCaptain && ludoContestBean.isAccepted()) {
                setBothData(ludoContestBean, holder, mIsCaptain);
            } else if (mIsCaptain && !ludoContestBean.isAccepted()) {
                setCaptainData(ludoContestBean, holder, mIsCaptain);
            } else {
                setBothData(ludoContestBean, holder, mIsCaptain);
            }
            holder.mAcceptTV.setText(R.string.txt_canceled);
            holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (ludoContestBean.isResultDeclared()) {
            if (mIsCaptain) {
                if (ludoContestBean.getCaptainResult().isIsWon()) {
                    holder.mAcceptTV.setText(R.string.text_you_won);
                } else {
                    holder.mAcceptTV.setText(R.string.text_you_lost);
                }
            } else {
                if (ludoContestBean.getOpponentResult().isIsWon()) {
                    holder.mAcceptTV.setText(R.string.text_you_won);
                } else {
                    holder.mAcceptTV.setText(R.string.text_you_lost);
                }
            }
            setBothData(ludoContestBean, holder, mIsCaptain);
            holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.blue_dark));
        } else if (ludoContestBean.getAdminStatus() == 2) {
            if (mIsCaptain) {
                holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                if (ludoContestBean.isCaptainDeclared() || (ludoContestBean.getCaptainError() != null && !TextUtils.isEmpty(ludoContestBean.getCaptainError().getImg()))) {
                    holder.mAcceptTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mAcceptTV.setText(R.string.text_ludo_upload_result);
                }
            } else {
                holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                if (ludoContestBean.isOpponentDeclared() || (ludoContestBean.getOpponentError() != null && !TextUtils.isEmpty(ludoContestBean.getOpponentError().getImg()))) {
                    holder.mAcceptTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mAcceptTV.setText(R.string.text_ludo_upload_result);
                }
            }
            setBothData(ludoContestBean, holder, mIsCaptain);
        } else if (ludoContestBean.getAdminStatus() == 1) {
            if (mIsCaptain) {
                holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                if (ludoContestBean.isCaptainDeclared()) {
                    holder.mAcceptTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mAcceptTV.setText(R.string.text_ludo_upload_result);
                }
            } else {
                holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                if (ludoContestBean.isOpponentDeclared()) {
                    holder.mAcceptTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mAcceptTV.setText(R.string.text_ludo_upload_result);
                }
            }
            setBothData(ludoContestBean, holder, mIsCaptain);
        } else if ((ludoContestBean.isCaptainDeclared() && !ludoContestBean.isOpponentDeclared()) || (ludoContestBean.isOpponentDeclared() && !ludoContestBean.isCaptainDeclared())) {
            if ((mIsCaptain && ludoContestBean.isCaptainDeclared()) || (userId.equalsIgnoreCase(ludoContestBean.getOpponentId()) && ludoContestBean.isOpponentDeclared())) {
                holder.mAcceptTV.setText(R.string.text_ludo_result_updated);
            } else {
                holder.mAcceptTV.setText(R.string.text_ludo_upload_result);
            }
            setBothData(ludoContestBean, holder, mIsCaptain);
            holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.blue_dark));
        } else if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
            holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.blue_dark));
            if (ludoContestBean.isPlayed()) {
                setBothData(ludoContestBean, holder, mIsCaptain);
                holder.mAcceptTV.setText(R.string.text_in_progress);
            } else if (ludoContestBean.isAccepted()) {
                setBothData(ludoContestBean, holder, mIsCaptain);
                holder.mAcceptTV.setText(R.string.text_update_room_code);
            } else {
                setCaptainData(ludoContestBean, holder, mIsCaptain);
                holder.mAcceptTV.setText(R.string.text_waiting);
            }
        } else if (userId.equalsIgnoreCase(ludoContestBean.getOpponentId())) {
            holder.mAcceptTV.setBackgroundColor(mContext.getResources().getColor(R.color.blue_dark));
            setBothData(ludoContestBean, holder, mIsCaptain);
            if (ludoContestBean.isPlayed() || !TextUtils.isEmpty(ludoContestBean.getRoomId())) {
                holder.mAcceptTV.setText(R.string.text_play_ludo_king);
            } else {
                holder.mAcceptTV.setText(R.string.text_room_code_pending);
            }
        }
    }

    private void setCaptainData(LudoContest ludoContestBean, LudoContestHolder holder, boolean show) {
        if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        Glide.with(mContext).clear(holder.mProfileAccepterIV);
        holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        if (show) {
            holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getName());
            holder.mPlayerNameTV.setText("");
        } else {
            holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getLudoName());
            holder.mPlayerNameTV.setText("");
        }
    }

    private void setBothData(LudoContest ludoContestBean, LudoContestHolder holder, boolean show) {
        if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileIV);
            holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        }
        if (!TextUtils.isEmpty(ludoContestBean.getOpponent().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        if (show) {
            holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getName());
            holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getLudoName());
        } else {
            holder.mCaptainNameTV.setText(ludoContestBean.getCaptain().getLudoName());
            holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getName());
        }
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    private void setOpponentData(LudoContest ludoContestBean, LudoContestHolder holder, boolean show) {
        if (ludoContestBean.getOpponent().getDp() != null && !TextUtils.isEmpty(ludoContestBean.getOpponent().getDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getDp()).placeholder(R.mipmap.ic_launcher).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        Glide.with(mContext).clear(holder.mProfileIV);
        holder.mProfileIV.setImageResource(R.mipmap.ic_launcher);
        if (show) {
            holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getLudoName());
        } else {
            holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getName());
        }
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_contest_code)
        TextView mContestNameTV;
        @BindView(R.id.tv_accept)
        TextView mAcceptTV;
        @BindView(R.id.iv_profile)
        ImageView mProfileIV;
        @BindView(R.id.tv_captain_name)
        TextView mCaptainNameTV;
        @BindView(R.id.iv_profile_player)
        ImageView mProfileAccepterIV;
        @BindView(R.id.tv_player_name)
        TextView mPlayerNameTV;
        @BindView(R.id.tv_date)
        TextView mDateTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;

        public LudoContestHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}