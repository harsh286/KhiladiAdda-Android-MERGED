package com.khiladiadda.main.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.main.adapter.HomeFragmentPagerAdapter;
import com.khiladiadda.main.game.GameFragment;
import com.khiladiadda.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    private int mSelected = 0;
    private List<Fragment> mFragmentList;

    public HomeFragment() {
    }

    public static Fragment getInstance(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.EXTRA_SELECTED, i);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mSelected = bundle.getInt(AppConstant.EXTRA_SELECTED);
    }

    @Override
    protected void initVariables() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(GameFragment.getInstance());
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), getContext(), mFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(mActivity, R.color.lighter_gray));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
        viewPager.setCurrentItem(mSelected);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void getData(int position) {
        if (mActivity == null) {
            return;
        }
    }

    public interface IOnPageLoaded {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}