package com.khiladiadda.ludoTournament.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LudoTmtLiveMatchAdapter extends RecyclerView.Adapter<LudoTmtLiveMatchAdapter.ViewHolder> {
    private Context mContext;
    private IOnClickListener mIOnClickListener;


    public LudoTmtLiveMatchAdapter(Context mContext, IOnClickListener mIOnClickListener) {
        this.mContext = mContext;
        this.mIOnClickListener = mIOnClickListener;
    }

    @NonNull
    @Override
    public LudoTmtLiveMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View quizHeaderData = layoutInflater.inflate(R.layout.items_of_alltournament_ludotmt, parent, false);
        return new LudoTmtLiveMatchAdapter.ViewHolder(quizHeaderData, mIOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LudoTmtLiveMatchAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.btn_join)
        AppCompatButton joinBtn;

        IOnClickListener iOnClickListener;

        public ViewHolder(View view, IOnClickListener mIOnClickListener) {
            super(view);
            ButterKnife.bind(this, itemView);
            iOnClickListener = mIOnClickListener;
            joinBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_join) {
                iOnClickListener.onItemClick(getAbsoluteAdapterPosition());
            }
        }
    }


}
