package com.khiladiadda.wordsearch.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesResponse;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesMainResponse;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wordsearch.adapter.WordSearchQuizAdapter;
import com.khiladiadda.wordsearch.adapter.WordSearchTrendingQuizAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchCategoriesPresenter;
import com.khiladiadda.wordsearch.ip.WordSearchMyQuizzesPresenter;
import com.khiladiadda.wordsearch.listener.IOnClickListener;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryQuizView;
import com.khiladiadda.wordsearch.listener.IWordSearchMyQuizzesPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchMyQuizzesView;

import java.util.List;

import butterknife.BindView;

public class WordSearchCategoryListActivity extends BaseActivity implements IOnClickListener, IWordSearchCategoryQuizView, IWordSearchMyQuizzesView {

    @BindView(R.id.rv_quizzes)
    RecyclerView mQuizzesRv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.mcv_quizes)
    MaterialCardView mQuizzesMcv;
    @BindView(R.id.cl_toolbars)
    ConstraintLayout mToolBarCl;
    @BindView(R.id.tv_no_data)
    TextView mNoDataTv;
    @BindView(R.id.tv_no_data_other)
    TextView mNoDataOtherTv;
    @BindView(R.id.tv_trending)
    TextView mTrendingTv;
    @BindView(R.id.tv_categories)
    TextView mCategoriesTv;
    private String mFrom;
    private IWordSearchCategoryPresenter mCategoryPresenter;
    private IWordSearchMyQuizzesPresenter mMyQuizzesPresenter;
    private List<WordSearchCategoriesQuizzesResponse> mCategoriesQuizzesResponseList;
    private List<WordSearchMyQuizzesResponse> mMyQuizzesMainResponseList;
    private String mQuizId, mColorName = "D25656";
    private String mCategoryName;

    @Override
    protected int getContentView() {
        return R.layout.activity_word_search_quiz;
    }

    @Override
    protected void initViews() {
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
        mQuizId = getIntent().getStringExtra(AppConstant.WORD_SEARCH_QUIZ_ID);
        mCategoryName = getIntent().getStringExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME);
        mColorName = getIntent().getStringExtra(AppConstant.WORD_SEARCH_COLOR_NAME);
        mCategoryPresenter = new WordSearchCategoriesPresenter(this, mQuizId);
        mMyQuizzesPresenter = new WordSearchMyQuizzesPresenter(this);
        setupRecycler();
        // mNameTv.setText(R.string.text_my_tournaments);
        changeColor();
        setupUi();
    }

    private void changeColor() {
        if (mColorName != null) {
            mToolBarCl.setBackgroundColor(Color.parseColor("#" + mColorName));
            mNameTv.setText(R.string.text_my_tournaments);
        } else {
            mNameTv.setText("All " + mCategoryName + " Tournaments");
        }
    }

    @Override
    protected void initVariables() {
        mBackIv.setOnClickListener(this);
        mQuizzesMcv.setOnClickListener(this);
        mTrendingTv.setOnClickListener(this);
        mCategoriesTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        } else if (view.getId() == R.id.mcv_quizes) {
            finish();
        } else if (view.getId() == R.id.tv_trending) {
            startActivity(new Intent(this, WordSearchMainActivity.class));
            finish();
        } else if (view.getId() == R.id.tv_categories) {
            Intent intent3 = new Intent(this, WordSearchCategoriesActivity.class);
            startActivity(intent3);
            finish();
        }
    }

    private void setupUi() {
//        if (mFrom.equals(AppConstant.ALL_QUIZZES)) {
//        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            if (mFrom.equals(AppConstant.ALL_QUIZZES)) {
                mMyQuizzesPresenter.getMyQuizzes();
            } else {
                mCategoryPresenter.getCategoriesQuiz();
            }
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecycler() {
        mQuizzesRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onWordSearchQuizComplete(WordSearchCategoriesQuizzesMainResponse responseModel) {
        hideProgress();
        if (!responseModel.getResponse().isEmpty()) {
            mNoDataOtherTv.setVisibility(View.GONE);
            mCategoriesQuizzesResponseList = responseModel.getResponse();
            mQuizzesRv.setAdapter(new WordSearchQuizAdapter(this, mCategoriesQuizzesResponseList, mCategoryName));
        } else {
            mNoDataOtherTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onWordSearchQuizFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onItemClick(int pos) {
        if (mFrom.equals(AppConstant.ALL_QUIZZES)) {
            if (mMyQuizzesMainResponseList.get(pos).getQuiz().get(0).getQuizStatus() != 0) {
                openLiveLeaderboard(pos, mMyQuizzesMainResponseList.get(pos).getQuizId());
            } else {
                Intent intent = new Intent(this, WordSearchDetailsActivity.class);
                intent.putExtra(AppConstant.WORD_SEARCH_TYPE, 3);
                intent.putExtra(AppConstant.WORD_SEARCH_MY_QUIZZES, mMyQuizzesMainResponseList.get(pos));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(this, WordSearchDetailsActivity.class);
            intent.putExtra(AppConstant.WORD_SEARCH_TYPE, 0);
            intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_QUIZZES, mCategoriesQuizzesResponseList.get(pos));
            startActivity(intent);
        }
    }

    private void openLiveLeaderboard(int position, String id) {
        Intent intent;
        if (mMyQuizzesMainResponseList.get(position).getQuiz().get(0).getQuizStatus() == 0) {
            intent = new Intent(this, WordSearchLeaderBoardActivity.class);
            intent.putExtra(AppConstant.FROM, true);
        } else {
            intent = new Intent(this, FinalLeaderBoardActivity.class);
        }
        intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onWordSearchMyQuizzesComplete(WordSearchMyQuizzesMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mMyQuizzesMainResponseList = responseModel.getResponse();
            if (!responseModel.getResponse().isEmpty()) {
                mNoDataTv.setVisibility(View.GONE);
                mQuizzesRv.setAdapter(new WordSearchTrendingQuizAdapter(this, mMyQuizzesMainResponseList, mColorName, mCategoryName));
            } else {
                /*Remove this line*/
//                mQuizzesRv.setAdapter(new WordSearchTrendingQuizAdapter(this, mMyQuizzesMainResponseList, mColorName));
                mNoDataTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onWordSearchMyQuizzesFailure(ApiError error) {
        hideProgress();
    }
}