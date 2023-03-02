package com.khiladiadda.ludoTournament.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khiladiadda.clashx2.main.fragment.AllDashboardFragment;
import com.khiladiadda.clashx2.main.fragment.CricketDashBoardFragment;
import com.khiladiadda.clashx2.main.fragment.FootballDashBoardFragment;
import com.khiladiadda.clashx2.main.fragment.KabbadiDashBoardFragment;
import com.khiladiadda.ludoTournament.fragment.LiveLudoTmtFragment;
import com.khiladiadda.ludoTournament.fragment.PastLudoTmtFragment;
import com.khiladiadda.ludoTournament.fragment.UpcomingLudoTmtFragment;

public class MyTournamentViewPagerAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public MyTournamentViewPagerAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new PastLudoTmtFragment();
            case 2:
                return new UpcomingLudoTmtFragment();
            default:
                return new LiveLudoTmtFragment();
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}