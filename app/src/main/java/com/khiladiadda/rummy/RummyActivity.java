package com.khiladiadda.rummy;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.CallBreakDialog;
import com.khiladiadda.dialogs.RummyDialog;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.RummyDetails;
import com.khiladiadda.network.model.response.RummyPayload;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.rummy.adapter.RummyAdapter;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RummyActivity extends BaseActivity implements IRummyView, IOnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_points)
    TextView mPointsTV;
    @BindView(R.id.tv_pool)
    TextView mPoolTV;
    @BindView(R.id.tv_deal)
    TextView mDealTV;
    @BindView(R.id.tv_one)
    TextView mOneTV;
    @BindView(R.id.tv_two)
    TextView mTwoTV;
    @BindView(R.id.tv_three)
    TextView mThreeTV;
    @BindView(R.id.rv_rummy)
    RecyclerView mRummyRV;
    @BindView(R.id.ll_mode_option)
    LinearLayout mModeOptionLL;
    @BindView(R.id.iv_announcement)
    ImageView mAnnouncementIV;
    private RummyAdapter mAdapter;
    private List<RummyDetails> mList;
    private IRummyPresenter mPresenter;
    private String mType, mRefreshToken = "";
    private int mMode = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_rummy;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText("Rummy Adda");
        mBackIV.setOnClickListener(this);
        mPointsTV.setOnClickListener(this);
        mPoolTV.setOnClickListener(this);
        mDealTV.setOnClickListener(this);
        mOneTV.setOnClickListener(this);
        mTwoTV.setOnClickListener(this);
        mThreeTV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new RummyPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new RummyAdapter(this, mList);
        mRummyRV.setLayoutManager(new LinearLayoutManager(this));
        mRummyRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        setMode(14);
        setModeOptionOne();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_points:
                mMode = 1;
                mModeOptionLL.setVisibility(View.VISIBLE);
                mThreeTV.setVisibility(View.GONE);
                setMode(14);
                setModeOptionOne();
                break;
            case R.id.tv_pool:
                mMode = 2;
                mModeOptionLL.setVisibility(View.VISIBLE);
                mThreeTV.setVisibility(View.VISIBLE);
                setMode(24);
                setModeOptionTwo();
                break;
            case R.id.tv_deal:
                mMode = 3;
                mModeOptionLL.setVisibility(View.GONE);
                setMode(34);
                break;
            case R.id.tv_one:
                if (mMode == 1) {
                    setMode(14);
                } else if (mMode == 2) {
                    setMode(24);
                } else
                    setMode(34);
                break;
            case R.id.tv_two:
                if (mMode == 1) {
                    setMode(15);
                } else if (mMode == 2) {
                    setMode(25);
                } else
                    setMode(34);
                break;
            case R.id.tv_three:
                if (mMode == 1) {
                    setMode(16);
                } else if (mMode == 2) {
                    setMode(26);
                } else
                    setMode(34);
                break;
        }
    }

    private void setMode(int mode) {
        mPointsTV.setSelected(false);
        mPoolTV.setSelected(false);
        mDealTV.setSelected(false);
        mOneTV.setTextColor(getResources().getColor(R.color.white));
        mTwoTV.setTextColor(getResources().getColor(R.color.white));
        mThreeTV.setTextColor(getResources().getColor(R.color.white));
        switch (mode) {
            case 14:
                mType = "pt_S13";
                setPoints();
                setOne();
                break;
            case 15:
                mType = "mtwc_S13";
                setPoints();
                setTwo();
                break;
            case 16:
                mType = "pool51_S13";
                setPoints();
                setThree();
                break;
            case 24:
                mType = "pool101_S13";
                setPool();
                setOne();
                break;
            case 25:
                mType = "pool201_S13";
                setPool();
                setTwo();
                break;
            case 26:
                mType = "pool51_S13";
                setPool();
                setThree();
                break;
            case 34:
                mType = "deal_S13";
                setDeal();
                setOne();
                break;
//            case 35:
//                mType = "deal_S13";
//                setDeal();
//                setTwo();
//                break;
//            case 36:
//                mType = "deal_S13";
//                setDeal();
//                setThree();
//                break;
        }
    }

    private void setModeOptionOne() {
        mOneTV.setText("13 Cards");
        mTwoTV.setText("2 Jokers");
        mThreeTV.setVisibility(View.GONE);
    }

    private void setModeOptionTwo() {
        mOneTV.setText("Pool 51");
        mTwoTV.setText("Pool 101");
        mThreeTV.setVisibility(View.VISIBLE);
    }

    private void setOne() {
        mOneTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }

    private void setTwo() {
        mTwoTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }

    private void setThree() {
        mThreeTV.setTextColor(getResources().getColor(R.color.black));
        getData();
        getRefreshToken();
    }

    private void setDeal() {
        mDealTV.setSelected(true);
        mDealTV.setTextColor(getResources().getColor(R.color.battle_red));
    }

    private void setPool() {
        mPoolTV.setSelected(true);
        mPoolTV.setTextColor(getResources().getColor(R.color.battle_red));
    }

    private void setPoints() {
        mPointsTV.setSelected(true);
        mPointsTV.setTextColor(getResources().getColor(R.color.battle_red));
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRummyList(mType);
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getRefreshToken() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRummyRefreshToken();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestSuccess(RummyResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mList.clear();
            mList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
            if (responseModel.getBanner().size() > 0)
                mAnnouncementIV.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(responseModel.getBanner().get(0).getImg())) {
                Glide.with(mAnnouncementIV.getContext()).load(responseModel.getBanner().get(0).getImg()).transform(new CenterCrop(), new RoundedCorners(20)).into(mAnnouncementIV);
            } else {
                mAnnouncementIV.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetContestFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetContestRefreshTokenSuccess(RummyRefreshTokenMainResponse responseModel) {
        if (responseModel.isStatus()) {
            mRefreshToken = responseModel.getResponse();
        } else {
            Toast.makeText(this, "" + responseModel.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContestRefreshTokenFailure(ApiError error) {

    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        openBottomDialog(position);
    }

    private void openBottomDialog(int position) {
        Coins mCoins = mAppPreference.getProfileData().getCoins();
        double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        double mDepWinAmount = mCoins.getDeposit() + mCoins.getWinning();

        RummyDialog addExpenseDialog = new RummyDialog(this, String.valueOf(mList.get(position).getEntryFee()), String.valueOf(mTotalWalletBal), String.valueOf(mDepWinAmount), mList.get(position).getCardId(), AppSharedPreference.getInstance().getSessionToken(), mRefreshToken);
        addExpenseDialog.setCancelable(false);
        addExpenseDialog.setCanceledOnTouchOutside(false);
        addExpenseDialog.show();
    }
}