package com.khiladiadda.clashx2.football.createbattle.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FootballHeadToHeadFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[]{"FWD", "DEF", "MID", "GK", "REVIEW"};
    private List<Fragment> mFragmentList;

    public FootballHeadToHeadFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}