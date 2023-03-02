package com.khiladiadda.clashx2.main.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khiladiadda.clashx2.main.fragment.AllDashboardFragment;
import com.khiladiadda.clashx2.main.fragment.CricketDashBoardFragment;
import com.khiladiadda.clashx2.main.fragment.FootballDashBoardFragment;
import com.khiladiadda.clashx2.main.fragment.KabbadiDashBoardFragment;

public class ClashXDashBoardTabsAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public ClashXDashBoardTabsAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new CricketDashBoardFragment();
            case 2:
                return new FootballDashBoardFragment();
            case 3:
                return new KabbadiDashBoardFragment();
            default:
                return new AllDashboardFragment();
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}