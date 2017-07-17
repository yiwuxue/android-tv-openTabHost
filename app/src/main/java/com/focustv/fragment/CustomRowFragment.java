package com.focustv.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liqi on 2017/7/14 0014.
 */

public class CustomRowFragment extends RowsFragment {
    public CustomRowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        getVerticalGridView().setFocusScrollStrategy(1);
        return view;
    }

    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        super.setAlignment(0);
    }

    @Override
    public void setExpand(boolean expand) {
        super.setExpand(false);
    }

}
