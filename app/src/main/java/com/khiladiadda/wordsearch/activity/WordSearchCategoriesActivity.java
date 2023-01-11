package com.khiladiadda.wordsearch.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryMainResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wordsearch.adapter.WordSearchCategoriesAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchTournamentCategoriesPresenter;
import com.khiladiadda.wordsearch.listener.IOnClickListener;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoriesPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryView;

import butterknife.BindView;

public class WordSearchCategoriesActivity extends BaseActivity implements IWordSearchCategoryView, IOnClickListener {

    @BindView(R.id.rv_categories_quizes)
    RecyclerView mCategoriesRv;
    @BindView(R.id.tv_trending)
    TextView mTrendingTv;
    @BindView(R.id.tv_tournaments)
    TextView mTournamentsTv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;

    private IWordSearchCategoriesPresenter mPresenter;
    private WordSearchCategoryMainResponse mainResponse;


    @Override
    protected int getContentView() {
        return R.layout.activity_word_search_categories;

    }

    @Override
    protected void initViews() {
        mPresenter = new WordSearchTournamentCategoriesPresenter(this);
        mCategoriesRv.setLayoutManager(new GridLayoutManager(this, 2));
        getData();
    }

    @Override
    protected void initVariables() {
        mTrendingTv.setOnClickListener(this);
        mTournamentsTv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.tv_trending) {
            Intent intent = new Intent(this, WordSearchMainActivity.class);
            startActivity(intent);
            finish();
        } else if (p0.getId() == R.id.tv_tournaments) {
            Intent intent2 = new Intent(this, WordSearchQuizActivity.class);
            intent2.putExtra("category_name", "My Quizzes");
            intent2.putExtra(AppConstant.FROM, AppConstant.ALL_QUIZZES);
            startActivity(intent2);
            finish();
        } else if (p0.getId() == R.id.iv_back) {
            startActivity(new Intent(this, WordSearchMainActivity.class));
            finish();
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mPresenter.getCategoriesTournament();
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWordSearchComplete(WordSearchCategoryMainResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus() == true) {
            mainResponse = responseModel;
            mCategoriesRv.setAdapter(new WordSearchCategoriesAdapter(this, responseModel.getResponse(), this));
        } else Toast.makeText(this, "" + responseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWordSearchFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onItemClick(int pos) {
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WordSearchCategoryListActivity.class);
        intent.putExtra(AppConstant.FROM, "");
        intent.putExtra(AppConstant.WORD_SEARCH_CATEGORY_NAME, mainResponse.getResponse().get(pos).getName());
//        intent.putExtra(AppConstant.WORD_SEARCH_COLOR_NAME, mTrendingMainResponse.getCategoryQuiz().get(pos).getColour());
        intent.putExtra(AppConstant.WORD_SEARCH_QUIZ_ID, mainResponse.getResponse().get(pos).getId());
        startActivity(intent);
    }
}