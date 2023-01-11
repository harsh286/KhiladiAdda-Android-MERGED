package com.khiladiadda.wordsearch.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class WordSearchRulesActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_rules)
    TextView mRulesTv;
    @BindView(R.id.tv_name)
    TextView mNameTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_word_search_rules;

    }

    @Override
    protected void initViews() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mNameTv.setText("Rules");
        String rules = "1. Ranks will be decided and calculated according to the highest score and the lowest time span.\n\n" +
                "2. Please check your updated rank at the end of the tournament.\n\n" +
                "3. All prizes will be credited to your Khiladi Adda Winning Wallet once the tournament ends.\n\n" +
                "4. For all queries, you may contact us in our Help section.\n\n" +
                "5. If the maximum number of entries is not fully filled by the quiz’s end time, the prize pool will automatically shrink according to the number of entries.\n\n" +
                "6. Maximum 3 Attempts - The user can play one tournament a maximum of three times. The entry Fee is chargeable for only the first attempt\n\n" +
                "7. The result of the last attempt will be considered as the final result.\n\n" +
                "8. Please play all tournaments with your full honesty if anyone is found guilty his/her account may be terminated.\n\n"+
                "9. Ranks will be calculated according to the highest score and the fastest time span";

        mRulesTv.setText(rules);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}