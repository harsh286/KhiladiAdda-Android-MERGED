package com.khiladiadda.main.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    //private String tabTitles[] = new String[] { "FACTS", "GAMES", "QUIZZES" };
    private String tabTitles[] = new String[] { "GAMES", "QUIZZES"};
    private List<Fragment> mFragmentList;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

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
