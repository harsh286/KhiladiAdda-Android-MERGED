package com.khiladiadda.clashroyale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.response.GameCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClashRoyaleFiltersAdapter extends RecyclerView.Adapter<ClashRoyaleFiltersAdapter.PersonViewHolder> {

    private List<GameCategory> mEventList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public ClashRoyaleFiltersAdapter(List<GameCategory> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private GameCategory getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override public int getItemCount() {
        return mEventList.size();
    }

    @Override public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_clashroyale_filter, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(PersonViewHolder holder, int position) {
        GameCategory details = getItemAt(position);
        holder.mFilterNameBTN.setText(details.getTitle());
        if (details.isSelected()) {
            holder.mFilterNameBTN.setSelected(true);
        } else {
            holder.mFilterNameBTN.setSelected(false);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.btn_filter) Button mFilterNameBTN;

        private IOnItemClickListener mOnItemClickListener;
        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(View itemView, IOnItemClickListener onItemClickListener, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnItemClickListener = onItemClickListener;
            mOnItemChildClickListener = onItemChildClickListener;
            itemView.setOnClickListener(this);
            mFilterNameBTN.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (v.getId() == R.id.btn_filter) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onFilterButtonClicked(getAdapterPosition());
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getAdapterPosition(), 0);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onFilterButtonClicked(int position);
    }

}