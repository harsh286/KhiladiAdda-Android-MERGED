package com.khiladiadda.battle;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;

public class BattleHelpActivity extends BaseActivity {

    @BindView(R.id.iv_ludo_help)
    ImageView mLuodHelpIV;
    @BindView(R.id.rl_next)
    RelativeLayout mNextRL;
    @BindView(R.id.rl_previous)
    RelativeLayout mPreviousRL;
    @BindView(R.id.iv_next)
    ImageView mNextIV;
    @BindView(R.id.btn_next)
    TextView mNextBTN;
    @BindView(R.id.btn_view_video)
    TextView mVideoBTN;
    private int mNextCount;
    private int[] mFBHelpDrawables = {R.drawable.fb_help_one, R.drawable.fb_help_two, R.drawable.fb_help_three, R.drawable.fb_help_four, R.drawable.refer_help};
    private int[] mFBCategoryDrawables = {R.drawable.reverse_1, R.drawable.reverse_2, R.drawable.reverse_3, R.drawable.reverse_4, R.drawable.refer_help};
    private int[] mHTHDrawables = {R.drawable.hth_step1, R.drawable.hth_step2, R.drawable.hth_step3, R.drawable.hth_step4, R.drawable.hth_step5, R.drawable.hth_step6, R.drawable.refer_help};
    private int mHowToPLay;

    @Override
    protected int getContentView() {
        return R.layout.activity_battle_help;
    }

    @Override
    protected void initViews() {
        mNextRL.setOnClickListener(this);
        mPreviousRL.setOnClickListener(this);
        mVideoBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mHowToPLay = getIntent().getIntExtra(AppConstant.FROM, 0);
        if (mHowToPLay == 1) {
            mLuodHelpIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fb_help_one));
        } else if (mHowToPLay == 2) {
            mLuodHelpIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hth_step1));
        } else {
            mLuodHelpIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.reverse_1));
        }
        mPreviousRL.setVisibility(View.GONE);
    }

    private void updateOnNext(int[] helpResourceArray) {
        if (mHowToPLay == 1) {
            if (mNextCount < 3) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
            } else if (mNextCount == 3) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
                mNextBTN.setText(R.string.done);
                mNextIV.setImageResource(0);
            } else if (mNextCount == 4) {
                finish();
            }
        } else if (mHowToPLay == 2) {
            if (mNextCount < 5) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
            } else if (mNextCount == 5) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
                mNextBTN.setText(R.string.done);
                mNextIV.setImageResource(0);
            } else if (mNextCount == 6) {
                finish();
            }
        } else {
            if (mNextCount < 4) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
            } else if (mNextCount == 4) {
                mLuodHelpIV.setImageResource(helpResourceArray[mNextCount]);
                mNextBTN.setText(R.string.done);
                mNextIV.setImageResource(0);
            } else if (mNextCount == 5) {
                finish();
            }
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
            if (mHowToPLay == 1) {
                updateOnNext(mFBHelpDrawables);
            } else if (mHowToPLay == 2) {
                updateOnNext(mHTHDrawables);
            } else {
                updateOnNext(mFBCategoryDrawables);
            }
        } else if (v.getId() == R.id.rl_previous) {
            mNextCount = mNextCount - 1;
            if (mHowToPLay == 1) {
                updateOnPrevious(mFBHelpDrawables);
            } else if (mHowToPLay == 2) {
                updateOnPrevious(mHTHDrawables);
            } else {
                updateOnPrevious(mFBCategoryDrawables);
            }
        } else if (v.getId() == R.id.btn_view_video) {
            AppUtilityMethods.openLudoVideo(this);
//            if (mAppPreference.getBoolean(AppConstant.FB_VIDEO_SEEN, false)) {
//                AppUtilityMethods.openLudoVideo(this);
//            } else {
//                startActivity(new Intent(this, VideoActivity.class));
//            }
        }
    }

}