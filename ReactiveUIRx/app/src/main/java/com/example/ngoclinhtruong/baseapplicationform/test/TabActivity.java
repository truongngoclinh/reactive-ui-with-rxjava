package com.example.ngoclinhtruong.baseapplicationform.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.example.base.BaseActivity;
import com.example.ngoclinhtruong.baseapplicationform.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Truong on 9/20/16 - 23:00.
 * Description: We have 3 ways to create tabs with swipe view.
 * - using PageTabStrip: child of ViewPager but the animation, view is not same as common tabs
 * - using TabHost: deprecated
 * - using ActionBar: deprecated
 * So which one should be use?
 */

@EActivity
public class TabActivity extends BaseActivity implements ActionBar.TabListener {

    private final String tabs[] = {"1", "2"};

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    private TabPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        /* using actionBar create tabs */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (String tabName : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
        }

        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PagerFragment fragment = PagerFragment_.builder().position(position).build();

            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
