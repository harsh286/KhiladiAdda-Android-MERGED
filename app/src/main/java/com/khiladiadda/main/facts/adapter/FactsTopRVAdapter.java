package com.khiladiadda.main.facts.adapter;

import android.content.Context;
import android.text.TextUtils;
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

public class FactsTopRVAdapter extends RecyclerView.Adapter<FactsTopRVAdapter.EventHolder> {

    private Context mContext;
    private List<FactsList> mFDList;
    private IOnItemClickListener mOnItemClickListener;

    public FactsTopRVAdapter(Context context, List<FactsList> exhibitorList) {
        this.mContext = context;
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_top, parent, false);
        return new EventHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        FactsList exhibitorsResponse = mFDList.get(position);
        String imageURL = exhibitorsResponse.getImgurl();
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
        holder.mDateTV.setText(exhibitorsResponse.getHeading());
        holder.mAmountTV.setText(exhibitorsResponse.getSubheading());
    }

    @Override
    public int getItemCount() {
        return mFDList.size();
    }


    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_top)
        ImageView mTrendingIV;
        @BindView(R.id.tv_date)
        TextView mDateTV;
        @BindView(R.id.tv_amount)
        TextView mAmountTV;

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