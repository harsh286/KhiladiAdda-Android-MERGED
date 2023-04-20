package com.khiladiadda.ludo.luodhelp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.ludo.LudoChallengeActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;

public class LudoHelpActivity extends BaseActivity {

    @BindView(R.id.iv_ludo_help)
    ImageView mLuodHelpIV;
    @BindView(R.id.rl_next)
    RelativeLayout mNextRL;
    @BindView(R.id.rl_previous)
    RelativeLayout mPreviousRL;
    @BindView(R.id.iv_next)
    ImageView mNextIV;
    @BindView(R.id.iv_previous)
    ImageView mPreviousIV;
    @BindView(R.id.btn_next)
    TextView mNextBTN;
    @BindView(R.id.btn_previous)
    TextView mPreviousBTN;
    private int mNextCount;
    private int[] mLudoHelpDrawables = {R.drawable.ludo_help_one, R.drawable.ludo_help_two, R.drawable.ludo_help_three, R.drawable.ludo_help_four, R.drawable.ludo_help_five, R.drawable.ludo_help_six, R.drawable.refer_help};

    @Override
    protected int getContentView() {
        return R.layout.activity_challenge_help;
    }

    @Override
    protected void initViews() {
        mNextRL.setOnClickListener(this);
        mPreviousRL.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mLuodHelpIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ludo_help_one));
        AppUtilityMethods.deleteCache(this);
        mPreviousRL.setVisibility(View.GONE);
    }

    private void updateOnNext(int[] helpResourceArray, String preferenceUpdateGameKey, int contestType) {
        if (mNextCount < 6) {
            mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
        } else if (mNextCount == 6) {
            mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
            mNextBTN.setText(R.string.done);
            mNextIV.setImageResource(0);
        } else if (mNextCount == 7) {
            mAppPreference.setBoolean(preferenceUpdateGameKey, true);
            Intent i = new Intent(this, LudoChallengeActivity.class);
            i.putExtra(AppConstant.CONTEST_TYPE, contestType);
            startActivity(i);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_next) {
            mNextCount = mNextCount + 1;
            mPreviousRL.setVisibility(View.VISIBLE);
            updateOnNext(mLudoHelpDrawables, AppConstant.LUDO_VIDEO_SEEN, AppConstant.TYPE_LUDO);
        } else if (v.getId() == R.id.rl_previous) {
            mNextCount = mNextCount - 1;
            updateOnPrevious(mLudoHelpDrawables);
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        super.onDestroy();
    }

}