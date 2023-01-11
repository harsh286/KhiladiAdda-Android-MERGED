package com.khiladiadda.wordsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryResponse;
import com.khiladiadda.wordsearch.listener.IOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordSearchCategoriesAdapter extends RecyclerView.Adapter<WordSearchCategoriesAdapter.ViewHolder> {

    private List<WordSearchCategoryResponse> mWordSearchCategoryResponseList;
    private Context context;
    private IOnClickListener iOnClickListener;


    public WordSearchCategoriesAdapter(Context context, List<WordSearchCategoryResponse> mWordSearchCategoryResponseList, IOnClickListener iOnClickListener) {
        this.mWordSearchCategoryResponseList = mWordSearchCategoryResponseList;
        this.context = context;
        this.iOnClickListener = iOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_of_categories_wordsearch, parent, false);
        return new ViewHolder(view, iOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordSearchCategoryResponse mItem = mWordSearchCategoryResponseList.get(position);
        holder.mCategoriesName.setText(mItem.getName());
        Glide.with(context).load(mItem.getImage()).into(holder.mCategoriesIv);
    }

    @Override
    public int getItemCount() {
        return mWordSearchCategoryResponseList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_categories_name)
        TextView mCategoriesName;
        @BindView(R.id.iv_category)
        ImageView mCategoriesIv;

        private final IOnClickListener mIOnClickListener;


        public ViewHolder(View view, IOnClickListener mViewAllClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            mIOnClickListener = mViewAllClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (mIOnClickListener != null) {
                mIOnClickListener.onItemClick(position);

            }
        }
    }
}
