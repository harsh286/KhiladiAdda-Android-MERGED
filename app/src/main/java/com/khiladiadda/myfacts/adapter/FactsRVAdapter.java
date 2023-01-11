package com.khiladiadda.myfacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.FactsList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsRVAdapter extends RecyclerView.Adapter<FactsRVAdapter.EventHolder> {

    private List<FactsList> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public FactsRVAdapter(List<FactsList> exhibitorList) {
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_bookmarked, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override public void onBindViewHolder(EventHolder holder, int position) {
        FactsList details = mFDList.get(position);
        String imageURL = details.getImgurl();
        if (imageURL != null) {
            Glide.with(holder.mTrendingIV.getContext()).load(imageURL).thumbnail(Glide.with(holder.mTrendingIV.getContext()).load(imageURL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).dontTransform().into(holder.mTrendingIV);
        } else {
            holder.mTrendingIV.setBackground(AppCompatResources.getDrawable(holder.mTrendingIV.getContext(), R.drawable.app_logo));
        }
        holder.mHeadingTV.setText(details.getHeading());
        holder.msubHeadingTV.setText(details.getSubheading());
    }

    @Override public int getItemCount() {
        return mFDList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_trending) ImageView mTrendingIV;
        @BindView(R.id.tv_heading) TextView mHeadingTV;
        @BindView(R.id.tv_sub_heading) TextView msubHeadingTV;

        private IOnItemClickListener mOnItemClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
            }
        }
    }
}