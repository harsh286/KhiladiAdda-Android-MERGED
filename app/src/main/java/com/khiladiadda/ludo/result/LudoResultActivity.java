package com.khiladiadda.ludo.result;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.chat.ChatActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnLudoUpdateListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.ludo.result.interfaces.ILudoResultPresenter;
import com.khiladiadda.ludo.result.interfaces.ILudoResultView;
import com.khiladiadda.ludo.unplayed.LudoErrorActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.request.LudoUpdateRequest;
import com.khiladiadda.network.model.response.LudoContest;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.Date;

import butterknife.BindView;

public class LudoResultActivity extends BaseActivity implements ILudoResultView, IOnLudoUpdateListener {
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_contest_code)
    TextView mContestNameTV;
    @BindView(R.id.iv_captain_dp)
    ImageView mCaptainIV;
    @BindView(R.id.tv_captain_detail)
    TextView mCaptainNameTV;
    @BindView(R.id.iv_player_dp)
    ImageView mPlayerIV;
    @BindView(R.id.tv_player_detail)
    TextView mPlayerNameTV;
    @BindView(R.id.tv_vs)
    TextView mVsTV;
    @BindView(R.id.tv_amount)
    TextView mAmountTV;
    @BindView(R.id.tv_winning_amount)
    TextView mWinningAmountTV;
    @BindView(R.id.tv_roomid)
    TextView mRoomIdTV;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.btn_update_roomid)
    Button mUpdateRoomIdBTN;
    @BindView(R.id.btn_chat)
    Button mChatBTN;
    @BindView(R.id.btn_won)
    Button mWonBTN;
    @BindView(R.id.btn_lost)
    Button mLostBTN;
    @BindView(R.id.btn_error)
    Button mErrorBTN;
    @BindView(R.id.ll_player)
    LinearLayout mPlayerLL;
    private Dialog mUpdateLudoDialog;
    private ILudoResultPresenter mPresenter;
    private LudoContest mLudoContest;
    private String mRoomCode;
    private boolean mIsCaptain, mIsDataChange;
    private int mContestType;
    private MediaPlayer mMediaPlayer;
    Handler handler = new Handler();
    private boolean isRoomCodeCopy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoNotificationReceiver, new IntentFilter("com.khiladiadda.LUDO_NOTIFY"));
    }

    private BroadcastReceiver mLudoNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            openLudoChallengeActivity();
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLudoNotificationReceiver);
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(runnable, 37000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private void stopPlayer() {
        removeCallbacks();
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        removeCallbacks();
        super.onBackPressed();
    }

    private void removeCallbacks() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    Runnable runnable = this::stopPlayer;

    @Override
    protected int getContentView() {
        return R.layout.activity_challenge_result;
    }

    @Override
    protected void initViews() {
        mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        if (mContestType == AppConstant.TYPE_LUDO) {
            mActivityNameTV.setText(getString(R.string.text_ludo_contest));
        }
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);
        mUpdateRoomIdBTN.setOnClickListener(this);
        mWonBTN.setOnClickListener(this);
        mLostBTN.setOnClickListener(this);
        mErrorBTN.setOnClickListener(this);
        mChatBTN.setOnClickListener(this);
        mRoomIdTV.setOnClickListener(this);
        mMediaPlayer = MediaPlayer.create(this, R.raw.ludo_audio);
        playAudio();
    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoResultPresenter(this);
        mLudoContest = getIntent().getParcelableExtra(AppConstant.LUDO_CONTEST);
        setData();
    }

    private void playAudio() {
        int playedAudio = mAppPreference.getInt(AppConstant.LUDO_AUDIO, 0) + 1;
        if (playedAudio <= 5) {
            mMediaPlayer.start();
            mAppPreference.setInt(AppConstant.LUDO_AUDIO, playedAudio);
        }
    }

    private void setData() {
        mContestNameTV.setText(mLudoContest.getContestCode());
        String mUserId = mAppPreference.getString(AppConstant.USER_ID, "");
        mIsCaptain = mUserId.equals(mLudoContest.getCaptainId());
        Resources res = getResources();
        mAmountTV.setText(String.format(res.getString(R.string.text_challenged_coins), String.valueOf(mLudoContest.getEntryFees())));
        mWinningAmountTV.setText(String.format(res.getString(R.string.text_winning_coins), String.valueOf(mLudoContest.getWinningAmount())));
        if (mIsCaptain && mLudoContest.isAccepted()) {
            mCaptainNameTV.setText(mLudoContest.getCaptain().getName());
            showCaptainImage();
            mPlayerNameTV.setText(mLudoContest.getOpponent().getLudoName());
            showOpponentDummyImage();
        } else if (mIsCaptain && !mLudoContest.isAccepted()) {
            mCaptainNameTV.setText(mLudoContest.getCaptain().getName());
            showCaptainImage();
        } else {
            mCaptainNameTV.setText(mLudoContest.getCaptain().getLudoName());
            showCaptainDummyImage();
            mPlayerNameTV.setText(mLudoContest.getOpponent().getName());
            showOpponentImage();
        }
        if ((mIsCaptain && mLudoContest.isCaptainDeclared()) || (!mIsCaptain && mLudoContest.isOpponentDeclared())) {
            mUpdateRoomIdBTN.setVisibility(View.GONE);
        }
        if (mIsCaptain && mLudoContest.isResultDeclared()) {
            mRoomIdTV.setText(String.format(res.getString(R.string.text_room_code_slash), mLudoContest.getRoomId()));
            mCancelBTN.setVisibility(View.GONE);
            mUpdateRoomIdBTN.setVisibility(View.GONE);
            mChatBTN.setVisibility(View.VISIBLE);
            setStatusButton(true);
        } else if (mIsCaptain && !TextUtils.isEmpty(mLudoContest.getRoomId())) {
            mRoomIdTV.setText(String.format(res.getString(R.string.text_room_code_slash), mLudoContest.getRoomId()));
            mCancelBTN.setVisibility(View.VISIBLE);
            mCancelBTN.setText(R.string.click_to_go_ludo_king);
            mUpdateRoomIdBTN.setVisibility(View.VISIBLE);
            mChatBTN.setVisibility(View.VISIBLE);
            setStatusButton(true);
            updateMoengage(getString(R.string.click_to_go_ludo_king));
        } else if (mIsCaptain && mLudoContest.isAccepted() && TextUtils.isEmpty(mLudoContest.getRoomId())) {
            mRoomIdTV.setText(getString(R.string.text_update_room_code_proceed));
            mCancelBTN.setVisibility(View.VISIBLE);
            mUpdateRoomIdBTN.setVisibility(View.VISIBLE);
            mChatBTN.setVisibility(View.VISIBLE);
            setStatusButton(false);
            updateMoengage(getString(R.string.text_update_room_code_proceed));
        } else if (mIsCaptain && !mLudoContest.isAccepted()) {
            mRoomIdTV.setText(getString(R.string.text_waiting_opponent));
            mCancelBTN.setVisibility(View.VISIBLE);
            mUpdateRoomIdBTN.setVisibility(View.VISIBLE);
            mChatBTN.setVisibility(View.GONE);
            setStatusButton(false);
            updateMoengage(getString(R.string.text_waiting_opponent));
        } else if (!mIsCaptain && !TextUtils.isEmpty(mLudoContest.getRoomId())) {
            mRoomIdTV.setText(String.format(res.getString(R.string.text_room_code_slash), mLudoContest.getRoomId()));
            mCancelBTN.setVisibility(View.VISIBLE);
            mCancelBTN.setText(R.string.click_to_go_ludo_king);
            mChatBTN.setVisibility(View.VISIBLE);
            setStatusButton(true);
            updateMoengage(getString(R.string.click_to_go_ludo_king));
        } else if (!mIsCaptain && TextUtils.isEmpty(mLudoContest.getRoomId())) {
            mRoomIdTV.setText(getString(R.string.text_waiting_room_code));
            mChatBTN.setVisibility(View.VISIBLE);
            mCancelBTN.setVisibility(View.VISIBLE);
            setStatusButton(false);
            updateMoengage(getString(R.string.text_waiting_room_code));
        }
        mAppPreference.setBoolean(AppConstant.IS_DATA_CHANGE, false);
    }

    private void updateMoengage(String status) {
        Properties properties = new Properties();
        properties
                .addAttribute("game", "LudoKing")
                .addAttribute("current", status)
                .addAttribute("gamecreatedDate", new Date());
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "LudoKing", properties);
    }

    private void showCaptainImage() {
        if (!TextUtils.isEmpty(mLudoContest.getCaptain().getDp())) {
            Glide.with(this).load(mLudoContest.getCaptain().getDp()).into(mCaptainIV);
        }
    }

    private void showOpponentImage() {
        if (!TextUtils.isEmpty(mLudoContest.getOpponent().getDp())) {
            Glide.with(this).load(mLudoContest.getOpponent().getDp()).into(mPlayerIV);
        }
        mPlayerLL.setVisibility(View.VISIBLE);
        mVsTV.setVisibility(View.VISIBLE);
    }

    private void showCaptainDummyImage() {
        if (!TextUtils.isEmpty(mLudoContest.getCaptain().getLudoDp())) {
            Glide.with(this).load(mLudoContest.getCaptain().getLudoDp()).into(mCaptainIV);
        }
    }

    private void showOpponentDummyImage() {
        if (!TextUtils.isEmpty(mLudoContest.getOpponent().getLudoDp())) {
            Glide.with(this).load(mLudoContest.getOpponent().getLudoDp()).into(mPlayerIV);
        }
        mPlayerLL.setVisibility(View.VISIBLE);
        mVsTV.setVisibility(View.VISIBLE);
    }

    private void setStatusButton(boolean status) {
        if (status) {
            mWonBTN.setEnabled(true);
            mLostBTN.setEnabled(true);
            mErrorBTN.setEnabled(true);
        } else {
            mWonBTN.setEnabled(false);
            mLostBTN.setEnabled(false);
            mErrorBTN.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_roomid:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.room_id), mLudoContest.getRoomId());
                clipboard.setPrimaryClip(clip);
                Snackbar.make(mWonBTN, "RoomCode is copied. Go to LudoKing and paste on RoomCode.", Snackbar.LENGTH_SHORT).show();
                LudoUpdateRequest request = new LudoUpdateRequest(mRoomCode, true);
                isRoomCodeCopy = true;
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.updateLudoContest(mLudoContest.getId(), request);
                }
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_update_roomid:
                if (mLudoContest.isAccepted()) {
                    mUpdateLudoDialog = AppDialog.updateLudoDialog(this, this);
                } else {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_ludo_roomcode_update), false);
                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_won:
                if ((mIsCaptain && mLudoContest.isCaptainDeclared()) || (!mIsCaptain && mLudoContest.isOpponentDeclared())) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_already_updated_ludo_result), false);
                } else {
                    Intent i = new Intent(this, LudoErrorActivity.class);
                    i.putExtra(AppConstant.FROM, AppConstant.LUDO_WON);
                    i.putExtra(AppConstant.ID, mLudoContest.getId());
                    i.putExtra(AppConstant.CONTEST_TYPE, mContestType);
                    startActivity(i);
                    finish();
                }
                break;
            case R.id.btn_lost:
                if ((mIsCaptain && mLudoContest.isCaptainDeclared()) || (!mIsCaptain && mLudoContest.isOpponentDeclared())) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_already_updated_ludo_result), false);
                } else {
                    showAlert(this, AppConstant.FROM_LOST);
                }
                break;
            case R.id.btn_error:
                if ((mIsCaptain && mLudoContest.isCaptainDeclared()) || (!mIsCaptain && mLudoContest.isOpponentDeclared())) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_already_updated_ludo_result), false);
                } else {
                    showAlert(this, AppConstant.FROM_ERROR);
                }
                break;
            case R.id.btn_cancel:
                if (new NetworkStatus(this).isInternetOn()) {
                    if (TextUtils.isEmpty(mLudoContest.getRoomId())) {
                        showAlert(this, AppConstant.FROM_CANCEL);
                    } else {
                        AppUtilityMethods.openLudoKing(this);
                    }
                } else {
                    Snackbar.make(mCancelBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_chat:
                Intent i = new Intent(this, ChatActivity.class);
                i.putExtra(AppConstant.ID, mLudoContest.getId());
                i.putExtra(AppConstant.ROOM_ID, mLudoContest.getContestCode());
                i.putExtra(AppConstant.RECEIVER_CHATID, AppConstant.FROM_WON);
                if (mIsCaptain) {
                    i.putExtra(AppConstant.IMAGE_PATH, mLudoContest.getOpponent().getLudoDp());
                    i.putExtra(AppConstant.USER_ID, mLudoContest.getOpponent().getLudoName());
                    i.putExtra(AppConstant.SENDER_ID, mLudoContest.getCaptainId());
                    i.putExtra(AppConstant.SENDER_NAME, mLudoContest.getCaptain().getName());
                    i.putExtra(AppConstant.RECEIVER_ID, mLudoContest.getOpponentId());
                    if (!TextUtils.isEmpty(mLudoContest.getOpponent().getFirebase_chat_id())) {
                        i.putExtra(AppConstant.RECEIVER_CHATID, mLudoContest.getOpponent().getFirebase_chat_id());
                    }
                } else {
                    i.putExtra(AppConstant.IMAGE_PATH, mLudoContest.getCaptain().getLudoDp());
                    i.putExtra(AppConstant.USER_ID, mLudoContest.getCaptain().getLudoName());
                    i.putExtra(AppConstant.SENDER_ID, mLudoContest.getOpponentId());
                    i.putExtra(AppConstant.SENDER_NAME, mLudoContest.getOpponent().getName());
                    i.putExtra(AppConstant.RECEIVER_ID, mLudoContest.getCaptainId());
                    if (!TextUtils.isEmpty(mLudoContest.getCaptain().getFirebase_chat_id())) {
                        i.putExtra(AppConstant.RECEIVER_CHATID, mLudoContest.getCaptain().getFirebase_chat_id());
                    }
                }
                startActivity(i);
                break;
        }
    }

    @Override
    public void onLudoResultSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsg(getString(R.string.text_ludo_result_declared), getString(R.string.text_ludo_result_lost), false);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onLudoResultFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateLudo(String roomId, boolean isRoomCodeCopy) {
        mRoomCode = roomId;
        LudoUpdateRequest request = new LudoUpdateRequest(roomId, isRoomCodeCopy);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.updateLudoContest(mLudoContest.getId(), request);
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancelLudoContestSuccess(BaseResponse response) {
        hideProgress();
        mCancelBTN.setVisibility(View.GONE);
        mUpdateRoomIdBTN.setVisibility(View.GONE);
        mWonBTN.setVisibility(View.GONE);
        mLostBTN.setVisibility(View.GONE);
        showMsg(getString(R.string.txt_canceled), getString(R.string.text_ludo_challenge_cancel), false);
    }

    @Override
    public void cancelLudoContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLudoUpdateSuccess(BaseResponse ludoUpdateResponse) {
        hideProgress();
        mIsDataChange = true;
        if (ludoUpdateResponse.isStatus()) {
            if (mUpdateLudoDialog != null && mUpdateLudoDialog.isShowing()) {
                mUpdateLudoDialog.dismiss();
            }
            if (!isRoomCodeCopy) {
                showMsg(getString(R.string.text_ludo_roomcode_updated), getString(R.string.text_help_captain_roomcode), false);
            }
        } else {
            AppUtilityMethods.showMsg(this, ludoUpdateResponse.getMessage(), false);
        }
    }

    @Override
    public void onLudoUpdateFailure(ApiError error) {
        hideProgress();
        if (mUpdateLudoDialog != null && mUpdateLudoDialog.isShowing()) {
            mUpdateLudoDialog.dismiss();
        }
        Snackbar.make(mActivityNameTV, error.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void openLudoChallengeActivity() {
        mAppPreference.setBoolean(AppConstant.IS_DATA_CHANGE, mIsDataChange);
        Intent intent = new Intent();
        setResult(AppConstant.FROM_LUDO_LIST, intent);
        finish();
    }

    public void showMsg(String msg, String help, boolean isCancel) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.challenge_add_complete_popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_help = dialog.findViewById(R.id.tv_help);
        tv_help.setText(help);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            openLudoChallengeActivity();
        });
        dialog.show();
    }

    public void showAlert(final Activity activity, final int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        Button mOkBTN = dialog.findViewById(R.id.btn_ok);
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        if (from == AppConstant.FROM_ERROR) {
            mOkBTN.setText(R.string.txt_continue);
            mNoBTN.setText(R.string.text_cancel);
            tv_msg.setText(getString(R.string.text_msg_error_ludo));
        } else if (from == AppConstant.FROM_LOST) {
            tv_msg.setText(getString(R.string.text_alert_ludo_lost));
        } else if (from == AppConstant.FROM_CANCEL) {
            tv_msg.setText(getString(R.string.text_cancel_ludo_confirm));
        }
        mOkBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            if (new NetworkStatus(activity).isInternetOn()) {
                if (from == AppConstant.FROM_ERROR) {
                    Intent i = new Intent(LudoResultActivity.this, LudoErrorActivity.class);
                    i.putExtra(AppConstant.FROM, AppConstant.LUDO_ERROR);
                    i.putExtra(AppConstant.ID, mLudoContest.getId());
                    i.putExtra(AppConstant.CONTEST_TYPE, mContestType);
                    startActivity(i);
                    finish();
                } else {
                    mIsDataChange = true;
                    showProgress(getString(R.string.txt_progress_authentication));
                    if (from == AppConstant.FROM_LOST) {
                        LudoResultRequest request = new LudoResultRequest(AppConstant.FROM_LOST, "");
                        mPresenter.updateLudoResult(mLudoContest.getId(), request);
                        Properties properties = new Properties();
                        properties.addAttribute("game", "LudoKing");
                        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Lose", properties);
                    } else if (from == AppConstant.FROM_CANCEL) {
                        mPresenter.cancelLudoContest(mLudoContest.getId());
                        Properties properties = new Properties();
                        properties.addAttribute("game", "LudoKing");
                        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "GameCanceled", properties);
                    }
                }
            } else {
                Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        });
        mNoBTN.setOnClickListener(arg0 -> dialog.dismiss());
        dialog.show();
    }

}