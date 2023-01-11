package com.khiladiadda.clashx2.kabaddi.createbattle.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.clashx2.kabaddi.createbattle.adapter.KabaddiPlayerSelectionAdapter;
import com.khiladiadda.network.model.response.hth.CaptainTeamHTH;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KabaddiPlayerFragment extends BaseFragment implements KabaddiPlayerSelectionAdapter.IOnItemChildClickListener {

    private List<KaPlayerHTH> mKaplayerList;
    @BindView(R.id.rv_batsman)
    RecyclerView mPlayerRV;
    private KabaddiPlayerSelectionAdapter mPlayerSelectionAdapter;
    private int mPlayerPosition = -1;
    private List<Integer> mPlayerPositionArr = new ArrayList<Integer>();
    private List<KaPlayerHTH> mPlayerList;
    private int mType;
    private List<CaptainTeamHTH> mCaptainHTH;
    private List<CaptainTeamHTH> mOppentHTHList;
    private List<KaPlayerHTH> mKaPlayerHTH = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_player;
    }

    public KabaddiPlayerFragment() {// Required empty public constructor
    }

    public static Fragment getInstance(List<KaPlayerHTH> kaPlayerList, List<CaptainTeamHTH> captainHTHList, int type) {
        KabaddiPlayerFragment batsmanFragment = new KabaddiPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstant.DATA, (ArrayList<? extends Parcelable>) kaPlayerList);
        bundle.putParcelableArrayList(AppConstant.BATTLE_DATA, (ArrayList<? extends Parcelable>) captainHTHList);
        bundle.putInt(AppConstant.EXTRA_TYPE, type);
        batsmanFragment.setArguments(bundle);
        return batsmanFragment;
    }


    public static Fragment getInstanceCreateBattle(List<KaPlayerHTH> kaPlayerList, int type) {
        KabaddiPlayerFragment batsmanFragment = new KabaddiPlayerFragment();
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
        mPlayerSelectionAdapter = new KabaddiPlayerSelectionAdapter(mPlayerList, mCaptainHTH, mOppentHTHList);
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
        for (int i = 0; i < mPlayerList.size(); i++) {
            if (!mPlayerPositionArr.contains(i)) {
                if (mPlayerList.get(i).isSelected()) {
                    mPlayerList.get(i).setSelected(false);
                    mPlayerSelectionAdapter.getItemCount();
                    if (mPlayerPositionArr.size() < 2) {
                        mPlayerPositionArr.add(i);
                        mPlayerList.get(i).setSelected(true);
                    }
                }
            } else break;
        }
        mPlayerPosition = position;
        if (mPlayerPositionArr.size() == 2) {
            mPlayerList.get(mPlayerPositionArr.get(0)).setSelected(false);
            mPlayerPositionArr.remove(0);
            mKaPlayerHTH.clear();
        }
        mPlayerPositionArr.add(position);
        mPlayerList.get(position).setSelected(true);
        mPlayerSelectionAdapter.notifyDataSetChanged();
    }

    public List<KaPlayerHTH> getSelectedPlayer() {
        if (mPlayerPositionArr.size() != 0) {
            mKaPlayerHTH.clear();
            for (int data : mPlayerPositionArr)
                mKaPlayerHTH.add(mPlayerList.get(data));
        }

        mPlayerPositionArr.clear();
        return mKaPlayerHTH;
    }
}