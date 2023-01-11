package com.khiladiadda.main.game.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.TopUsersDetails;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopKhiladiAdapter extends RecyclerView.Adapter<TopKhiladiAdapter.EventHolder> {

    private final Context mContext;
    private final List<TopUsersDetails> mList;

    public TopKhiladiAdapter(Context context, List<TopUsersDetails> exhibitorList) {
        this.mContext = context;
        this.mList = exhibitorList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_khiladi_item_new, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        TopUsersDetails details = mList.get(position);
        String imageURL = details.getDp();
        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(mContext)
                    .load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mImageIV);
        } else {
            holder.mImageIV.setImageResource(R.drawable.splash_logo);
        }
        holder.mNameTV.setText(details.getName());
        holder.mPrizeTV.setText("Won \n Rs." + (details.getWinningAmount()));
        if(details.getLeaderboardType() == 1){
            holder.mModeTV.setText("By Playing Ludo");
        } else  if(details.getLeaderboardType() == 2){
            holder.mModeTV.setText("By Playing Fan Battle");
        } else  if(details.getLeaderboardType() == 3){
            holder.mModeTV.setText("By Playing League");
        } else  if(details.getLeaderboardType() == 4){
            holder.mModeTV.setText("By Playing Quiz");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_image)
        ImageView mImageIV;
        @BindView(R.id.tv_name)
        TextView mNameTV;
        @BindView(R.id.tv_total_won)
        TextView mPrizeTV;
        @BindView(R.id.tv_mode)
        TextView mModeTV;

        public EventHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void onClick(View v) {
        }
    }

}