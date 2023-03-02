package com.khiladiadda.scratchcard;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ScratchCardResponse;
import com.khiladiadda.network.model.response.ScratchCardResponseDettails;
import com.khiladiadda.scratchcard.adapter.ScratchCardAdapter;
import com.khiladiadda.scratchcard.dialog.ScratchCardDialog;
import com.khiladiadda.scratchcard.interfaces.IScratchPresenter;
import com.khiladiadda.scratchcard.interfaces.IScratchView;
import com.khiladiadda.scratchcard.interfaces.ScratchCardListerner;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScratchCardActivity extends BaseActivity implements IScratchView, IOnItemClickListener, ScratchCardListerner {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.rv_scratch_card)
    RecyclerView mScratchCardRV;
    @BindView(R.id.tv_amount_earned)
    TextView mAmountEarnedTV;
    @BindView(R.id.rl_ludo)
    ImageView mLudoCardRL;
    @BindView(R.id.rl_fanbattle)
    ImageView mFanBattleCardRL;
    @BindView(R.id.rl_leagues)
    ImageView mLeaguesCardRL;
    @BindView(R.id.rl_quiz)
    ImageView mQuizCardRL;
    @BindView(R.id.rl_hth)
    ImageView mHTHRL;
    @BindView(R.id.rl_lu)
    ImageView mLURL;
    @BindView(R.id.rl_droid)
    ImageView mDroidRl;
    @BindView(R.id.rl_wordsearch)
    ImageView mWSRl;
    @BindView(R.id.txt_nodata)
    TextView mNoDataTV;
    @BindView(R.id.tv_categories)
    TextView mGamecountTV;
    //    @BindView(R.id.iv_pcesports)
