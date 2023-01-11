package com.khiladiadda.main.facts.adapter;

import android.content.Context;
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

public class FactsTrendingRVAdapter extends RecyclerView.Adapter<FactsTrendingRVAdapter.EventHolder> {

    private Context mContext;
    private List<FactsList> mFDList;
    private IOnItemClickListener mOnItemClickListener;
    private FactsTrendingRVAdapter.IOnItemChildClickListener mOnItemChildClickListener;

    public FactsTrendingRVAdapter(Context context, List<FactsList> exhibitorList) {
        this.mContext = context;
        this.mFDList = exhibitorList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(FactsTrendingRVAdapter.IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_trending, parent, false);
        return new EventHolder(itemView, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        FactsList details = mFDList.get(position);
         String imageURL = details.getImgurl();
        if (imageURL != null) {
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
        holder.mDateTV.setText(details.getHeading());
        holder.mAmountTV.setText(details.getSubheading());

        if(details.isLiked()) {
            holder.mWishlistIV.setImageResource(R.drawable.ic_favorite_red);
        } else {
            holder.mWishlistIV.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        if(details.isBookmarked()) {
            holder.mBookmarkIV.setImageResource(R.drawable.ic_bookmark_green);
        } else{
           holder.mBookmarkIV.setImageResource(R.drawable.ic_bookmark_black);
        }
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
        @BindView(R.id.tv_amount)
        TextView mAmountTV;
        @BindView(R.id.iv_wishlist)
        ImageView mWishlistIV;
        @BindView(R.id.iv_bookmark)
        ImageView mBookmarkIV;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public EventHolder(View view, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            mWishlistIV.setOnClickListener(this);
            mBookmarkIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_wishlist:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onWishlistClicked(getAdapterPosition());
                    }
                    break;
                case R.id.iv_bookmark:
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onBookmarkClicked(getAdapterPosition());
                    }
                    break;
                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
                    }
                    break;
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onWishlistClicked(int position);

        void onBookmarkClicked(int position);
    }
}