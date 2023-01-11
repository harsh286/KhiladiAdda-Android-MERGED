package com.khiladiadda.clashx2.main.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashx2.main.adapter.PlayerStatusAdapter;
import com.khiladiadda.utility.AppConstant;

import butterknife.BindView;

public class PlayerStatus extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.rv_player_points)
    RecyclerView mPointsRV;
    @Override
    protected int getContentView() {
        return R.layout.activity_player_status;
    }

    @Override
    protected void initViews() {
        PlayerStatusAdapter mPointsAdapter = new PlayerStatusAdapter(getIntent().getParcelableArrayListExtra(AppConstant.DATA));
        mPointsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPointsRV.setAdapter(mPointsAdapter);

    }

    @Override
    protected void initVariables() {
        mBackIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }
}