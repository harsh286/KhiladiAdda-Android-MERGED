package com.khiladiadda.help.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.HelpDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.PersonViewHolder> {

    private List<HelpDetails> mEventList;
    private IOnItemChildClickListener mOnItemChildClickListener;

    public HelpAdapter(List<HelpDetails> mEventList) {
        this.mEventList = mEventList;
    }

    public void setOnItemChildClickListener(IOnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    private HelpDetails getItemAt(int index) {
        return mEventList.get(index);
    }

    @Override public int getItemCount() {
        return mEventList.size();
    }

    @Override public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_help, viewGroup, false);
        return new PersonViewHolder(v, mOnItemChildClickListener);
    }

    @Override public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        HelpDetails details = getItemAt(position);
        personViewHolder.mQuestionTV.setText(details.getQuestion());
        personViewHolder.mAnswerTV.setText(details.getAnswer());
        if (!details.isExpand()) {
            personViewHolder.mAnswerTV.setVisibility(View.GONE);
        } else {
            personViewHolder.mAnswerTV.setVisibility(View.VISIBLE);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_question) TextView mQuestionTV;
        @BindView(R.id.tv_answer) TextView mAnswerTV;

        private IOnItemChildClickListener mOnItemChildClickListener;

        public PersonViewHolder(View itemView, IOnItemChildClickListener onItemChildClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mOnItemChildClickListener = onItemChildClickListener;
            mQuestionTV.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (v.getId() == R.id.tv_question) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onViewAnswerClicked(getAdapterPosition());
                }
            }
        }
    }

    public interface IOnItemChildClickListener {
        void onViewAnswerClicked(int position);
    }

}