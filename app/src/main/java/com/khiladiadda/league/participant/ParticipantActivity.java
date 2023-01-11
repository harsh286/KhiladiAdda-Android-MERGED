package com.khiladiadda.league.participant;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.league.participant.adapter.FBBattleParticipantAdapter;
import com.khiladiadda.league.participant.adapter.FBParticipantAdapter;
import com.khiladiadda.league.participant.adapter.ParticipantAdapter;
import com.khiladiadda.league.participant.interfaces.IParticipantPresenter;
import com.khiladiadda.league.participant.interfaces.IParticipantView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.FBParticipantData;
import com.khiladiadda.network.model.response.FBParticipantList;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.ParticipantDetails;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.QuizParticipant;
import com.khiladiadda.network.model.response.QuizParticipantResponse;
import com.khiladiadda.network.model.response.TeamResponse;
import com.khiladiadda.quiz.adapters.QuizParticipantAdapter;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.khiladiadda.utility.AppConstant.PAGE_SIZE;

public class ParticipantActivity extends BaseActivity implements IParticipantView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTV;
    @BindView(R.id.rv_participant)
    RecyclerView mRV;

    private LinearLayoutManager mLayoutManager;
    private IParticipantPresenter mPresenter;
    private ParticipantAdapter mLeagueAdapter;
    private List<ParticipantDetails> mLeagueList;
    private QuizParticipantAdapter mQuizAdapter;
    private List<QuizParticipant> mQuizList;
    private boolean isLoading, isLastPage;
    private int mCurrentPage = 0;
    private FBParticipantAdapter mFBGroupParticipantAdapter;
    private List<FBParticipantList> mFBGroupParticipantList;

    @Override
    protected int getContentView() {
        return R.layout.activity_participant;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_view_participant);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new ParticipantPresenter(this);

        mLeagueList = new ArrayList<>();
        mLeagueAdapter = new ParticipantAdapter(this, mLeagueList);
        mLayoutManager = new LinearLayoutManager(this);
        mRV.setLayoutManager(mLayoutManager);
        mRV.setAdapter(mLeagueAdapter);
        mRV.addOnScrollListener(recyclerViewOnScrollListener);

        mQuizList = new ArrayList<>();
        mQuizAdapter = new QuizParticipantAdapter(mQuizList);
        mFBGroupParticipantList = new ArrayList<>();
        mFBGroupParticipantAdapter = new FBParticipantAdapter(this, mFBGroupParticipantList);
        List<FBParticipantData> mFBBattleParticipantList = new ArrayList<>();
        FBBattleParticipantAdapter mFBBattleParticipantAdapter = new FBBattleParticipantAdapter(this, mFBBattleParticipantList);

        getData();
    }

    private void getData() {
        showProgress(getString(R.string.txt_progress_authentication));
        if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_VIEW_QUIZ)) {
            mPresenter.getQuizParticipant(getIntent().getStringExtra(AppConstant.ID), mCurrentPage, AppConstant.PAGE_SIZE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.LEAGUE)) {
            mPresenter.getParticipant(getIntent().getStringExtra(AppConstant.ID));
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_FB_BATTLE)) {
            mPresenter.getFBBattleParticipant(getIntent().getStringExtra(AppConstant.ID), mCurrentPage, AppConstant.PAGE_SIZE);
        } else if (getIntent().getStringExtra(AppConstant.FROM).equalsIgnoreCase(AppConstant.FROM_FB_MATCH)) {
            mPresenter.getFBMatchParticipant(getIntent().getStringExtra(AppConstant.ID), mCurrentPage, AppConstant.PAGE_SIZE);
        } else {
            mPresenter.getFBGroupParticipant(getIntent().getStringExtra(AppConstant.ID), mCurrentPage, AppConstant.PAGE_SIZE, true);
        }
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
        }
    }

    @Override
    public void onParticipantComplete(ParticipantResponse responseModel) {
        hideProgress();
        if (responseModel.getResponse().size() > 0) {
            mLeagueList.clear();
            mLeagueList.addAll(responseModel.getResponse());
            mLeagueAdapter.notifyDataSetChanged();
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onParticipantFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onTeamComplete(TeamResponse responseModel) {
    }

    @Override
    public void onTeamFailure(ApiError error) {
    }

    @Override
    public void onQuizParticipantComplete(QuizParticipantResponse responseModel) {
        hideProgress();
        mRV.setAdapter(mQuizAdapter);
        if (responseModel.getResponse().size() > 0) {
            if (mCurrentPage == 0) {
                mQuizList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mNoDataTV.setVisibility(View.VISIBLE);
            } else if (responseModel.getResponse().size() > 0) {
                mQuizList.addAll(responseModel.getResponse());
                mQuizAdapter.notifyDataSetChanged();
            }
            isLoading = false;
            mCurrentPage++;
            if (responseModel.getResponse().size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onQuizParticipantFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFBGroupParticipantComplete(FBParticipantResponse responseModel) {
        hideProgress();
        mRV.setAdapter(mFBGroupParticipantAdapter);
        if (responseModel.getResponse().size() > 0) {
            if (mCurrentPage == 0) {
                mFBGroupParticipantList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mNoDataTV.setVisibility(View.VISIBLE);
            } else if (responseModel.getResponse().size() > 0) {
                mFBGroupParticipantList.addAll(responseModel.getResponse());
                mFBGroupParticipantAdapter.notifyDataSetChanged();
            }
            isLoading = false;
            mCurrentPage++;
            if (responseModel.getResponse().size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFBGroupParticipantFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFBBattleParticipantComplete(FBParticipantResponse responseModel) {
        hideProgress();
        mRV.setAdapter(mFBGroupParticipantAdapter);
        if (responseModel.getResponse().size() > 0) {
            if (mCurrentPage == 0) {
                mFBGroupParticipantList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mNoDataTV.setVisibility(View.VISIBLE);
            } else if (responseModel.getResponse().size() > 0) {
                mFBGroupParticipantList.addAll(responseModel.getResponse());
                mFBGroupParticipantAdapter.notifyDataSetChanged();
            }
            isLoading = false;
            mCurrentPage++;
            if (responseModel.getResponse().size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }

//        mRV.setAdapter(mFBBattleParticipantAdapter);
//        if (responseModel.getResponse().size() > 0) {
//            if (mCurrentPage == 0) {
//                mFBBattleParticipantList.clear();
//            }
//            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
//                mNoDataTV.setVisibility(View.VISIBLE);
//            } else if (responseModel.getResponse().size() > 0) {
//                mFBBattleParticipantList.addAll(responseModel.getResponse());
//                mFBBattleParticipantAdapter.notifyDataSetChanged();
//            }
//            isLoading = false;
//            mCurrentPage++;
//            if (responseModel.getResponse().size() < PAGE_SIZE) {
//                isLastPage = true;
//            }
//        } else {
//            mNoDataTV.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onFBBattleParticipantFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFBMatchParticipantComplete(FBParticipantResponse responseModel) {
        hideProgress();
        mRV.setAdapter(mFBGroupParticipantAdapter);
        if (responseModel.getResponse().size() > 0) {
            if (mCurrentPage == 0) {
                mFBGroupParticipantList.clear();
            }
            if (mCurrentPage == 0 && responseModel.getResponse().size() <= 0) {
                mNoDataTV.setVisibility(View.VISIBLE);
            } else if (responseModel.getResponse().size() > 0) {
                mFBGroupParticipantList.addAll(responseModel.getResponse());
                mFBGroupParticipantAdapter.notifyDataSetChanged();
            }
            isLoading = false;
            mCurrentPage++;
            if (responseModel.getResponse().size() < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFBMatchParticipantFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        //show bottom progress bar
        isLoading = true;
        getData();
    }

}