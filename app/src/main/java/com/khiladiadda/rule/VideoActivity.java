//package com.khiladiadda.rule;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.MediaController;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import com.khiladiadda.R;
//import com.khiladiadda.base.BaseActivity;
//
//import com.khiladiadda.fanbattle.FanBattleActivity;
//import com.khiladiadda.fcm.NotificationActivity;
//import com.khiladiadda.utility.AppConstant;
//
//import butterknife.BindView;
//
//public class VideoActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {
//
//    @BindView(R.id.iv_back)
//    ImageView mBackIV;
//    @BindView(R.id.tv_activity_name)
//    TextView mActivityNameTV;
//    @BindView(R.id.iv_notification)
//    ImageView mNotificationIV;
//    @BindView(R.id.videoview)
//    VideoView mVideoViewVV;
//    @BindView(R.id.tv_quiz_timer)
//    TextView mVideoTimerTV;
//    @BindView(R.id.pb_quiz_timer)
//    ProgressBar mVideoTimerPB;
//    private static final int COUNTDOWN_TIME = 31;
//    private int mTimerValue = COUNTDOWN_TIME;
//    private Handler mHandler = new Handler();
//    private Handler mHandler1 = new Handler();
//    MediaController mediaController;
//    int stopPosition;
//    int i = 0;
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_video;
//    }
//
//    @Override
//    protected void initViews() {
//        mActivityNameTV.setText(R.string.text_view_video);
//        mBackIV.setOnClickListener(this);
//        mNotificationIV.setOnClickListener(this);
//        startUpdateQuizTimer();
//    }
//
//    @Override
//    protected void initVariables() {
//        mediaController = new MediaController(this);
//        mediaController.setAnchorView(mVideoViewVV);
//        mediaController.setVisibility(View.GONE);
//        mVideoViewVV.setMediaController(null);
//        String path = "android.resource://" + getPackageName() + "/" + R.raw.fb_video;
//        mVideoViewVV.setVideoURI(Uri.parse(path));
//        mVideoViewVV.setOnCompletionListener(this);
//        mVideoViewVV.start();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.iv_notification:
//                startActivity(new Intent(this, NotificationActivity.class));
//                break;
//        }
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        if (mAppPreference.getBoolean(AppConstant.FB_VIDEO_SEEN, false)) {
//            finish();
//        } else {
//            mAppPreference.setBoolean(AppConstant.FB_VIDEO_SEEN, true);
//            startActivity(new Intent(this, FanBattleActivity.class));
//            finish();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mAppPreference.getBoolean(AppConstant.FB_VIDEO_SEEN, false)) {
//            super.onBackPressed();
//        }
//    }
//
//    public void startUpdateQuizTimer() {
//        mHandler.postDelayed(mQuizTimerRunnable, 1000);
//    }
//
//    public void stopQuizTimer() {
//        mHandler.removeCallbacksAndMessages(null);
//    }
//
//    private Runnable mQuizTimerRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (mTimerValue < 0) {
//                stopQuizTimer();
//            } else {
//                mVideoTimerTV.setText(mTimerValue + "s");
//                mVideoTimerPB.setProgress((COUNTDOWN_TIME - mTimerValue));
//                mTimerValue--;
//                startUpdateQuizTimer();
//            }
//        }
//    };
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopPosition = mVideoViewVV.getCurrentPosition();
//        mVideoViewVV.pause();
//        mHandler.removeCallbacksAndMessages(null);
//        i = 1;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("TAG", "onResume called");
//        mVideoViewVV.seekTo(stopPosition);
//        mVideoViewVV.start();
//        if (i != 0)
//            startUpdateQuizTimer();
//    }
//
//}