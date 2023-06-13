package com.khiladiadda.rummy;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.RummyHelpRequest;
import com.khiladiadda.network.model.response.HelpRulesPointData;
import com.khiladiadda.network.model.response.RummyHelpResponse;
import com.khiladiadda.rummy.adapter.RummyHelpAdapter;
import com.khiladiadda.rummy.interfaces.IRummyHelpRulePresenter;
import com.khiladiadda.rummy.interfaces.IRummyHelpRuleView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class RummyHelpActivity extends BaseActivity implements IRummyHelpRuleView {
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_points)
    TextView mPointsTV;
    @BindView(R.id.tv_pool)
    TextView mPoolTv;
    @BindView(R.id.tv_deal)
    TextView mDealTv;
    @BindView(R.id.tv_tips)
    TextView mTipsTv;
    @BindView(R.id.tv_offer)
    TextView mOfferTv;
    @BindView(R.id.ll_mode_option)
    LinearLayout mModeOptionLL;
    @BindView(R.id.tv_one)
    TextView mOneTv;
    @BindView(R.id.tv_two)
    TextView mTwoTv;
    @BindView(R.id.tv_three)
    TextView mThreeTv;
    @BindView(R.id.rv_help)
    RecyclerView mHelpRv;
    @BindView(R.id.iv_translator)
    ImageView mTranslatorIv;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTv;
    @BindView(R.id.tv_payment_history)
    TextView mPaymentHistoryTv;
    @BindView(R.id.tv_history)
    TextView mHistoryTv;
    /* @BindView(R.id.vv_player)
     VideoView mPlayerVv;*/
   /* @BindView(R.id.pb_buffer)
    ProgressBar mBufferPb;*/
    @BindView(R.id.error_tv)
    TextView mErrorTV;
    private Uri mUri;
    private IRummyHelpRulePresenter mPresenter;
    private int mCategoryType = 1;  // 1 - > point, 2 -> deal, 3 -> pool, 4 -> tips, 5 -> offer
    private int mSubCategoryType = 1;  // 1,1 2,1
    private int mLanguage;  // 1 -> English, 2 -> Hindi, 3 -> Hinglish
    private int mIsFirstTime = 0;
    private List<HelpRulesPointData> mHelpRulesPointData = new ArrayList<>();
    private RummyHelpAdapter mRummyRuleAdapter;
    private YouTubePlayer mYouTubePlayer;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_rummy_help;
    }

    @Override
    protected void initViews() {
        mPresenter = new RummyHelpRulePresenter(this);
        mHelpRv.setLayoutManager(new LinearLayoutManager(this));
        mRummyRuleAdapter = new RummyHelpAdapter(this, mHelpRulesPointData);
        mHelpRv.setAdapter(mRummyRuleAdapter);
        mTranslatorIv.setVisibility(View.VISIBLE);
        mPaymentHistoryTv.setVisibility(View.GONE);
        mHistoryTv.setVisibility(View.GONE);
        mActivityNameTv.setText("Rummy Adda");
        mLanguage = getIntent().getIntExtra("type", 1);
        modeSet(1);
    }

    @Override
    protected void initVariables() {
        mPointsTV.setOnClickListener(this);
        mPoolTv.setOnClickListener(this);
        mDealTv.setOnClickListener(this);
        mTipsTv.setOnClickListener(this);
        mOfferTv.setOnClickListener(this);
        mOneTv.setOnClickListener(this);
        mTwoTv.setOnClickListener(this);
        mThreeTv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mTranslatorIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mIsFirstTime = 1;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_points:
                mCategoryType = 1;
                modeSet(1);
//                getData();
                break;

            case R.id.tv_pool:
                mCategoryType = 3;
                modeSet(2);
//                getData();
                break;

            case R.id.tv_deal:
                mCategoryType = 2;
                modeSet(3);
//                getData();
                break;

            case R.id.tv_tips:
                mCategoryType = 4;
                modeSet(4);
//                getData();
                break;

            case R.id.tv_offer:
                mCategoryType = 5;
                modeSet(5);
//                getData();

                break;

            case R.id.tv_one:
                mSubCategoryType = 1;
                modeSubSet(6);
//                getData();

                break;


            case R.id.tv_two:
                mSubCategoryType = 2;
                modeSubSet(7);
//                getData();
                break;


            case R.id.tv_three:
                mSubCategoryType = 3;
                modeSubSet(8);
//                getData();
                break;

            case R.id.iv_translator:
                AppUtilityMethods.showRummyTooltip(this, mTranslatorIv, AppConstant.FROM_HELP_ACTIVITY);
                break;

        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            manageVideoBuffer();
            mPresenter.getRummyHelpRuleStatus(new RummyHelpRequest(mCategoryType, mSubCategoryType, mLanguage));
        } else {
            Snackbar.make(mHelpRv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void modeSet(int type) {
        mPointsTV.setSelected(false);
        mPoolTv.setSelected(false);
        mDealTv.setSelected(false);
        mTipsTv.setSelected(false);
        mOfferTv.setSelected(false);
        mPointsTV.setTextColor(Color.parseColor("#9A9797"));
        mPoolTv.setTextColor(Color.parseColor("#9A9797"));
        mDealTv.setTextColor(Color.parseColor("#9A9797"));
        mTipsTv.setTextColor(Color.parseColor("#9A9797"));
        mOfferTv.setTextColor(Color.parseColor("#9A9797"));
        mModeOptionLL.setVisibility(View.GONE);
        switch (type) {
            case 1:
                mModeOptionLL.setVisibility(View.VISIBLE);
                mPointsTV.setSelected(true);
                mPointsTV.setTextColor(getResources().getColor(R.color.battle_red));
                mThreeTv.setVisibility(View.GONE);
                mOneTv.setText("13 Cards");
                mTwoTv.setText("2 Jokers");
                modeSubSet(6);
                break;
            case 2:
                mModeOptionLL.setVisibility(View.VISIBLE);
                mPoolTv.setSelected(true);
                mPoolTv.setTextColor(getResources().getColor(R.color.battle_red));
                mOneTv.setText("51 Pool");
                mTwoTv.setText("101 Pool");
                mThreeTv.setVisibility(View.VISIBLE);
                modeSubSet(6);
                break;
            case 3:
                mDealTv.setSelected(true);
                mDealTv.setTextColor(getResources().getColor(R.color.battle_red));
                getData();
                break;
            case 4:
                mTipsTv.setSelected(true);
                mTipsTv.setTextColor(getResources().getColor(R.color.battle_red));
                getData();
                break;
            case 5:
                mOfferTv.setSelected(true);
                mOfferTv.setTextColor(getResources().getColor(R.color.battle_red));
                getData();
                break;
        }

    }

    private void modeSubSet(int type) {
        mOneTv.setTextColor(getResources().getColor(R.color.white));
        mOneTv.setTextAppearance(this, R.style.RummyModeButton);
        mOneTv.setSelected(false);
        mTwoTv.setTextColor(getResources().getColor(R.color.white));
        mTwoTv.setTextAppearance(this, R.style.RummyModeButton);
        mTwoTv.setSelected(false);
        mThreeTv.setTextColor(getResources().getColor(R.color.white));
        mThreeTv.setTextAppearance(this, R.style.RummyModeButton);
        mThreeTv.setSelected(false);
        switch (type) {
            case 6:
                mSubCategoryType = 1;
                mOneTv.setSelected(true);
                mOneTv.setTextColor(getResources().getColor(R.color.black));
                break;
            case 7:
                mSubCategoryType = 2;
                mTwoTv.setSelected(true);
                mTwoTv.setTextColor(getResources().getColor(R.color.black));
                break;
            case 8:
                mSubCategoryType = 3;
                mThreeTv.setSelected(true);
                mThreeTv.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (Objects.equals(event, "1")) {
            mLanguage = 1;
            getData();
        } else if (Objects.equals(event, "2")) {
            mLanguage = 2;
            getData();
        } else if (Objects.equals(event, "3")) {
            mLanguage = 3;
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onGetRummyHelpRuleSuccess(RummyHelpResponse responseModel) {
        hideProgress();
        try {
            if (responseModel.isStatus()) {
                mHelpRulesPointData.clear();
                playerVideo(responseModel);
                if (responseModel.getResponse().getmHelpRulesPointData() != null
                        && responseModel.getResponse().getmHelpRulesPointData().size() != 0) {
                    mHelpRulesPointData.addAll(responseModel.getResponse().getmHelpRulesPointData());
                    mRummyRuleAdapter.notifyDataSetChanged();
                }
                //issue
                if (mIsFirstTime != 0) {
                    mYouTubePlayer.loadVideo(responseModel.getResponse().getmVideo(), 0f);
                }
            }
        } catch (Exception e) {
            mHelpRulesPointData.clear();
            throw new RuntimeException(e);
        }
    }

    private void playerVideo(RummyHelpResponse responseModel) {
        initializeYoutubePlayer(responseModel.getResponse().getmVideo());

    }

    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
    private void initializeYoutubePlayer(String youtubeKey) {
        youTubePlayerView =findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.enableBackgroundPlayback(false);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                mYouTubePlayer = youTubePlayer;
                youTubePlayer.loadVideo(youtubeKey, 0f);
            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                super.onError(youTubePlayer, error);
                Toast.makeText(RummyHelpActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public void onGetRummyHelpRuleFailure(ApiError error) {
        hideProgress();
        mHelpRulesPointData.clear();

    }

    private void manageVideoBuffer() {
        /*mPlayerVv.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer mp) {
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp,int percent) {
                        if (percent==100){
                            mBufferPb.setVisibility(View.GONE);
                        } else {
                            mBufferPb.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });*/
    }

}