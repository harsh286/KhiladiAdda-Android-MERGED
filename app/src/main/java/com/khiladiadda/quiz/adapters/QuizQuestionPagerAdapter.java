package com.khiladiadda.quiz.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class QuizQuestionPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public QuizQuestionPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
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
