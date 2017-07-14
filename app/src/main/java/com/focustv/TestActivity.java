package com.focustv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.focustv.fragment.FragmentA;
import com.focustv.fragment.FragmentB;

import java.util.ArrayList;

/**
 * Created by qili on 2017/7/6.
 */

public class TestActivity extends FragmentActivity {
    private CustomTabHost openTabHost;
    private CustomTabHost.Adapter mAdapter;
    private TestPageAdapter testPageAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        openTabHost = (CustomTabHost) findViewById(R.id.open_tab_host);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new CustomTabHost.Adapter() {

            @Override
            public View getView(ViewGroup parent, int position) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.item_title, parent, false);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.width = 0;
                layoutParams.weight = 1.0f;
                int marginLeft = Utils.convertDpToPixel(parent.getContext(), 10);
                layoutParams.setMargins(marginLeft, 0, marginLeft, 0);
                return view;
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        };
        openTabHost.setAdapter(mAdapter);
        openTabHost.selectPosition(0);
        testPageAdapter = new TestPageAdapter(getSupportFragmentManager());
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        viewPager.setAdapter(testPageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setFocusableInTouchMode(true);
    }

    class TestPageAdapter extends FragmentPagerAdapter {

        public TestPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