//    ImageView mPcEsports;
    @BindView(R.id.iv_courtpiece)
    ImageView mCourtPiece;
    @BindView(R.id.iv_rummy)
    ImageView mRummyIv;
    @BindView(R.id.nudge)
    NudgeView mNV;

    private IScratchPresenter mPresenter;
    private ScratchCardAdapter mAdapter;
    private List<ScratchCardResponseDettails> mScratchList;
    private int mCardType;

    @Override
    protected int getContentView() {
        return R.layout.activity_scratch_card;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.scratch_cards);
    }

    @Override
    protected void initVariables() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mPresenter = new ScratchPresenter(this);
        mLeaguesCardRL.setOnClickListener(this);
        mLudoCardRL.setOnClickListener(this);
        mQuizCardRL.setOnClickListener(this);
        mFanBattleCardRL.setOnClickListener(this);
        mHTHRL.setOnClickListener(this);
        mLURL.setOnClickListener(this);
        mDroidRl.setOnClickListener(this);
        mWSRl.setOnClickListener(this);
        mCourtPiece.setOnClickListener(this);
        mRummyIv.setOnClickListener(this);
        mScratchList = new ArrayList<>();
        mAdapter = new ScratchCardAdapter(mScratchList);
        mScratchCardRV.setLayoutManager(new GridLayoutManager(this, 3));
        mScratchCardRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        getCategory(AppConstant.LUDO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.rl_ludo:
                getCategory(AppConstant.LUDO);
                break;
            case R.id.rl_fanbattle:
                getCategory(AppConstant.FAN_BATTLE);
                break;
            case R.id.rl_leagues:
                getCategory(AppConstant.LEAGUE);
                break;
            case R.id.rl_quiz:
                getCategory(AppConstant.QUIZONLY);
                break;
            case R.id.rl_hth:
                getCategory(AppConstant.HTHREFRSH);
                break;
            case R.id.rl_lu:
                getCategory(AppConstant.LUDO_UNIVERSE_ID);
                break;
            case R.id.rl_droid:
                getCategory(AppConstant.DROIDO);
                break;
            case R.id.rl_wordsearch:
                getCategory(AppConstant.WORD_SEARCH_TYPE);
                break;
            case R.id.iv_courtpiece:
                getCategory(AppConstant.COURTPIECE_TYPE);
                break;
            case R.id.iv_rummy:
                getCategory(AppConstant.RUMMY_TYPE);
                break;
        }
    }

    private void getCategory(String mFromCategory) {
        mFanBattleCardRL.setBackground(null);
        mQuizCardRL.setBackground(null);
        mLeaguesCardRL.setBackground(null);
        mLudoCardRL.setBackground(null);
        mHTHRL.setBackground(null);
        mLURL.setBackground(null);
        mWSRl.setBackground(null);
        mDroidRl.setBackground(null);
//        mPcEsports.setBackground(null);
        mCourtPiece.setBackground(null);
        mRummyIv.setBackground(null);
        if (mFromCategory.equalsIgnoreCase(AppConstant.LUDO)) {
            mLudoCardRL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 1;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.FAN_BATTLE)) {
            mFanBattleCardRL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 4;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.LEAGUE)) {
            mLeaguesCardRL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 3;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.QUIZONLY)) {
            mQuizCardRL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 2;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.HTHREFRSH)) {
            mHTHRL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 5;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.LUDO_UNIVERSE_ID)) {
            mLURL.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 6;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.DROIDO)) {
            mDroidRl.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 8;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.WORD_SEARCH_TYPE)) {
            mWSRl.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 7;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.PC_ESPORTS_TYPE)) {
//            mPcEsports.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 9;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.COURTPIECE_TYPE)) {
            mCourtPiece.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 10;
        } else if (mFromCategory.equalsIgnoreCase(AppConstant.RUMMY_TYPE)) {
            mRummyIv.setBackground(getResources().getDrawable(R.drawable.card_selected));
            mCardType = 11;
        }
        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getScratchCard(mCardType);
        } else {
            Snackbar.make(mBackIV, getString(R.string.error_internet), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScratchCardComplete(ScratchCardResponse responseModel) {
        hideProgress();
        mScratchList.clear();
        if (responseModel.isStatus()) {
            mScratchCardRV.setVisibility(View.VISIBLE);
            mNoDataTV.setVisibility(View.GONE);
            mAmountEarnedTV.setText(String.format("%.2f", responseModel.getTotalAmountEarned()));
            mScratchList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
            updateCategoryClick(responseModel);
            if (responseModel.getResponse().isEmpty()) {
                mScratchCardRV.setVisibility(View.GONE);
                mNoDataTV.setVisibility(View.VISIBLE);
            }
        } else {
            mScratchCardRV.setVisibility(View.GONE);
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    private void updateCategoryClick(ScratchCardResponse responseModel) {
        String msg = "Play " + responseModel.getnGameRemaining() + " more games of the same category to won a Scratch Card.";
        if (responseModel.getnGameRemaining() > 0) {
            switch (mCardType) {
                case 1:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_LUDO, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_LUDO, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                        //            mGamecountTV.setText(getString(R.string.categories) + " ("+responseModel.getnGameRemaining()+")"+" Left");
                    }
                    break;
                case 2:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_QUIZ, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_QUIZ, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_LEAGUE, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_LEAGUE, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_FB, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_FB, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 5:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_HTH, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_HTH, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 6:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_LA, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_LA, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 7:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_WS, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_WS, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 8:
                    if (!mAppPreference.getBoolean(AppConstant.IS_SCRATCH_DROID_DO, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_SCRATCH_DROID_DO, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 9:
                    if (!mAppPreference.getBoolean(AppConstant.IS_PCESPORT, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_PCESPORT, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 10:
                    if (!mAppPreference.getBoolean(AppConstant.IS_COURTPIECE, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_COURTPIECE, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                case 11:
                    if (!mAppPreference.getBoolean(AppConstant.IS_RUMMY, false)) {
                        mAppPreference.setBoolean(AppConstant.IS_RUMMY, true);
                        AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
                    } else {
//                        AppUtilityMethods.showMsg(this, msg, true);
                        Snackbar.make(mBackIV, msg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onScratchCardFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onScratchedCardComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            getData();

        }
    }

    @Override
    public void onScratchedCardFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        int mIsUsed = mScratchList.get(position).getIsUsed();
        if (mIsUsed == 0) {
            Bundle bundle = new Bundle();
            bundle.putDouble("amount", mScratchList.get(position).getAmount());
            bundle.putString("card_id", mScratchList.get(position).getId());
            ScratchCardDialog scratchDialog = new ScratchCardDialog(ScratchCardActivity.this, bundle, R.style.MyDialog);
            scratchDialog.show();
        }
    }

    @Override
    public void onscratchcard_id(String card_id) {
        if (card_id != null) {
            mPresenter.applyScratchCard(card_id);

        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

}