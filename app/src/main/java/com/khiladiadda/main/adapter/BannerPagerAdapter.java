package com.khiladiadda.main.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BannerPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;

    public BannerPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
