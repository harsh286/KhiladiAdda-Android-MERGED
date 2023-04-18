package com.khiladiadda.callbreak;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.callbreak.adapter.CallBreakAdapter;
import com.khiladiadda.callbreak.adapter.CallBreakHistoryAdapter;
import com.khiladiadda.callbreak.interfaces.ICallBreakPresenter;
import com.khiladiadda.callbreak.interfaces.ICallBreakView;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.CallBreakDetails;
import com.khiladiadda.network.model.response.CallBreakHistoryPlayerResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CBHistoryActivity extends BaseActivity implements ICallBreakView, IOnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.tv_help_video)
    TextView mHelpVideoTV;
    @BindView(R.id.rv_callbreak)
    RecyclerView mCallBreakRV;
    @BindView(R.id.tv_error)
    TextView mErrorTv;
    private CallBreakHistoryAdapter mAdapter;
    private List<CallBreakDetails> mList;
    private ICallBreakPresenter mPresenter;
    private List<CallBreakHistoryPlayerResponse> playersValue;

    @Override
    protected int getContentView() {
        return R.layout.activity_cbhistory;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_history);
        mBackIV.setOnClickListener(this);
        mHelpVideoTV.setVisibility(View.GONE);
    }

    @Override
    protected void initVariables() {
        mPresenter = new CallBreakPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new CallBreakHistoryAdapter(this, mList, this);
        mCallBreakRV.setLayoutManager(new LinearLayoutManager(this));
        mCallBreakRV.setAdapter(mAdapter);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getCallBreakHistory();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    @Override
    public void onGetContestSuccess(CallBreakResponse responseModel) {

    }

    @Override
    public void onGetContestFailure(ApiError error) {

    }

    @Override
    public void onGetContestJoinSuccess(CallBreakJoinMainResponse responseModel) {

    }

    @Override
    public void onGetContestJoinFailure(ApiError error) {

    }

    @Override
    public void onGetHistorySuccess(CallBreakResponse responseModel) {
        if (responseModel.isStatus()) {
            mList.clear();
            if (responseModel.getResponse().size() != 0) {
                mList.addAll(responseModel.getResponse());
                mAdapter.notifyDataSetChanged();
                mErrorTv.setVisibility(GONE);
            } else {
                mErrorTv.setVisibility(View.VISIBLE);
            }
            hideProgress();
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onGetHistoryFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetHistoryRankSuccess(CbHistoryRankMainResponse responseModel) {

    }

    @Override
    public void onGetHistoryRankFailure(ApiError error) {

    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent i = new Intent(this, CBScoreActivity.class);
        playersValue = mList.get(position).getPlayers();
        i.putExtra(AppConstant.ID, mList.get(position).getId());
        i.putParcelableArrayListExtra("player_list", (ArrayList<? extends Parcelable>) playersValue);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}