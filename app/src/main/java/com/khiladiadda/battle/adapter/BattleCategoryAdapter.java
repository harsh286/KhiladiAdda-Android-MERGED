package com.khiladiadda.battle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.CategoriesList;
import com.khiladiadda.interfaces.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BattleCategoryAdapter extends RecyclerView.Adapter<BattleCategoryAdapter.PersonViewHolder> {

    private List<CategoriesList> mEventList;
    private IOnItemClickListener mOnItemClickListener;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public BattleCategoryAdapter(List<CategoriesList> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemClickListener(IOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private CategoriesList getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_battle_category, viewGroup, false);
        return new PersonViewHolder(v, mOnItemClickListener, mOnItemChildClickListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        CategoriesList details = getItemAt(position);
        personViewHolder.mFilterNameBTN.setText(details.getTitle() + " (" + details.getmBattleSize() + ")");
        if (details.isSelected()) {
            personViewHolder.mFilterNameBTN.setSelected(true);
        } else {
            personViewHolder.mFilterNameBTN.setSelected(false);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.btn_category)
        Button mFilterNameBTN;
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

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_category) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onCategoryClicked(getBindingAdapterPosition());
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getBindingAdapterPosition(), 0);
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onCategoryClicked(int position);
    }

}