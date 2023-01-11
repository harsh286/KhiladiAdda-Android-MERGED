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
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAllNewChallengeAdapter extends RecyclerView.Adapter<MyAllNewChallengeAdapter.LudoContestHolder> {

    private Context mContext;
    private List<LudoContest> mLudoChallengeList;
    private IOnItemClickListener mOnItemClickListener;
    private MyAllNewChallengeAdapter.IOnItemChildClickListener mOnItemChildClickListener;

    public MyAllNewChallengeAdapter(Context context, List<LudoContest> ludoChallengeList) {
        this.mContext = context;
        this.mLudoChallengeList = ludoChallengeList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(MyAllNewChallengeAdapter.IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @NonNull
    @Override
    public MyAllNewChallengeAdapter.LudoContestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_my_challenge, parent, false);
        return new MyAllNewChallengeAdapter.LudoContestHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(MyAllNewChallengeAdapter.LudoContestHolder holder, int position) {
        LudoContest ludoContestBean = mLudoChallengeList.get(position);
        String userId = AppSharedPreference.getInstance().getString(AppConstant.USER_ID, "");
        holder.mWinningAmountTV.setText(String.format("Winning: %s Coins", ludoContestBean.getWinningAmount()));
        holder.mContestNameTV.setText(ludoContestBean.getContestCode());
        if (ludoContestBean.getMode() == 1) {
            holder.mModeTV.setText("CLASSIC");
        } else if (ludoContestBean.getMode() == 2) {
            holder.mModeTV.setText("POPULAR");
        }

        if (ludoContestBean.isCancelled()) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                if (ludoContestBean.isAccepted()) {
                    setOpponentData(ludoContestBean, holder);
                } else {
                    holder.mTitleTV.setText(String.format("You have challenged for\n%s Coins", ludoContestBean.getEntryFees()));
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                holder.mTitleTV.setText(String.format("You accepted challenge for \n%s Coins", ludoContestBean.getEntryFees()));
            }
            holder.mCancelTV.setVisibility(View.VISIBLE);
            holder.mCancelTV.setText(R.string.txt_canceled);
            holder.mCancelTV.setEnabled(false);
            holder.mPlayTV.setVisibility(View.GONE);
            holder.mReviewTV.setVisibility(View.GONE);
        } else if (ludoContestBean.isResultDeclared()) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                setOpponentData(ludoContestBean, holder);
                if (ludoContestBean.getCaptainResult().isIsWon()) {
                    holder.mPlayTV.setText(R.string.text_you_won);
                } else {
                    holder.mPlayTV.setText(R.string.text_you_lost);
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                if (ludoContestBean.getOpponentResult().isIsWon()) {
                    holder.mPlayTV.setText(R.string.text_you_won);
                } else {
                    holder.mPlayTV.setText(R.string.text_you_lost);
                }
            }
            holder.mPlayTV.setVisibility(View.VISIBLE);
            holder.mPlayTV.setEnabled(false);
            holder.mCancelTV.setVisibility(View.GONE);
            holder.mReviewTV.setVisibility(View.GONE);
        } else if (ludoContestBean.getAdminStatus() == 2) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                setOpponentData(ludoContestBean, holder);
                if (ludoContestBean.isCaptainDeclared() || (ludoContestBean.getCaptainError() != null && !TextUtils.isEmpty(ludoContestBean.getCaptainError().getImg()))) {
                    holder.mReviewTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mReviewTV.setText(R.string.text_ludo_upload_result);
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                if (ludoContestBean.isOpponentDeclared() || (ludoContestBean.getOpponentError() != null && !TextUtils.isEmpty(ludoContestBean.getOpponentError().getImg()))) {
                    holder.mReviewTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mReviewTV.setText(R.string.text_ludo_upload_result);
                }
            }
            holder.mCancelTV.setVisibility(View.GONE);
            holder.mPlayTV.setVisibility(View.GONE);
            holder.mReviewTV.setVisibility(View.VISIBLE);
        } else if (ludoContestBean.getAdminStatus() == 1) {
            if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
                setOpponentData(ludoContestBean, holder);
                if (ludoContestBean.isCaptainDeclared()) {
                    holder.mReviewTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mReviewTV.setText(R.string.text_ludo_upload_result);
                }
            } else {
                setCaptainData(ludoContestBean, holder);
                if (ludoContestBean.isOpponentDeclared()) {
                    holder.mReviewTV.setText(R.string.text_ludo_result_declared_error);
                } else {
                    holder.mReviewTV.setText(R.string.text_ludo_upload_result);
                }
            }
            holder.mCancelTV.setVisibility(View.GONE);
            holder.mPlayTV.setVisibility(View.GONE);
            holder.mReviewTV.setVisibility(View.VISIBLE);
        } else if ((ludoContestBean.isCaptainDeclared() && !ludoContestBean.isOpponentDeclared()) || (ludoContestBean.isOpponentDeclared() && !ludoContestBean.isCaptainDeclared())) {
            if ((userId.equalsIgnoreCase(ludoContestBean.getCaptainId()) && ludoContestBean.isCaptainDeclared()) || (userId.equalsIgnoreCase(ludoContestBean.getOpponentId()) && ludoContestBean.isOpponentDeclared())) {
                setOpponentData(ludoContestBean, holder);
                holder.mReviewTV.setText(R.string.text_ludo_result_updated);
            } else {
                setCaptainData(ludoContestBean, holder);
                holder.mReviewTV.setText(R.string.text_ludo_upload_result);
            }
            holder.mCancelTV.setVisibility(View.GONE);
            holder.mPlayTV.setVisibility(View.GONE);
            holder.mReviewTV.setVisibility(View.VISIBLE);
        } else if (userId.equalsIgnoreCase(ludoContestBean.getCaptainId())) {
            if (ludoContestBean.isPlayed()) {
                setOpponentData(ludoContestBean, holder);
                holder.mPlayTV.setText(R.string.text_play_ludo_king);
                holder.mPlayTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setVisibility(View.GONE);
                holder.mReviewTV.setVisibility(View.GONE);
            } else if (ludoContestBean.isAccepted()) {
                setOpponentData(ludoContestBean, holder);
                holder.mCancelTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setText(R.string.text_cancel);
                holder.mPlayTV.setVisibility(View.VISIBLE);
                holder.mPlayTV.setText(R.string.text_update_room_code);
                holder.mReviewTV.setVisibility(View.GONE);
            } else {
                holder.mTitleTV.setText(String.format("You have challenged for\n%s Coins", ludoContestBean.getEntryFees()));
                holder.mPlayerNameTV.setText(R.string.text_waiting_dot);
                holder.mCancelTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setText(R.string.text_cancel);
                holder.mPlayTV.setVisibility(View.GONE);
                holder.mReviewTV.setVisibility(View.GONE);
            }
        } else if (userId.equalsIgnoreCase(ludoContestBean.getOpponentId())) {
            setCaptainData(ludoContestBean, holder);
            if (ludoContestBean.isPlayed() || !TextUtils.isEmpty(ludoContestBean.getRoomId())) {
                holder.mPlayTV.setText(R.string.text_play_ludo_king);
                holder.mPlayTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setVisibility(View.GONE);
                holder.mReviewTV.setVisibility(View.GONE);
            } else {
                holder.mPlayTV.setText(R.string.text_room_code_pending);
                holder.mPlayTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setVisibility(View.VISIBLE);
                holder.mCancelTV.setText(R.string.text_cancel);
                holder.mReviewTV.setVisibility(View.GONE);
            }
        }
    }

    private void setCaptainData(LudoContest ludoContestBean, MyAllNewChallengeAdapter.LudoContestHolder holder) {
        holder.mTitleTV.setText("You accepted challenge for \n" + ludoContestBean.getEntryFees() + " Coins");
        if (!TextUtils.isEmpty(ludoContestBean.getCaptain().getLudoDp())) {
            Glide.with(mContext).load(ludoContestBean.getCaptain().getLudoDp()).placeholder(R.drawable.profile).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mPlayerNameTV.setText(ludoContestBean.getCaptain().getLudoName());
    }

    private void setOpponentData(LudoContest ludoContestBean, MyAllNewChallengeAdapter.LudoContestHolder holder) {
        holder.mTitleTV.setText("Your challenge has been accepted\n" + ludoContestBean.getEntryFees() + " Coins");
        if (ludoContestBean.getOpponent() != null && ludoContestBean.getOpponent().getLudoDp() != null && !TextUtils.isEmpty(ludoContestBean.getOpponent().getLudoDp())) {
            Glide.with(mContext).load(ludoContestBean.getOpponent().getLudoDp()).placeholder(R.drawable.profile).into(holder.mProfileAccepterIV);
        } else {
            Glide.with(mContext).clear(holder.mProfileAccepterIV);
            holder.mProfileAccepterIV.setImageResource(R.mipmap.ic_launcher);
        }
        holder.mPlayerNameTV.setText(ludoContestBean.getOpponent().getLudoName());
    }

    @Override
    public int getItemCount() {
        return mLudoChallengeList.size();
    }

    public static class LudoContestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView mTitleTV;
        @BindView(R.id.tv_wining_amount)
        TextView mWinningAmountTV;
        @BindView(R.id.tv_contest_code)
        TextView mContestNameTV;
        @BindView(R.id.tv_play)
        TextView mPlayTV;
        @BindView(R.id.tv_cancel)
        TextView mCancelTV;
        @BindView(R.id.tv_review)
        TextView mReviewTV;
        @BindView(R.id.iv_profile_player)
        ImageView mProfileAccepterIV;
        @BindView(R.id.tv_player_name)
        TextView mPlayerNameTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;

        private IOnItemClickListener mOnItemClickListener;
        private MyAllNewChallengeAdapter.IOnItemChildClickListener mOnItemChildClickListener;

        public LudoContestHolder(View view, IOnItemClickListener onItemClickListener, MyAllNewChallengeAdapter.IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mPlayTV.setOnClickListener(this);
            mCancelTV.setOnClickListener(this);
            mReviewTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_play:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onPlayClicked(getBindingAdapterPosition());
                    }
                    break;
                case R.id.tv_cancel:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onCancelClick(getBindingAdapterPosition());
                    }
                    break;
                case R.id.tv_review:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onReviewClick(getBindingAdapterPosition());
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onPlayClicked(int position);

        void onCancelClick(int position);

        void onReviewClick(int position);
    }

}