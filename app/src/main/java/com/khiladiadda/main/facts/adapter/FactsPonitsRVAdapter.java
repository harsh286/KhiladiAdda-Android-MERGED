package com.khiladiadda.main.facts.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.PointDetails;

import java.util.List;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsPonitsRVAdapter extends RecyclerView.Adapter<FactsPonitsRVAdapter.EventHolder> {

    private Context mContext;
    private List<PointDetails> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public FactsPonitsRVAdapter(Context context, List<PointDetails> exhibitorList) {
        this.mContext = context;
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_points, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        PointDetails exhibitorsResponse = mFDList.get(position);
         String imageURL = exhibitorsResponse.getImage();
        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(mContext)
                    .load(imageURL)
                    .thumbnail(Glide.with(mContext).load(imageURL))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontTransform()
                    .into(holder.mTrendingIV);
        } else {
            holder.mTrendingIV.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.app_logo));
        }
        holder.mDateTV.setText(exhibitorsResponse.getText());
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }


    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_trending)
        ImageView mTrendingIV;
        @BindView(R.id.tv_date)
        TextView mDateTV;

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