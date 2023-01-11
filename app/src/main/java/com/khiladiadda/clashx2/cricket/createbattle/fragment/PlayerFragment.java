package com.khiladiadda.clashx2.cricket.createbattle.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.cricket.createbattle.adapter.PlayerSelectionAdapter;
import com.khiladiadda.network.model.response.hth.CaptainTeamHTH;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlayerFragment extends BaseFragment implements PlayerSelectionAdapter.IOnItemChildClickListener {

    private List<KaPlayerHTH> mKaplayerList;
    @BindView(R.id.rv_batsman)
    RecyclerView mPlayerRV;
    private PlayerSelectionAdapter mPlayerSelectionAdapter;
    private int mPlayerPosition = -1;
    private List<KaPlayerHTH> mPlayerList;
    private int mType;
    private List<CaptainTeamHTH> mCaptainHTH;
    private List<CaptainTeamHTH> mOppentHTHList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_player;
    }

    public PlayerFragment() {// Required empty public constructor
    }

    public static Fragment getInstance(List<KaPlayerHTH> kaPlayerList, List<CaptainTeamHTH> captainHTHList, int type) {
        PlayerFragment batsmanFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstant.DATA, (ArrayList<? extends Parcelable>) kaPlayerList);
        bundle.putParcelableArrayList(AppConstant.BATTLE_DATA, (ArrayList<? extends Parcelable>) captainHTHList);
        bundle.putInt(AppConstant.EXTRA_TYPE, type);
        batsmanFragment.setArguments(bundle);
        return batsmanFragment;
    }


    public static Fragment getInstanceCreateBattle(List<KaPlayerHTH> kaPlayerList, int type) {
        PlayerFragment batsmanFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstant.DATA, (ArrayList<? extends Parcelable>) kaPlayerList);
        bundle.putInt(AppConstant.EXTRA_TYPE, type);
        batsmanFragment.setArguments(bundle);
        return batsmanFragment;
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if (bundle != null) {
            mKaplayerList = bundle.getParcelableArrayList(AppConstant.DATA);
            mCaptainHTH = bundle.getParcelableArrayList(AppConstant.BATTLE_DATA);
            mOppentHTHList = bundle.getParcelableArrayList(AppConstant.BATTLE_DATAOPP);
            mType = bundle.getInt(AppConstant.EXTRA_TYPE);
        }
    }

    @Override
    protected void initVariables() {
        mPlayerList = new ArrayList<>();
        for (int i = 0; i < mKaplayerList.size(); i++) {
            if (mKaplayerList.get(i).getRole() == mType) {
                mPlayerList.add(mKaplayerList.get(i));
            }
        }
        mPlayerSelectionAdapter = new PlayerSelectionAdapter(mPlayerList, mCaptainHTH,mOppentHTHList);
        mPlayerSelectionAdapter.notifyDataSetChanged();
        mPlayerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlayerRV.setAdapter(mPlayerSelectionAdapter);
        mPlayerSelectionAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPlayerSelected(int position) {
        mPlayerPosition = position;
        for (int i = 0; i < mPlayerList.size(); i++) {
            mPlayerList.get(i).setSelected(false);
        }
        mPlayerList.get(position).setSelected(true);
        mPlayerSelectionAdapter.notifyDataSetChanged();
    }

    public KaPlayerHTH getSelectedPlayer() {
        if (mPlayerPosition >= 0) {
            return mPlayerList.get(mPlayerPosition);
        }
        return null;
    }
}