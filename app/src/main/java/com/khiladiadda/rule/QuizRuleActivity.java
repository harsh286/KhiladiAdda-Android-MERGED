package com.khiladiadda.rule;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageActivity;

import butterknife.BindView;

public class QuizRuleActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_league)
    TextView mLeagueTV;
    @BindView(R.id.tv_home)
    TextView mHomeTV;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.tv_rule)
    TextView mRuleTV;
    @BindView(R.id.btn_view_video)
    Button mVideoViewBTN;
    @BindView(R.id.iv_cod_help)
    ImageView mCodHelpIV;

    @Override
    protected int getContentView() {
        return R.layout.activity_quiz_rule;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_rules);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mHomeTV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mRuleTV.setMovementMethod(new ScrollingMovementMethod());
        mVideoViewBTN.setOnClickListener(this);
        mCodHelpIV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_HOW_JOINING)) {
            mRuleTV.setText(AppConstant.RULE_JOIN);
            mVideoViewBTN.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_ID)) {
            mRuleTV.setText(AppConstant.TEXT_JOIN_PUBGNS);
            mVideoViewBTN.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_TDM)) {
            mActivityNameTV.setText(R.string.text_rules_tdm);
            mRuleTV.setText(AppConstant.RULE_TDM);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_BGMI)) {
            mActivityNameTV.setText(R.string.text_rules_bgmi);
            mRuleTV.setText(AppConstant.RULE_GAME);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_CALLOFDUTY)) {
            mActivityNameTV.setText(R.string.text_rules_cod);
            mRuleTV.setText(AppConstant.RULE_CALL_DUTY);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_FREEFIRE)) {
            mActivityNameTV.setText(R.string.text_rules_ff);
            mRuleTV.setText(AppConstant.RULE_FREEFIRE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_FF_CLASH)) {
            mActivityNameTV.setText(R.string.text_rules_ff_clash);
            mRuleTV.setText(AppConstant.RULE_FF_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_FF_MAX)) {
            mActivityNameTV.setText(R.string.text_rules_ff_max);
            mRuleTV.setText(AppConstant.RULE_FREEFIRE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FREEFIRE_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_ff);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_FREEFIRE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FF_CLASH_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_ff_clash);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_FF_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FF_MAX_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_ff_max);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_FREEFIRE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_QUIZ)) {
            mActivityNameTV.setText(R.string.text_rules_quiz);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_QUIZ);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PUBG_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_tdm);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_TDM);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PUBG_LITE_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_bgmi);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_GAME);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.CALL_DUTY_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_cod);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_CALL_DUTY);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.CLASHROYALE)) {
            mActivityNameTV.setText(R.string.text_rules_cr);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_CLASHROYALE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PUBG_GLOBAL_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_pubglobal);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_PUBGLOBAL_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PREMIUM_ESPORTS_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_esp);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.RULE_ESP_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_GLOBAL)) {
            mActivityNameTV.setText(R.string.text_rules_pubglobal);
            mRuleTV.setText(AppConstant.RULE_PUBGLOBAL_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_PREMIUM_ESPORTS)) {
            mActivityNameTV.setText(R.string.text_rules_esp);
            mRuleTV.setText(AppConstant.RULE_ESP_CLASH);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_PUBG_NEWSTATE)) {
            mActivityNameTV.setText(R.string.text_rules_pgns);
            mRuleTV.setText(AppConstant.TEXT_RULE_PUBGNS);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.PUBG_NEWSTATE_SOLO)) {
            mActivityNameTV.setText(R.string.text_rules_pgns);
            mVideoViewBTN.setVisibility(View.GONE);
            mRuleTV.setText(AppConstant.TEXT_RULE_PUBGNS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_league:
                Intent league = new Intent(this, MyLeagueActivity.class);
                startActivity(league);
                break;
            case R.id.tv_help:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                break;
            case R.id.btn_view_video:
                AppUtilityMethods.openLudoVideo(QuizRuleActivity.this);
                break;
            case R.id.iv_cod_help:
                Intent i = new Intent(this, ImageActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.CALLOFDUTY);
                startActivity(i);
                break;
        }
    }

}