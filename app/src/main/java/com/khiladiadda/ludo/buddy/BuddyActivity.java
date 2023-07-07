package com.khiladiadda.ludo.buddy;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.ludo.buddy.adapter.LudoBuddyAdapter;
import com.khiladiadda.ludo.buddy.interfaces.ILudoBuddyPresenter;
import com.khiladiadda.ludo.buddy.interfaces.ILudoBuddyView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoBuddyChallengeRequest;
import com.khiladiadda.network.model.response.BuddyDetails;
import com.khiladiadda.network.model.response.BuddyResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BuddyActivity extends BaseActivity implements ILudoBuddyView, LudoBuddyAdapter.IOnItemChildClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.rv_buddy)
    RecyclerView mBuddyRV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.action_bar)
    RelativeLayout mActionBarRL;

    private ILudoBuddyPresenter mPresenter;
    private LudoBuddyAdapter mAdapter;
    private List<BuddyDetails> mList;
    private int mContestType;
    private int mFrom;

    @Override
    protected int getContentView() {
        return R.layout.activity_buddy;
    }

    @Override
    protected void initViews() {
        mContestType = getIntent().getIntExtra(AppConstant.CONTEST_TYPE, 0);
        if (mContestType == AppConstant.TYPE_LUDO) {
            mActivityNameTV.setText(R.string.ludo_king);
        } else {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.ludo_uni_actionbar));
            mActionBarRL.setBackgroundResource(R.color.ludo_uni_actionbar);
            mActivityNameTV.setText(R.string.text_ludo_adda);
        }
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mFrom = getIntent().getIntExtra(AppConstant.FROM, 0);
    }

    @Override
    protected void initVariables() {
        mPresenter = new LudoBuddyPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new LudoBuddyAdapter(this, mList);
        mBuddyRV.setLayoutManager(new LinearLayoutManager(this));
        mBuddyRV.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        showProgress(getString(R.string.text_waiting));
        if (mContestType == AppConstant.TYPE_LUDO) {
            mPresenter.getBuddyList(String.valueOf(mContestType));
        } else {
            mPresenter.getBuddyListUniverse(mFrom);
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    @Override
    public void onBuddyListSuccess(BuddyResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mNoDataTV.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBuddyListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onChallengeSuccess(BaseResponse response) {
        hideProgress();
        AppUtilityMethods.showMsg(this, response.getMessage(), false);
    }

    @Override
    public void onChallengeFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLUBuddyListSuccess(BuddyResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus() && responseModel.getResponse().size() > 0) {
            mNoDataTV.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(responseModel.getResponse());
            mAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLUBuddyListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onRequestClicked(int position) {
        List<String> users = new ArrayList<String>();
        users.add(mList.get(position).getId());
        LudoBuddyChallengeRequest request = new LudoBuddyChallengeRequest();
        if (mContestType == AppConstant.TYPE_LUDO) {
            request.setTitle(mList.get(position).getName());
            request.setBody(getString(R.string.text_ludo_body));
        } else {
            request.setTitle(mList.get(position).getName());
            request.setBody(getString(R.string.text_snake_body));
        }
        request.setUsers(users);
        showProgress(getString(R.string.text_waiting));
        mPresenter.sendChallengeRequest(request);
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}