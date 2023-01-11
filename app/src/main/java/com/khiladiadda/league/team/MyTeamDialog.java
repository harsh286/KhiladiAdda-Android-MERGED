package com.khiladiadda.league.team;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.network.model.response.TeamDetails;
import com.khiladiadda.league.team.adapter.MyTeamAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTeamDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_team_name)
    TextView mTeamNameTV;
    @BindView(R.id.tv_team_code)
    TextView mTeamCodeTV;
    @BindView(R.id.rv_team_mate)
    RecyclerView mTeamMateRV;

    private final String mTeamName, mTeamCode, mSlot;
    private final Context mContext;
    private List<TeamDetails> mLeagueList = new ArrayList<>();

    public MyTeamDialog(@NonNull Context context, String teamName, String teamCode, String slot, List<TeamDetails> list) {
        super(context);
        this.mContext = context;
        this.mTeamName = teamName;
        this.mTeamCode = teamCode;
        this.mSlot = slot;
        this.mLeagueList = list;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_view_my_team);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void initViews() {
        ButterKnife.bind(this);
        MyTeamAdapter mTeamAdapter = new MyTeamAdapter(mContext, mLeagueList);
        mTeamMateRV.setLayoutManager(new LinearLayoutManager(mContext));
        mTeamMateRV.setAdapter(mTeamAdapter);
        mTeamNameTV.setText(mTeamName + " - SLOT: " + mSlot);
        mTeamCodeTV.setText("TeamId: " + mTeamCode);
        mTeamCodeTV.setOnClickListener(this);
        mTeamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_team_code) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Join your team using " + mTeamCode + " code.");
            mContext.startActivity(Intent.createChooser(share, "TeamId"));
        }
    }

}