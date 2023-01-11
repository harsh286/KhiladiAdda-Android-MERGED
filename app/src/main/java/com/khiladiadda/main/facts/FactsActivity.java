package com.khiladiadda.main.facts;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.main.facts.adapter.FactsPonitsRVAdapter;
import com.khiladiadda.main.facts.interfaces.IFactsPresenter;
import com.khiladiadda.main.facts.interfaces.IFactsView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.network.model.response.PointDetails;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FactsActivity extends BaseActivity implements IFactsView {

    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_url)
    ImageView mURLIV;
    @BindView(R.id.tv_heading)
    TextView mHeadingTV;
    @BindView(R.id.tv_sub_heading)
    TextView mSubHeadingTV;
    @BindView(R.id.tv_details)
    TextView mDetailsTV;
    @BindView(R.id.iv_wishlist)
    ImageView mWishlistIV;
    @BindView(R.id.iv_bookmark)
    ImageView mBookmarkIV;
    @BindView(R.id.iv_share)
    ImageView mShareIV;
    @BindView(R.id.rv_points)
    RecyclerView mPointsRV;

    private FactsList mList;
    private IFactsPresenter mPresenter;
    private boolean mIsLiked, mIsBookmarked;

    @Override
    protected int getContentView() {
        return R.layout.activity_facts;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_fact);
        setData();
    }

    @Override
    protected void initVariables() {
        mPresenter = new FactsPresenter(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mWishlistIV.setOnClickListener(this);
        mBookmarkIV.setOnClickListener(this);
        mShareIV.setOnClickListener(this);
        mPresenter.getFactDetails(mList.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.iv_wishlist:
                if (mIsLiked) {
                    mIsLiked = false;
                    mWishlistIV.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    mIsLiked = true;
                    mWishlistIV.setImageResource(R.drawable.ic_favorite_red);
                }
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.likeFact(mList.getId());
                break;
            case R.id.iv_bookmark:
                if (mIsBookmarked) {
                    mIsBookmarked = false;
                    mBookmarkIV.setImageResource(R.drawable.ic_bookmark_black);
                } else {
                    mIsBookmarked = true;
                    mBookmarkIV.setImageResource(R.drawable.ic_bookmark_green);
                }
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.bookmarkFact(mList.getId());
                break;
            case R.id.iv_share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, mList.getDetails());
                startActivity(Intent.createChooser(share, "Share Fact"));
                break;
        }
    }

    @Override
    public void onTrendingFactsComplete(FactsResponse responseModel) {
    }

    @Override
    public void onTrendingFactsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFactsComplete(FactsResponse responseModel) {
    }

    @Override
    public void onFactsFailure(ApiError error) {
    }

    @Override
    public void onFactDetailsComplete(FactDetailsResponse responseModel) {
        hideProgress();
    }

    @Override
    public void onFactDetailsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLikeFactComplete(LikeFactResponse responseModel) {
        hideProgress();
    }

    @Override
    public void onLikeFactFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onBookmarkFactComplete(LikeFactResponse responseModel) {
        hideProgress();
    }

    @Override
    public void onBookmarkFactFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFactsFetchedFromDB(List<FactsList> eventList) {
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    private void setData() {
        mList = getIntent().getParcelableExtra(AppConstant.DATA);
        mHeadingTV.setText(mList.getHeading());
        mSubHeadingTV.setText(AppUtilityMethods.getConvertDateFacts(mList.getDate()) + " - " + mList.getSubheading());
        mDetailsTV.setText(mList.getDetails());
        mIsLiked = mList.isLiked();
        mIsBookmarked = mList.isBookmarked();
        if (mIsLiked) {
            mWishlistIV.setImageResource(R.drawable.ic_favorite_red);
        } else {
            mWishlistIV.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        if (mIsBookmarked) {
            mBookmarkIV.setImageResource(R.drawable.ic_bookmark_green);
        } else {
            mBookmarkIV.setImageResource(R.drawable.ic_bookmark_black);
        }

        String imageURL = mList.getImgurl();
        if (imageURL != null) {
            Glide.with(this).load(imageURL).thumbnail(Glide.with(this).load(imageURL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).dontTransform().into(mURLIV);
        }
        List<PointDetails> mPointList = new ArrayList<>();
        mPointList.addAll(mList.getPoints());
        if (mPointList.size() > 0) {
            FactsPonitsRVAdapter mTrendingAdapter = new FactsPonitsRVAdapter(this, mPointList);
            mPointsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mPointsRV.setAdapter(mTrendingAdapter);
        } else {
            mPointsRV.setVisibility(View.GONE);
        }
    }

}