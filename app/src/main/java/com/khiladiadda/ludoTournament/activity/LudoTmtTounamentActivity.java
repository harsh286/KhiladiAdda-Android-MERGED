package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.ludoTournament.adapter.LudoTmtRoundAdapter;
import com.khiladiadda.ludoTournament.ip.ILudoTmtDetailsView;
import com.khiladiadda.ludoTournament.ip.ILudoTmtRoundsView;
import com.khiladiadda.ludoTournament.ip.LudoTmtJoinPresenter;
import com.khiladiadda.ludoTournament.ip.LudoTmtRoundsPresenter;
import com.khiladiadda.ludoTournament.listener.IOnClickListener;
import com.khiladiadda.ludoUniverse.LudoUniverseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import butterknife.BindView;

public class LudoTmtTounamentActivity extends BaseActivity implements ILudoTmtDetailsView, ILudoTmtRoundsView,
        IOnClickListener {

    @BindView(R.id.img_rules)
    ImageView rulesImg;
    @BindView(R.id.iv_back)
    ImageView backIv;
    @BindView(R.id.cl_match_close)
    ConstraintLayout matchCloseCl;
    @BindView(R.id.mcv_join)
    MaterialCardView mcvJoin;
    @BindView(R.id.tv_entry_fee)
    TextView entryFee;
    @BindView(R.id.tv_rounds)
    TextView roundsTv;
    @BindView(R.id.tv_start_time)
    TextView startTimeTv;
    @BindView(R.id.cl_join_tournaments)
    ConstraintLayout joinTournamentCl;
    @BindView(R.id.rv_match_tournament)
    RecyclerView matchRoundsRv;
    @BindView(R.id.tv_total_participants_new)
    TextView totalParticipantsNew;
    @BindView(R.id.pb_joined)
    ProgressBar joinedPb;

    private Context context;
    private Activity activity;
    private IOnClickListener onClickListener;
    private LudoTmtJoinPresenter mPresenter;
    private LudoTmtRoundsPresenter mRoundPresenter;
    private LudoTmtAllTournamentResponse matchDetailsResponse;
    private String mVersion, mCurrentVersion, mLink, mFilePath;
    private Intent launchIntent;
    private Dialog mVersionDialog;
    private double mDepositWinWallet;
    private double mTotalWalletBal;
    private boolean mIsRequestingAppInstallPermission;
    private LudoTmtRoundsDetailsMainResponse mDataResponse;
    private String tournamentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLudoTmtNotificationReceiver, new IntentFilter(AppConstant.LUDO_TOURNAMENT_PACKAGE));

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_tounament;
    }

    @Override
    protected void initViews() {
        context = this;
        activity = this;
        onClickListener = this;
        AppSharedPreference.initialize(this);
        mPresenter = new LudoTmtJoinPresenter(this);
        mRoundPresenter = new LudoTmtRoundsPresenter(this);
        matchDetailsResponse = getIntent().getParcelableExtra("AllLudoTournaments");
        mLink = AppSharedPreference.getInstance().getVersion().getVersion().getLudoApkLink();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.LudoAddaPackageName);
        if (launchIntent != null) getVersion();
        mCurrentVersion = AppSharedPreference.getInstance().getVersion().getVersion().getLudoAddaVersion();
        Coins mCoins = AppSharedPreference.getInstance().getProfileData().getCoins();
        mDepositWinWallet = mCoins.getDeposit() + mCoins.getWinning();
        mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        getJoined();
        setupUi();
