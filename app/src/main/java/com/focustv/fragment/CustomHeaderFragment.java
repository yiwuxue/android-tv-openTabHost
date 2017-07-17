package com.focustv.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.HeadersFragment;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DividerPresenter;
import android.support.v17.leanback.widget.DividerRow;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.SectionRow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.focustv.*;
import com.focustv.CustomRowHeaderPresenter;


/**
 * Created by liqi on 2017/7/14 0014.
 */

public class CustomHeaderFragment extends HeadersFragment {
    private static final PresenterSelector sHeaderPresenter = new ClassPresenterSelector()
            .addClassPresenter(DividerRow.class, new DividerPresenter())
            .addClassPresenter(SectionRow.class,
                    new RowHeaderPresenter(android.support.v17.leanback.R.layout.lb_section_header, false))
            .addClassPresenter(Row.class, new CustomRowHeaderPresenter(R.layout.item_lb_header));

    public CustomHeaderFragment() {
        setPresenterSelector(sHeaderPresenter);
    }

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
