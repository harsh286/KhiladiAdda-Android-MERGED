package com.khiladiadda.utility;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.battle.BattleActivity;
import com.khiladiadda.network.model.response.GameDetails;
import com.khiladiadda.network.model.response.MatchDetails;

import butterknife.BindView;

public class AllHelpActivity extends BaseActivity {

    @BindView(R.id.btn_ok)
    Button mOkBTN;

    private MatchDetails mMatchDetail;
    private GameDetails mBattleCategory;

    @Override
    protected int getContentView() { return R.layout.activity_all_help; }

    @Override
    protected void initViews() {
        mOkBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mMatchDetail = intent.getParcelableExtra(AppConstant.DATA);
        mBattleCategory = intent.getParcelableExtra(AppConstant.BATTLE_CATEGORY);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok) {
            Intent i = new Intent(this, BattleActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.FROM_FANBATTLE);
            i.putExtra(AppConstant.DATA, mMatchDetail);
            i.putExtra(AppConstant.BATTLE_CATEGORY, mBattleCategory);
            startActivity(i);
            finish();
        }
    }

}