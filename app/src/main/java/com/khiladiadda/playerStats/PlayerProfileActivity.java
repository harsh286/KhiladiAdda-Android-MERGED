package com.khiladiadda.playerStats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.playerStats.model.PlayerProfileRes;
import com.khiladiadda.utility.AppConstant;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;

public class PlayerProfileActivity extends BaseActivity implements IPlayerProfile.IPlayerProfileView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.profile_pic)
    CircularImageView mProfile_pic;
    @BindView(R.id.tv_player_name)
    TextView mPlayerNameTv;
    @BindView(R.id.tv_player_country)
    TextView mPlayerCountryTv;
    @BindView(R.id.tv_player_born)
    TextView mPlayerAgeTv;
    @BindView(R.id.tv_player_type)
    TextView mPlayerTypeTv;
    @BindView(R.id.tv_style)
    TextView mPlayerStyleTv;
    @BindView(R.id.tv_odi_batting_m)
    TextView mOdiBattingMTv;
    @BindView(R.id.tv_odi_batting_runs)
    TextView mOdiBattingRunsTv;
    @BindView(R.id.tv_odi_batting_hs)
    TextView mOdiBattingHsTv;
    @BindView(R.id.tv_odi_batting_50)
    TextView mOdiBatting50Tv;
    @BindView(R.id.tv_odi_batting_sr)
    TextView mOdiBattingSrTv;
    @BindView(R.id.tv_t20i_batting_m)
    TextView mT20BattingMTv;
    @BindView(R.id.tv_t20i_batting_runs)
    TextView mT20BattingRunsTv;
    @BindView(R.id.tv_t20i_batting_hs)
    TextView mT20BattingHSTv;
    @BindView(R.id.tv_t20i_batting_50)
    TextView mT20Batting50Tv;
    @BindView(R.id.tv_t20i_batting_sr)
    TextView mT20BattingSrTv;
    @BindView(R.id.tv_odi_bowling_m)
    TextView mOdiBowlingMTv;
    @BindView(R.id.tv_odi_bowling_b)
    TextView mOdiBowlingBallsTv;
    @BindView(R.id.tv_odi_bowling_wkts)
    TextView mOdiBowlingWktsTv;
    @BindView(R.id.tv_odi_bowling_avg)
    TextView mOdiBowlingAvgTv;
    @BindView(R.id.tv_odi_bowling_sr)
    TextView mOdiBowlingSrTv;
    @BindView(R.id.tv_t20i_bowling_m)
    TextView mT20BowlingMTv;
    @BindView(R.id.tv_t20i_bowling_b)
    TextView mT20BowlingBallsTv;
    @BindView(R.id.tv_t20i_bowling_wkts)
    TextView mT20BowlingWktsTv;
    @BindView(R.id.tv_t20i_bowling_avg)
    TextView mT20BowlingAvgTv;
    @BindView(R.id.tv_t20i_bowling_sr)
    TextView mT20BowlingSrTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_player_stats;
    }

    @Override
    protected void initViews() {
        PlayerProfilePresenter playerProfilePresenter = new PlayerProfilePresenter(this);
        playerProfilePresenter.check(AppConstant.CRIC_API_KEY, Integer.parseInt(getIntent().getStringExtra(AppConstant.PLAYER_ID)));
        showProgress(getString(R.string.text_waiting_dot));
    }

    @Override
    protected void initVariables() {
        mBackIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back) {
                finish();
        }
    }

    @Override
    public void onSuccess(PlayerProfileRes playerProfileRes) {
        try {
            Glide.with(mProfile_pic).load(playerProfileRes.getImageURL()).placeholder(R.mipmap.ic_launcher).into(mProfile_pic);
        }finally {
            mPlayerNameTv.setText(playerProfileRes.getName());
            mPlayerCountryTv.setText(playerProfileRes.getCountry());
            mPlayerTypeTv.setText(playerProfileRes.getPlayingRole());
            mPlayerStyleTv.setText(playerProfileRes.getBattingStyle());
            //mPlayerAgeTv.setText(playerProfileRes.getCurrentAge());
            //Batting ODI
            mOdiBattingMTv.setText(playerProfileRes.getData().getBatting().getODIs().getMat());
            mOdiBattingRunsTv.setText(playerProfileRes.getData().getBatting().getODIs().getRuns());
            mOdiBattingHsTv.setText(playerProfileRes.getData().getBatting().getODIs().getHS());
            mOdiBatting50Tv.setText(playerProfileRes.getData().getBatting().getODIs().get50());
            mOdiBattingSrTv.setText(playerProfileRes.getData().getBatting().getODIs().getSR());

            //Batting T20
            mT20BattingMTv.setText(playerProfileRes.getData().getBatting().getT20Is().getMat());
            mT20BattingRunsTv.setText(playerProfileRes.getData().getBatting().getT20Is().getRuns());
            mT20BattingHSTv.setText(playerProfileRes.getData().getBatting().getT20Is().getHS());
            mT20Batting50Tv.setText(playerProfileRes.getData().getBatting().getT20Is().get50());
            mT20BattingSrTv.setText(playerProfileRes.getData().getBatting().getT20Is().getSR());

            //Bowling ODI
            mOdiBowlingMTv.setText(playerProfileRes.getData().getBowling().getODIs().getMat());
            mOdiBowlingBallsTv.setText(playerProfileRes.getData().getBowling().getODIs().getBalls());
            mOdiBowlingWktsTv.setText(playerProfileRes.getData().getBowling().getODIs().getWkts());
            mOdiBowlingAvgTv.setText(playerProfileRes.getData().getBowling().getODIs().getAve());
            mOdiBowlingSrTv.setText(playerProfileRes.getData().getBowling().getODIs().getSR());

            //BowlingT20
            mT20BowlingMTv.setText(playerProfileRes.getData().getBowling().getT20Is().getMat());
            mT20BowlingBallsTv.setText(playerProfileRes.getData().getBowling().getT20Is().getBalls());
            mT20BowlingWktsTv.setText(playerProfileRes.getData().getBowling().getT20Is().getWkts());
            mT20BowlingAvgTv.setText(playerProfileRes.getData().getBowling().getT20Is().getAve());
            mT20BowlingSrTv.setText(playerProfileRes.getData().getBowling().getT20Is().getSR());

        }
        hideProgress();
    }

    @Override
    public void showError(String message) {
        hideProgress();
    }

    @Override
    public void onLive(CricScorce response) {

    }

    @Override
    public void onFailure(CricApiError error) {
        hideProgress();
    }

}