package com.khiladiadda.battle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.adapter.PlayerPointsAdapter;
import com.khiladiadda.utility.AppConstant;

import butterknife.BindView;

public class PlayerPointsActivity extends BaseActivity {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.rv_player_points) RecyclerView mPointsRV;

    @Override protected int getContentView() {
        return R.layout.activity_player_points;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_player_info);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setVisibility(View.GONE);
    }

    @Override protected void initVariables() {
        PlayerPointsAdapter mPointsAdapter = new PlayerPointsAdapter(getIntent().getParcelableArrayListExtra(AppConstant.DATA));
        mPointsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPointsRV.setAdapter(mPointsAdapter);
    }

    @Override public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

}