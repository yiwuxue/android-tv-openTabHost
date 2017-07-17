package com.focustv.widget;

import android.content.Context;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.AttributeSet;

import com.focustv.R;

/**
 * Created by qili on 2017/7/17.
 */

public class BrowseHeaderVerticalGridView extends VerticalGridView {
    public BrowseHeaderVerticalGridView(Context context) {
        super(context);
    }

    public BrowseHeaderVerticalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrowseHeaderVerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("RestrictedApi")
    @Override
    public void setChildrenVisibility(int visibility) {
        super.setChildrenVisibility(VISIBLE);
    }
}
