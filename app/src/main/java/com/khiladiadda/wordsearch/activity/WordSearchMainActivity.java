package com.khiladiadda.wordsearch.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.interfaces.IOnVesrionDownloadListener;
import com.khiladiadda.interfaces.IOnFileDownloadedListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchTrendingMainResponse;
import com.khiladiadda.network.model.response.WordSearchTrendingResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.DownloadApk;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.providers.GenericFileProvider;
import com.khiladiadda.wordsearch.adapter.WordSearchMainAdapter;
import com.khiladiadda.wordsearch.adapter.WordSearchViewPagerAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchPresenter;
import com.khiladiadda.wordsearch.listener.IOnViewAllClickListener;
import com.khiladiadda.wordsearch.listener.IWordSearchMainPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchMainView;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.io.File;

import butterknife.BindView;

public class WordSearchMainActivity extends BaseActivity implements IOnViewAllClickListener, IWordSearchMainView {

    //    @BindView(R.id.rv_main_quizes)
//    RecyclerView mMainQuizesRv;
    @BindView(R.id.rv_trending_quizes)
    RecyclerView mTrendingQuiz;
    @BindView(R.id.mcv_quizes)
    MaterialCardView mQuizzesMcv;
    @BindView(R.id.tv_all_quizzes)
    TextView mAllQuizzesTv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTv;
    @BindView(R.id.mcv_download)
    MaterialCardView mDownloadMVC;
    @BindView(R.id.cl_toolbars)
    ConstraintLayout mToolbar;
    @BindView(R.id.tv_download)
    TextView mDownloadTV;
    @BindView(R.id.tv_tournaments)
    TextView mTournamentsTv;
    @BindView(R.id.tv_categories)
    TextView mCategoriesTv;
    //    @BindView(R.id.tv_categories)
//    TextView mCategorgiesTV;
    private boolean mIsRequestingAppInstallPermission;
    private String mFilePath;
    private double Amount;
    private Dialog mVersionDialog;
    private String mLink;
    private String mVersion;
    private String mCurrentVersion;
    private IWordSearchMainPresenter mPresenter;
    private WordSearchTrendingResponse mTrendingMainResponse;
    private Intent launchIntent;


    @Override
    protected int getContentView() {
        return R.layout.activity_word_search_main;
    }

    @Override
    protected void initViews() {
        mPresenter = new WordSearchPresenter(this);
        mAllQuizzesTv.setText("My Tournaments");
        mAppPreference.setBoolean("WSDownload", false);
        setupRecycler();
        launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.WordSearchPackageName);
        if (launchIntent != null) getVersion();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_banner_wordsearch);
        WordSearchViewPagerAdapter wordSearchViewPagerAdapter = new WordSearchViewPagerAdapter(this);
        viewPager.setAdapter(wordSearchViewPagerAdapter);
        viewPager.setCurrentItem(wordSearchViewPagerAdapter.getCount() - 1);
    }

    private void setupRecycler() {
        mToolbar.setBackgroundColor(Color.parseColor("#DA0000"));
        //For Trending
        mTrendingQuiz.setLayoutManager(new LinearLayoutManager(this));
        mTrendingQuiz.setVisibility(View.VISIBLE);

        //For Category
//        mMainQuizesRv.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void initVariables() {
        mQuizzesMcv.setVisibility(View.GONE);
//        mQuizzesMcv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mDownloadMVC.setOnClickListener(this);
        mTournamentsTv.setOnClickListener(this);
        mCategoriesTv.setOnClickListener(this);
        getData();
    }


    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mPresenter.getTrendingQuiz();
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mcv_quizes:
                Intent intent = new Intent(WordSearchMainActivity.this, WordSearchQuizActivity.class);
                intent.putExtra("category_name", "My Quizzes");
                intent.putExtra(AppConstant.FROM, AppConstant.ALL_QUIZZES);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.mcv_download:
                mVersionDialog = downloadOptionPopup(this, mOnVersionListener);
                break;
            case R.id.tv_tournaments:
                Intent intent2 = new Intent(WordSearchMainActivity.this, WordSearchQuizActivity.class);
                intent2.putExtra("category_name", "My Quizzes");
                intent2.putExtra(AppConstant.FROM, AppConstant.ALL_QUIZZES);
                startActivity(intent2);
                finish();
                break;
            case R.id.tv_categories:
                Intent intent3 = new Intent(WordSearchMainActivity.this, WordSearchCategoriesActivity.class);
                startActivity(intent3);
                finish();
                break;

        }
    }

    @Override
    public void onWordSearchQuizComplete(WordSearchTrendingMainResponse responseModel) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> hideProgress(), 500);
        if (responseModel.getResponse().getCategoryQuiz().isEmpty() &&
                responseModel.getResponse().getTrendingQuiz().isEmpty()) {
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.GONE);
//            mCategorgiesTV.setVisibility(View.VISIBLE);
        }
        mLink = responseModel.getResponse().getWSLink();
        mCurrentVersion = responseModel.getResponse().getApk_version();
        mAppPreference.setString(AppConstant.WS_LINK, mLink);
        mAppPreference.setString(AppConstant.WS_VERSION, mCurrentVersion);
        mTrendingMainResponse = responseModel.getResponse();
        mTrendingQuiz.setAdapter(new WordSearchMainAdapter(this, this, responseModel.getResponse().getTrendingQuiz(), null));
