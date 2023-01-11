package com.khiladiadda.quiz.result;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.league.adapter.LeaguePrizePoolAdapter;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.quiz.result.adapter.PrizeBreakthroughRVAdapter;
import com.khiladiadda.utility.AppConstant;

import butterknife.BindView;

public class PrizeBreakthroughActivity extends BaseActivity {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_quiz_name) TextView mQuizNameTV;
    @BindView(R.id.rv_prize_pool_breakup) RecyclerView mPrizePoolBreakupRV;

    @Override protected int getContentView() {
        return R.layout.activity_prize_breakthrough;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_prize_pool_breakup);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        String from = getIntent().getStringExtra(AppConstant.FROM);
        if (from.equalsIgnoreCase(AppConstant.FROM_VIEW_QUIZ)) {
            QuizListDetails mQuizDetails = getIntent().getParcelableExtra(AppConstant.DATA_QUIZ);
            mQuizNameTV.setText(mQuizDetails.getName());
            if (mQuizDetails.getPrizePoolBreakthrough().size() > 0) {
                PrizeBreakthroughRVAdapter mAdapter = new PrizeBreakthroughRVAdapter(mQuizDetails.getPrizePoolBreakthrough());
                mPrizePoolBreakupRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mPrizePoolBreakupRV.setAdapter(mAdapter);
            }
        } else {
            LeagueListDetails mLeagueDetails = getIntent().getParcelableExtra(AppConstant.DATA);
            mQuizNameTV.setText(mLeagueDetails.getTitle());
            if (mLeagueDetails.getPrizePoolBreakup().size() > 0) {
                LeaguePrizePoolAdapter mAdapter = new LeaguePrizePoolAdapter(this, mLeagueDetails.getPrizePoolBreakup());
                mPrizePoolBreakupRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mPrizePoolBreakupRV.setAdapter(mAdapter);
            }
        }
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
        }
    }
}
