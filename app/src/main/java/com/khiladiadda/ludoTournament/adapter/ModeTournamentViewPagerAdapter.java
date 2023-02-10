package com.khiladiadda.ludoTournament.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khiladiadda.ludoTournament.fragment.ClassicFragment;
import com.khiladiadda.ludoTournament.fragment.LiveLudoTmtFragment;
import com.khiladiadda.ludoTournament.fragment.PastLudoTmtFragment;
import com.khiladiadda.ludoTournament.fragment.SeriesFragment;
import com.khiladiadda.ludoTournament.fragment.TimerFragment;
import com.khiladiadda.ludoTournament.fragment.UpcomingLudoTmtFragment;

public class ModeTournamentViewPagerAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public ModeTournamentViewPagerAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new ClassicFragment();
            case 2:
                return new SeriesFragment();
            default:
                return new TimerFragment();
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}