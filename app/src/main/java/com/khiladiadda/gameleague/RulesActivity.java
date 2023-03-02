package com.khiladiadda.gameleague;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class RulesActivity extends BaseActivity {
    @BindView(R.id.tv_rules)
    TextView mRulesTv;
    @BindView(R.id.iv_title_name)
    TextView mNameTv;
    @BindView(R.id.iv_back_arroww)
    ImageView mIvBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_rules;
    }

    @Override
    protected void initViews() {
        mNameTv.setText(getString(R.string.rules));
        mIvBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mNameTv.setText(getString(R.string.rules));
        String rules = "1. Ranks will be calculated according to the highest score and the fastest time span amongst the participants.\n\n" +
                "2. Please check your updated rank at the end of the contest.\n\n" +
                "3. All prizes will be credited to your KhiladiAdda account as Coins only after the tournament ends for all participants and the result is declared.\n\n" +
                "4. If the number of entries are not filled by the end time, the end time will be rescheduled for the next cycle and the result will be declared after rescheduled end time.\n\n" +
                "5. Please play all tournaments with your full honesty. If anyone is found guilty, his/her account may be terminated.\n\n" +
                "6. For any queries, you may contact us via email support or Whatsapp support in our help center.\n\n" +
                "7. The prizes may change without prior notice.";

        mRulesTv.setText(rules);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_arroww) {
            finish();
        }
    }
}