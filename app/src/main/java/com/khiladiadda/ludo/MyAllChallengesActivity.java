package com.khiladiadda.ludo;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.ludo.adapter.MyAllLudoChallengeAdapter;
import com.khiladiadda.ludo.adapter.MyAllNewChallengeAdapter;
import com.khiladiadda.ludo.adapter.MyChallengeAdapter;
import com.khiladiadda.ludo.interfaces.ILudoChallengePresenter;
import com.khiladiadda.ludo.interfaces.ILudoChallengeView;
import com.khiladiadda.ludo.result.LudoResultActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.PAGE_SIZE;

public class MyAllChallengesActivity extends BaseActivity implements ILudoChallengeView, IOnItemClickListener, MyAllNewChallengeAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.rv_ludo_contest) RecyclerView mLudoContestRV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;

    private ILudoChallengePresenter mPresenter;
    private List<LudoContest> mLudoContestList;
    private MyAllNewChallengeAdapter mAllChallengeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoNotificationReceiver, new IntentFilter("com.khiladiadda.LUDO_NOTIFY"));
    }

    @Override protected int getContentView() {
        return R.layout.activity_my_all_challenges;
    }

    @Override protected void initViews() {
        int mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        if (mContestType == AppConstant.TYPE_LUDO) {
            mActivityNameTV.setText(R.string.ludo_king);
        } else {
            mActivityNameTV.setText(R.string.text_snake_ladder_adda);
        }
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        mPresenter = new LudoChallengePresenter(this);
        mLudoContestList = new ArrayList<>();
        mAllChallengeAdapter = new MyAllNewChallengeAdapter(this, mLudoContestList);
        GridLayoutManager mLudoLayoutManager = new GridLayoutManager(this, 2);
        mLudoContestRV.setLayoutManager(mLudoLayoutManager);
        mLudoContestRV.setAdapter(mAllChallengeAdapter);
        mAllChallengeAdapter.setOnItemClickListener(this);
        mAllChallengeAdapter.setOnItemChildClickListener(this);
        getData();
    }

    private void getData() {
        AppUtilityMethods.deleteCache(this);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getAllContestList(String.valueOf(getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0)), 0, AppConstant.PAGE_SIZE);
        } else {
            Snackbar.make(mLudoContestRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
        }
    }

    @Override public void onGetContestSuccess(LudoContestResponse responseModel) {
        hideProgress();
        if(responseModel.getMyContests().size()>0) {
            mLudoContestList.clear();
            mLudoContestList.addAll(responseModel.getMyContests());
            mAllChallengeAdapter.notifyDataSetChanged();
        }else{
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override public void onGetContestFailure(ApiError error) {
        hideProgress();
    }

    @Override public void addChallengeSuccess(BaseResponse response) { }

    @Override public void addChallengeFailure(ApiError error) { }

    @Override
    public void acceptContestSuccess(BaseResponse response) {
    }

    @Override
    public void acceptContestFailure(ApiError error) {
    }

    @Override
    public void cancelContestSuccess(BaseResponse response) {
    }

    @Override
    public void cancelContestFailure(ApiError error) {
    }

    private BroadcastReceiver mLudoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getData();
        }
    };

    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLudoNotificationReceiver);
        mPresenter.destroy();
        super.onDestroy();
    }

    private void openResultScreen(int position) {
        Intent intent = new Intent(this, LudoResultActivity.class);
        intent.putExtra(AppConstant.LUDO_CONTEST, mLudoContestList.get(position));
        intent.putExtra(AppConstant.CONTEST_TYPE, AppConstant.TYPE_LUDO);
        ludoResultActivityResultLauncher.launch(intent);
    }

    @Override
    public void onPlayClicked(int position) {
      openResultScreen(position);
    }

    @Override
    public void onCancelClick(int position) {
        openResultScreen(position);
    }

    @Override
    public void onReviewClick(int position) {
        checkChallengeStatus(position);
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        checkChallengeStatus(position);
    }

    private void checkChallengeStatus(int position) {
        String mUserId = mAppPreference.getString(AppConstant.USER_ID, "");
        boolean mIsCaptain = mUserId.equals(mLudoContestList.get(position).getCaptainId());
        if (!mLudoContestList.get(position).isResultDeclared() && !mLudoContestList.get(position).isCancelled()) {
            if (mLudoContestList.get(position).getAdminStatus() == 1) {
                AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
            } else if (mLudoContestList.get(position).getAdminStatus() == 2) {
                if (((mIsCaptain && ((mLudoContestList.get(position).getCaptainError() != null && !TextUtils.isEmpty(mLudoContestList.get(position).getCaptainError().getImg())) || mLudoContestList.get(position).isCaptainDeclared())))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
                } else if (((!mIsCaptain && ((mLudoContestList.get(position).getOpponentError() != null && !TextUtils.isEmpty(mLudoContestList.get(position).getOpponentError().getImg())) || mLudoContestList.get(position).isOpponentDeclared())))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_review_admin), false);
                } else {
                    openResultScreen(position);
                }
            } else if ((mIsCaptain && mLudoContestList.get(position).isCaptainDeclared())) {
                showMsg(this, getString(R.string.text_waiting_opponent_result), false, AppConstant.FROM_LUDO_RESULT, position);
            } else if ((!mIsCaptain && mLudoContestList.get(position).isOpponentDeclared())) {
                showMsg(this, getString(R.string.text_waiting_opponent_result), false, AppConstant.FROM_LUDO_RESULT, position);
            } else {
                openResultScreen(position);
            }
        }
    }

    public void showMsg(final Context activity, String msg, boolean isCancel, int from, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.challenge_add_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        if (from == AppConstant.FROM_ADD) {
            tv_help.setText(getString(R.string.text_help_captain));
        } else if (from == AppConstant.FROM_UPDATE) {
            tv_help.setText(getString(R.string.text_help_opponent));
        } else if (from == AppConstant.FROM_LUDO_RESULT) {
            tv_msg.setText(getString(R.string.ludo_result));
            tv_help.setText(msg);
        } else if (from == 100) {
            mAppPreference.setBoolean(AppConstant.LUDO_SECURE_MSG, true);
            tv_help.setText("Ab se apko opponent ka naam random dikhega aur uska real name nahi dikhega. Isse KhiladiAdda platform and apke challenges aur safe and secure ho jayenge...\n!!!Dhanyawad!!!\n\nFrom now onwards opponent name will not appear and random name be displayed for more security of your challenges.\n!!!ThankYou!!!");
        } else {
            tv_help.setVisibility(View.GONE);
        }
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (from == AppConstant.FROM_LUDO_RESULT) {
                openResultScreen(position);
            } else if(from != 100){
                getData();
            }
        });
        dialog.show();
    }

    ActivityResultLauncher<Intent> ludoResultActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (mAppPreference.getBoolean(AppConstant.IS_DATA_CHANGE, false)) {
                   getData();
                }
            });

}