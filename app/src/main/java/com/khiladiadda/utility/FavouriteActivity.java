package com.khiladiadda.utility;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class FavouriteActivity extends BaseActivity {

    @BindView(R.id.iv_freefire)
    ImageView mFreeFireIV;
    @BindView(R.id.iv_bgmi)
    ImageView mBgmiIV;
    @BindView(R.id.iv_tdm)
    ImageView mTdmIV;
    @BindView(R.id.iv_hth)
    ImageView mHthIV;
    @BindView(R.id.iv_ludo)
    ImageView mLudoIV;
    @BindView(R.id.iv_fanbattle)
    ImageView mFBIV;
    @BindView(R.id.iv_quiz)
    ImageView mQuizIV;
    @BindView(R.id.iv_clsq)
    ImageView mFFCSIV;
    @BindView(R.id.iv_ff_max)
    ImageView mFFMaxIV;
    @BindView(R.id.iv_ludoadda)
    ImageView mLudoAddaIV;
    @BindView(R.id.iv_pubg_gobal)
    ImageView mPGIV;
    @BindView(R.id.iv_premium_esports)
    ImageView mEPIV;
    @BindView(R.id.iv_close)
    ImageView mCloseIV;
    @BindView(R.id.btn_confirm)
    Button mConfirmBTN;

    private boolean mFF, mFFMax, mFFCS, mBGMI, mTDM, mHTH, mFB, mLA, mLK, mQuiz, mPG, mPE;

    @Override
    protected int getContentView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setFinishOnTouchOutside(false);
        return R.layout.activity_favourite;
    }

    @Override
    protected void initViews() {
        mFreeFireIV.setOnClickListener(this);
        mFFCSIV.setOnClickListener(this);
        mFFMaxIV.setOnClickListener(this);
        mTdmIV.setOnClickListener(this);
        mBgmiIV.setOnClickListener(this);
        mPGIV.setOnClickListener(this);
        mEPIV.setOnClickListener(this);
        mLudoIV.setOnClickListener(this);
        mLudoAddaIV.setOnClickListener(this);
        mFBIV.setOnClickListener(this);
        mHthIV.setOnClickListener(this);
        mQuizIV.setOnClickListener(this);
        mCloseIV.setOnClickListener(this);
        mConfirmBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_confirm:
                boolean league = false, sports = false, cricket = false, quiz = false;
                if (mFF || mFFCS || mFFMax || mBGMI || mTDM || mPE || mPG) {
                    league = true;
                }
                if (mLA || mLK) {
                    sports = true;
                }
                if (mFB || mHTH) {
                    cricket = true;
                }
                if (mQuiz) {
                    quiz = true;
                }
                if(league || sports || cricket || quiz){
                    Intent intent = new Intent();
                    intent.putExtra("league", league);
                    intent.putExtra("quiz", quiz);
                    intent.putExtra("sports", sports);
                    intent.putExtra("cricket", cricket);
                    setResult(AppConstant.FROM_FAVOURITE, intent);
                    finish();
                } else{
                    Snackbar.make(mConfirmBTN, "Select your favourite section to get notification", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.iv_freefire:
                if (mFF) {
                    mFF = false;
                    mFreeFireIV.setSelected(false);
                } else {
                    mFF = true;
                    mFreeFireIV.setSelected(true);
                }
                break;
            case R.id.iv_bgmi:
                if (mBGMI) {
                    mBGMI = false;
                    mBgmiIV.setSelected(false);
                } else {
                    mBGMI = true;
                    mBgmiIV.setSelected(true);
                }
                break;
            case R.id.iv_tdm:
                if (mTDM) {
                    mTDM = false;
                    mTdmIV.setSelected(false);
                } else {
                    mTDM = true;
                    mTdmIV.setSelected(true);
                }
                break;
            case R.id.iv_clsq:
                if (mFFCS) {
                    mFFCS = false;
                    mFFCSIV.setSelected(false);
                } else {
                    mFFCS = true;
                    mFFCSIV.setSelected(true);
                }
                break;
            case R.id.iv_ff_max:
                if (mFFMax) {
                    mFFMax = false;
                    mFFMaxIV.setSelected(false);
                } else {
                    mFFMax = true;
                    mFFMaxIV.setSelected(true);
                }
                break;
            case R.id.iv_premium_esports:
                if (mPE) {
                    mPE = false;
                    mEPIV.setSelected(false);
                } else {
                    mPE = true;
                    mEPIV.setSelected(true);
                }
                break;
            case R.id.iv_pubg_gobal:
                if (mPG) {
                    mPG = false;
                    mPGIV.setSelected(false);
                } else {
                    mPG = true;
                    mPGIV.setSelected(true);
                }
                break;
            case R.id.iv_ludo:
                if (mLK) {
                    mLK = false;
                    mLudoIV.setSelected(false);
                } else {
                    mLK = true;
                    mLudoIV.setSelected(true);
                }
                break;
            case R.id.iv_ludoadda:
                if (mLA) {
                    mLA = false;
                    mLudoAddaIV.setSelected(false);
                } else {
                    mLA = true;
                    mLudoAddaIV.setSelected(true);
                }
                break;
            case R.id.iv_fanbattle:
                if (mFB) {
                    mFB = false;
                    mFBIV.setSelected(false);
                } else {
                    mFB = true;
                    mFBIV.setSelected(true);
                }
                break;
            case R.id.iv_hth:
                if (mHTH) {
                    mHTH = false;
                    mHthIV.setSelected(false);
                } else {
                    mHTH = true;
                    mHthIV.setSelected(true);
                }
                break;
            case R.id.iv_quiz:
                if (mQuiz) {
                    mQuiz = false;
                    mQuizIV.setSelected(false);
                } else {
                    mQuiz = true;
                    mQuizIV.setSelected(true);
                }
                break;
        }
    }

}