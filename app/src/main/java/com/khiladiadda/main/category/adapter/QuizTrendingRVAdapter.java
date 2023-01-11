package com.khiladiadda.main.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizTrendingRVAdapter extends RecyclerView.Adapter<QuizTrendingRVAdapter.EventHolder> {

    private Context mContext;
    private List<QuizListDetails> mList;
    private IOnItemClickListener mOnItemClickListener;

    public QuizTrendingRVAdapter(Context context, List<QuizListDetails> exhibitorList) {
        this.mContext = context;
        this.mList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_trending, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        QuizListDetails details = mList.get(position);
        String imageURL = details.getImage();
        if (imageURL != null) {
            Glide.with(mContext)
                    .load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mTrendingIV);
        } else {
            holder.mTrendingIV.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.app_logo));
        }
        holder.mNameTV.setText(details.getName());
        //holder.mPrizeTV.setText("Prize : " + String.valueOf(exhibitorsResponse.getPrizemoney()) + " Coins");
        //holder.mEntryTV.setText("Entry : " + String.valueOf(exhibitorsResponse.getEntryFees()) + " Coins");

        holder.mPrizeTV.setText("Prize : " + String.valueOf(details.getPrizemoney()) + " Coins");
        holder.mEntryTV.setText("Entry : " + String.valueOf(details.getEntryFees()) + " Coins");
        if(details.getIsPlayed().equalsIgnoreCase(AppConstant.TRUE)) {
            holder.mPlayedIV.setVisibility(View.VISIBLE);
        }else{
            holder.mPlayedIV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_trending)
        ImageView mTrendingIV;
        @BindView(R.id.iv_play)
        ImageView mPlayedIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_prize)
        TextView mPrizeTV;
        @BindView(R.id.tv_entry)
        TextView mEntryTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
            }
        }
    }

}