//        callRoundsApi();

    }

    @Override
    protected void initVariables() {
        rulesImg.setOnClickListener(this);
        mcvJoin.setOnClickListener(this);
        backIv.setOnClickListener(this);
        joinTournamentCl.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.img_rules) {
            startActivity(new Intent(this, LudoTmtRulesActivity.class));
        } else if (p0.getId() == R.id.iv_back) {
            finish();
        } else if (p0.getId() == R.id.mcv_join) {
            if (launchIntent != null) {
                if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                    showConfirmation(Double.parseDouble(matchDetailsResponse.getEntryFees().toString()));
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        } else if (p0.getId() == R.id.cl_join_tournaments) {
            if (launchIntent != null) {
                if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                    showConfirmation(Double.parseDouble(matchDetailsResponse.getEntryFees().toString()));
                } else {
                    mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                }
            } else {
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
            }
        }
    }

    private void getJoined() {
        if (matchDetailsResponse.isJoined()) {
            mcvJoin.setVisibility(View.GONE);
            callRoundsApi();
        } else {
            mcvJoin.setVisibility(View.VISIBLE);
        }
    }

    private void setupUi() {
        entryFee.setText("" + Double.parseDouble(matchDetailsResponse.getEntryFees().toString()) + " Coins");
        roundsTv.setText("" + matchDetailsResponse.getTtLevel());
        startTimeTv.setText(AppUtilityMethods.getConvertDateTimeMatch(matchDetailsResponse.getStartDate()));
        totalParticipantsNew.setText(matchDetailsResponse.getnParticipated() + "/" + matchDetailsResponse.getnParticipants());
        joinedPb.setProgress(matchDetailsResponse.getnParticipated());
        joinedPb.setMax(matchDetailsResponse.getnParticipants());
    }

    private void callJoinApi() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.onJoinTournament(matchDetailsResponse.getId());
        } else {
            Snackbar.make(backIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void callRoundsApi() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mRoundPresenter.getTournamentRounds(matchDetailsResponse.getId());
        } else {
            Snackbar.make(backIv, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJoinLudoTournamentComplete(LudoTmtJoinMainResponse response) {
        hideProgress();
        Snackbar.make(backIv, response.getMessage(), Snackbar.LENGTH_SHORT).show();
        mcvJoin.setVisibility(View.INVISIBLE);
        callRoundsApi();
    }

    @Override
    public void onJoinLudoTournamentFailure(ApiError errorMsg) {
        hideProgress();
        Toast.makeText(this, "" + errorMsg.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetRoundsTournamentComplete(LudoTmtRoundsDetailsMainResponse response) {
        hideProgress();
        if (response.isStatus()) {
//            if (response.getR) // todo hide round recycler
            mDataResponse = response;
            tournamentId = response.getTournamentDetails().getId();
            matchRoundsRv.setAdapter(new LudoTmtRoundAdapter(this, this, response.getResponse(), response.getTournamentDetails(), response.isExist(), false));
        } else {
            AppDialog.showStatusFailureDialog(this, response.getMessage());
        }
    }

    @Override
    public void onGetRoundsTournamentFailure(ApiError errorMsg) {
        hideProgress();

    }

    @Override
    public void onItemClick(int pos) {
        launchUnityWithData(matchDetailsResponse.getId(), matchDetailsResponse.getEntryFees(), matchDetailsResponse.getId(), Double.parseDouble(matchDetailsResponse.getPrize().toString()), "", "", tournamentId);
    }

    private void launchUnityWithData(String cId, double Amount, String mContestCode, double mWinAmount, String mRandomName, String mRandomDp, String tournamentId) {
        String mAmount = String.valueOf(Amount);
        String mWAmount = String.valueOf(mWinAmount);
        if (launchIntent != null) {
            launchIntent.putExtra("userToken", mAppPreference.getSessionToken());
            launchIntent.putExtra("contestId", cId);
            launchIntent.putExtra("ka_version", AppUtilityMethods.getVersion());
            launchIntent.putExtra("playerId", mAppPreference.getProfileData().getId());
            launchIntent.putExtra("amount", mAmount);
            launchIntent.putExtra("contestCode", mContestCode);
            launchIntent.putExtra("winAmount", mWAmount);
            launchIntent.putExtra("randomName", mRandomName);
            launchIntent.putExtra("randomPhoto", mRandomDp);
            launchIntent.putExtra("is_tournament", "true");
            launchIntent.putExtra("tournament_id", tournamentId);
            startActivity(launchIntent);
//            isSuccessfulGameOpen = true;
        }

    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ludoadda_download_popup);
        TextView tv = dialog.findViewById(R.id.textView9);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_download);
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
        ivCross.setOnClickListener(view -> {
            dialog.dismiss();
        });
        iv_playstore.setOnClickListener(view -> {
            if (listener != null) {
                progressBar.setVisibility(View.VISIBLE);
                listener.onDownloadVersion();
                iv_playstore.setVisibility(View.GONE);
                ivCross.setVisibility(View.GONE);
            }
        });
        dialog.show();
        return dialog;
    }

    private final IOnVesrionDownloadListener mOnVersionListener = new IOnVesrionDownloadListener() {
        @Override
        public void onDownloadVersion() {
            if (mLink != null && !mLink.isEmpty()) {
                new DownloadApk(mOnFileDownloadedListener).execute(mLink);
            }

        }
    };

    private final IOnFileDownloadedListener mOnFileDownloadedListener = new IOnFileDownloadedListener() {
        @Override
        public void onFileDownloaded(String filePath) {
            AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
            ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
            TextView tv = mVersionDialog.findViewById(R.id.textView9);
            tv.setText("Hey You have Successfully downloaded the Ludo Adda game, Now please click on install button to continue.");
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText("Install Now");
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("LudoDownload", true);
                } catch (Exception e) {
                    Toast.makeText(LudoTmtTounamentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_download);
                progressBar.setProgress(progress);

            }
        }
    };

    private void installApk(String filePath) {
        if (Build.VERSION.SDK_INT >= 26 && !LudoTmtTounamentActivity.this.getPackageManager().canRequestPackageInstalls()) {
            startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:com.khiladiadda")));
            mIsRequestingAppInstallPermission = true;
            mFilePath = filePath;
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(new File(filePath));
        } else {
            uri = FileProvider.getUriForFile(LudoTmtTounamentActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mFilePath = null;
        mIsRequestingAppInstallPermission = false;
    }

    private void getVersion() {
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.LudoAddaPackageName, 0);
            mVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showConfirmation(Double amt) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_battle);
        AppCompatButton okBTN = dialog.findViewById(R.id.btn_send);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);
        EditText amount = dialog.findViewById(R.id.et_amount);
        TextView mEstimatedProfitTV = dialog.findViewById(R.id.tv_estimated_winning);
        TextView mRechargeTV = dialog.findViewById(R.id.tv_recharge);
        TextView mTotalWalletBalanceTV = dialog.findViewById(R.id.tv_total_wallet_balance);
        TextView mDepositWalletTV = dialog.findViewById(R.id.tv_wallet_entry);
        TextView mEntryFeeTV = dialog.findViewById(R.id.tv_entry_fee);
        TextView hintTV = dialog.findViewById(R.id.tv_hint);
        mEntryFeeTV.setText(String.valueOf(amt));
        TextView heading = dialog.findViewById(R.id.tv_heading);
        heading.setText("Battle Amount");
        hintTV.setText(R.string.amout_cannotbechanged);
        LinearLayout hide = dialog.findViewById(R.id.ll_hide);
        hide.setVisibility(View.GONE);

        String currentString = String.valueOf(amt);
        String[] parts = currentString.split(Pattern.quote("."));
        String finAmount = parts[0];

        amount.setText(String.valueOf(finAmount));

        mTotalWalletBalanceTV.setText(new DecimalFormat("##.##").format(mTotalWalletBal));
        mDepositWalletTV.setText(new DecimalFormat("##.##").format(mDepositWinWallet));
        amount.setEnabled(false);
        double entry = (amt * 2);
        double earning = entry - (entry / 10);
        mEstimatedProfitTV.setText(String.valueOf(earning));
        okBTN.setOnClickListener(arg0 -> {
            if (amt <= mDepositWinWallet) {
                callJoinApi();
                dialog.dismiss();
            } else {
                mRechargeTV.setVisibility(View.VISIBLE);
                AppUtilityMethods.showRechargeMsghth(this, AppConstant.INSUFFICIENT_WALLET_RECHARGE);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    //NOTIFICATION
    private BroadcastReceiver mLudoTmtNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mFrom = intent.getStringExtra(AppConstant.FROM);
            if (mFrom.equalsIgnoreCase(AppConstant.LUDOTMT_OPP_JOINED)) {
                matchRoundsRv.setAdapter(new LudoTmtRoundAdapter(context, onClickListener, mDataResponse.getResponse(), mDataResponse.getTournamentDetails(), mDataResponse.isExist(), false));
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsRequestingAppInstallPermission) {
            installApk(mFilePath);
        }
    }
}