//        mMainQuizesRv.setAdapter(new WordSearchMainAdapter(this, this, null, responseModel.getResponse().getCategoryQuiz()));
        if (launchIntent != null) {
            if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
//                mQuizzesMcv.setVisibility(View.VISIBLE);
                mDownloadMVC.setVisibility(View.GONE);
                Properties properties = new Properties();
                properties.addAttribute("WordSearchVersion", mCurrentVersion);
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, "WordSearch", properties);
            } else {
                mDownloadTV.setText("Update");
                mQuizzesMcv.setVisibility(View.GONE);
                mDownloadMVC.setVisibility(View.VISIBLE);
            }
        } else {
//            mQuizzesMcv.setVisibility(View.GONE);
            mDownloadMVC.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onWordSearchQuizFailure(ApiError error) {
        hideProgress();
    }


    @Override
    public void onViewAllItemClick(int pos) {
//        Intent intent = new Intent(WordSearchMainActivity.this, WordSearchQuizActivity.class);
//        intent.putExtra(AppConstant.FROM, "");
//        intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME, mTrendingMainResponse.getCategoryQuiz().get(pos).getName());
//        intent.putExtra(AppConstant.WORD_SEARCH_COLOR_NAME, mTrendingMainResponse.getCategoryQuiz().get(pos).getColour());
//        intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mTrendingMainResponse.getCategoryQuiz().get(pos).getId());
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAppPreference.getBoolean("WSDownload", false)) {
            try {
                Intent launchIntent = getPackageManager().getLeanbackLaunchIntentForPackage(AppConstant.WordSearchPackageName);
                if (launchIntent != null) {
                    if (mVersion.equalsIgnoreCase(mCurrentVersion)) {
                        mDownloadTV.setText("Update");
                        mQuizzesMcv.setVisibility(View.GONE);
                        mDownloadMVC.setVisibility(View.VISIBLE);
                    } else {
                        finish();
                        startActivity(new Intent(this, WordSearchMainActivity.class));
                    }
                }
            } catch (Exception e) {
                finish();
                startActivity(new Intent(this, WordSearchMainActivity.class));

            }
        }
    }

    private Dialog downloadOptionPopup(final Context activity, final IOnVesrionDownloadListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wordsearch_download_popup);
        TextView tv = dialog.findViewById(R.id.textView9);
        ProgressBar progressBar = dialog.findViewById(R.id.pb_apk_download);
        AppCompatButton iv_playstore = dialog.findViewById(R.id.iv_playstore);
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
            AppCompatButton iv_playstore = mVersionDialog.findViewById(R.id.iv_playstore);
            ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
            TextView tv = mVersionDialog.findViewById(R.id.textView9);
            tv.setText("Hey You have Successfully downloaded the wordsearch game, Now please click on install button to continue.");
            progressBar.setVisibility(View.GONE);
            iv_playstore.setVisibility(View.VISIBLE);
            iv_playstore.setText("Install Now");
            iv_playstore.setOnClickListener(view -> {
                try {
                    installApk(filePath);
                    mVersionDialog.dismiss();
                    mVersionDialog = null;
                    mAppPreference.setBoolean("WSDownload", true);
                } catch (Exception e) {
                    Toast.makeText(WordSearchMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

        @Override
        public void onFileProgressUpdate(int progress, int fileLength) {
            if (mVersionDialog != null) {
                ProgressBar progressBar = mVersionDialog.findViewById(R.id.pb_apk_download);
                TextView tv = mVersionDialog.findViewById(R.id.textView9);
                progressBar.setProgress(progress);


            }
        }
    };

    private void installApk(String filePath) {
        if (Build.VERSION.SDK_INT >= 26 && !WordSearchMainActivity.this.getPackageManager().canRequestPackageInstalls()) {
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
            uri = FileProvider.getUriForFile(WordSearchMainActivity.this, GenericFileProvider.AUTHORITY, new File(filePath));
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
            PackageInfo pInfo = pm.getPackageInfo(AppConstant.WordSearchPackageName, 0);
            mVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}