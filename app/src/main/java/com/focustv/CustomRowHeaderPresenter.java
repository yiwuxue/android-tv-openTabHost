package com.focustv;

import android.support.annotation.RestrictTo;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.RowHeaderView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Created by liqi on 2017/7/17 0017.
 */

public class CustomRowHeaderPresenter extends RowHeaderPresenter {
    private final int mLayoutResourceId;
    private boolean mAnimateSelect = true;

    public CustomRowHeaderPresenter() {
        super(R.layout.item_lb_header);
        mLayoutResourceId = R.layout.item_lb_header;
    }

    public CustomRowHeaderPresenter(int layoutResourceId) {
        super(layoutResourceId);
        mLayoutResourceId = layoutResourceId;
    }

}
