package com.khiladiadda.main.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.main.activity.ClashXDashBoardActivity;
import com.khiladiadda.fanbattle.FanBattleActivity;
import com.khiladiadda.gameleague.NewDroidoActivity;
import com.khiladiadda.gameleague.TrendingTournamentActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.leaderboard.NewLeaderboardActivity;
import com.khiladiadda.league.LeagueActivity;
import com.khiladiadda.ludo.LudoChallengeActivity;
import com.khiladiadda.ludo.luodhelp.LudoHelpActivity;
import com.khiladiadda.ludoTournament.activity.LudoTmtDashboardActivity;
import com.khiladiadda.ludoUniverse.LudoUniverseActivity;
import com.khiladiadda.main.fragment.HomeFragment;
import com.khiladiadda.main.game.adapter.TopKhiladiAdapter;
import com.khiladiadda.network.model.response.DashboardDetailResponse;
import com.khiladiadda.quiz.all.AllQuizListActivity;
import com.khiladiadda.scratchcard.ScratchCardActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.wordsearch.activity.WordSearchMainActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.Date;

import butterknife.BindView;

public class GameFragment extends BaseFragment implements HomeFragment.IOnPageLoaded {
    @BindView(R.id.tv_gift)
    TextView mGiftTV;
    @BindView(R.id.tv_quiz)
    TextView mQuizTV;
    @BindView(R.id.tv_winner)
    TextView mWinnerTV;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.iv_ludo)
    ImageView mLudoIV;
    @BindView(R.id.iv_droidd)
    ImageView mDroidIV;
    @BindView(R.id.iv_wordsearch)
    ImageView mWordSearchIV;
    @BindView(R.id.iv_ludo_universe)
    ImageView mLudoUniverseIV;
    @BindView(R.id.iv_clashx)
    ImageView mHTHIV;
    @BindView(R.id.iv_fanbattle)
    ImageView mFanbattleIV;
    @BindView(R.id.iv_freefire)
    ImageView mFreeFireIV;
    @BindView(R.id.iv_bgmi)
    ImageView mBGMIIV;
    @BindView(R.id.iv_ff_max)
    ImageView mFFMaxIV;
    @BindView(R.id.iv_ff_clash)
    ImageView mFFClashIV;
    @BindView(R.id.iv_tdm)
    ImageView mTDMIV;
    @BindView(R.id.iv_pubg_gobal_lite)
    ImageView mPubgGobalLiteIV;
    @BindView(R.id.iv_esportsperimum)
    ImageView mEsportsPremiumIV;
    @BindView(R.id.rv_top_khiladi)
    RecyclerView mTopKhiladiRV;
    @BindView(R.id.iv_ludotournament)
    ImageView mLudoTournamentIv;

    public static Fragment getInstance() {
        return new GameFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initViews(View view) {
        mGiftTV.setOnClickListener(this);
        mQuizTV.setOnClickListener(this);
        mWinnerTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mLudoIV.setOnClickListener(this);
        mDroidIV.setOnClickListener(this);
        mWordSearchIV.setOnClickListener(this);
        mLudoUniverseIV.setOnClickListener(this);
        mHTHIV.setOnClickListener(this);
        mFanbattleIV.setOnClickListener(this);
        mFreeFireIV.setOnClickListener(this);
        mBGMIIV.setOnClickListener(this);
        mFFMaxIV.setOnClickListener(this);
        mTDMIV.setOnClickListener(this);
        mFFClashIV.setOnClickListener(this);
        mPubgGobalLiteIV.setOnClickListener(this);
        mEsportsPremiumIV.setOnClickListener(this);
        mLudoTournamentIv.setOnClickListener(this);

    }

    @Override
    protected void initBundle(Bundle bundle) {
    }

    @Override
    protected void initVariables() {


    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        Properties properties = new Properties();
        switch (v.getId()) {
            case R.id.tv_gift:
                i = new Intent(getActivity(), ScratchCardActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "ScratchCard")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.tv_quiz:
                i = new Intent(getActivity(), AllQuizListActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "Quiz")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.tv_winner:
                i = new Intent(getActivity(), NewLeaderboardActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "OverAllLeaderBoard")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.tv_help:
                i = new Intent(getActivity(), HelpActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "Help")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_ludo:
                i = new Intent(getActivity(), LudoChallengeActivity.class);
                i.putExtra(AppConstant.CONTEST_TYPE, AppConstant.TYPE_LUDO);
                if (!mPreference.getBoolean(AppConstant.LUDO_VIDEO_SEEN, false)) {
                    i = new Intent(getActivity(), LudoHelpActivity.class);
                }
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "LudoKing")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_bgmi:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_BGMI);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "LeaguesBGMI")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_tdm:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "LeaguesTDM")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_freefire:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.FREEFIRE_SOLO);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "LeaguesFF")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_ff_clash:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_CLASH_SOLO);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "LeaguesFFCLash")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_fanbattle:
                i = new Intent(getActivity(), FanBattleActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "FanBattle")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_ff_max:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_MAX);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_MAX_SOLO);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "FreeFireMax")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_clashx:
                i = new Intent(getActivity(), ClashXDashBoardActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "ClashX")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_pubg_gobal_lite:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_GLOBAL);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.PUBG_GLOBAL_SOLO);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "PubgGlobal")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_esportsperimum:
                i = new Intent(getActivity(), LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PREMIUM_ESPORTS);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.PREMIUM_ESPORTS_SOLO);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "EsportsPerimum")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_wordsearch:
                i = new Intent(getActivity(), WordSearchMainActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "Word Seacrh")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_droidd:
                i = new Intent(getActivity(), NewDroidoActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "Droid-Do")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_ludo_universe:
                i = new Intent(getActivity(), LudoUniverseActivity.class);
                properties
                        // tracking click
                        .addAttribute(AppConstant.ScreenName, "Ludo Adda")
                        .addAttribute(AppConstant.ClickedDate, new Date());
                MoEAnalyticsHelper.INSTANCE.trackEvent(getContext(), "ScreenOpened", properties);
                break;
            case R.id.iv_ludotournament:
                i = new Intent(getActivity(), LudoTmtDashboardActivity.class);
                break;

        }
        startActivity(i);
    }

    public void setUpAdvertisement() {
        if (mPreference == null) {
            return;
        }
        DashboardDetailResponse dashboardData = mPreference.getDashboardData();
        if (dashboardData.getTopUsers() != null && dashboardData.getTopUsers().size() > 0) {
            TopKhiladiAdapter mTopKhiladiAdapter = new TopKhiladiAdapter(getActivity(), dashboardData.getTopUsers());
            mTopKhiladiRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mTopKhiladiRV.setAdapter(mTopKhiladiAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}