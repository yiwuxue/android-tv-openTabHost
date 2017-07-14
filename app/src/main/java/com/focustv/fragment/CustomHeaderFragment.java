package com.focustv.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.HeadersFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by liqi on 2017/7/14 0014.
 */

public class CustomHeaderFragment extends HeadersFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        getVerticalGridView().setScrollEnabled(false);
        getVerticalGridView().setFocusScrollStrategy(1);
        getVerticalGridView().setHasFixedSize(true);
        return view;
    }

    @Override
    public void setAlignment(int windowAlignOffsetTop) {
        super.setAlignment(0);
    }

    @Override
    public void setSelectedPosition(int position) {
        super.setSelectedPosition(position);
    }
}
