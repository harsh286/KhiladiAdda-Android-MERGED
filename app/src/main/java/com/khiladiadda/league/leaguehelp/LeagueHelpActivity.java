package com.khiladiadda.league.leaguehelp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.league.details.LeagueDetailsActivity;
import com.khiladiadda.network.model.response.LeagueListDetails;
import com.khiladiadda.utility.AppConstant;

import butterknife.BindView;

public class LeagueHelpActivity extends BaseActivity {

    @BindView(R.id.iv_ludo_help) ImageView mLuodHelpIV;
    @BindView(R.id.rl_next) RelativeLayout mNextRL;
    @BindView(R.id.rl_previous) RelativeLayout mPreviousRL;
    @BindView(R.id.iv_next) ImageView mNextIV;
    @BindView(R.id.btn_next) TextView mNextBTN;
    private int mNextCount;
    private int[] mDrawables = {R.drawable.l_one, R.drawable.l_two, R.drawable.l_three};
    private LeagueListDetails mLeagueDetails;
    private String mGameType, mGame;

    @Override protected int getContentView() {
        return R.layout.activity_challenge_help;
    }

    @Override protected void initViews() {
        mNextRL.setOnClickListener(this);
        mPreviousRL.setOnClickListener(this);
        mLeagueDetails = getIntent().getParcelableExtra(AppConstant.DATA);
        mGameType = getIntent().getStringExtra("type");
        mGame = getIntent().getStringExtra("game");
    }

    @Override protected void initVariables() {
        mLuodHelpIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.l_one));
        mPreviousRL.setVisibility(View.GONE);
    }

    private void updateOnNext(int[] helpResourceArray, String preferenceUpdateGameKey) {
        if (mNextCount < 2) {
            mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
        } else if (mNextCount == 2) {
            mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
            mNextBTN.setText(R.string.done);
            mNextIV.setImageResource(0);
        } else if (mNextCount == 3) {
            mAppPreference.setBoolean(preferenceUpdateGameKey, true);
            Intent details = new Intent(this, LeagueDetailsActivity.class);
            details.putExtra(AppConstant.FROM, AppConstant.LEAGUE);
            details.putExtra(AppConstant.DATA, mLeagueDetails);
            details.putExtra("type", mGameType);
            details.putExtra("game", mGame);
            startActivity(details);
            finish();
        }
    }

    private void updateOnPrevious(int[] helpResourceArray) {
        if (mNextCount == 0) {
            mPreviousRL.setVisibility(View.GONE);
        }
        mNextBTN.setText(R.string.text_next);
        mNextIV.setImageResource(R.drawable.ic_arrow_forward_white);
        mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
    }

    @Override public void onClick(View v) {
        if (v.getId() == R.id.rl_next) {
            mNextCount = mNextCount + 1;
            mPreviousRL.setVisibility(View.VISIBLE);
            updateOnNext(mDrawables, AppConstant.LEAGUE_CREATE_JOIN_HELP);
        } else if (v.getId() == R.id.rl_previous) {
            mNextCount = mNextCount - 1;
            updateOnPrevious(mDrawables);
        }
    }
}