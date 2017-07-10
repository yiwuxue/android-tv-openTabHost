package com.focustv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qili on 2017/7/6.
 */

public class TabHost extends LinearLayout {
    private View mLastFocusView;

    public TabHost(Context context) {
        super(context);
    }

    public TabHost(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return super.focusSearch(focused, direction);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        View temp = mLastFocusView;
        mLastFocusView = child;
        if (temp != null) {
            View childFocus = getFocusedChild();
            if (childFocus == null && child != temp) {
                temp.requestFocus();
//                super.requestChildFocus(temp, focused);
            } else {
                super.requestChildFocus(child, focused);
            }
        } else {
            super.requestChildFocus(child, focused);
        }
//        super.requestChildFocus(child, focused);
    }
}
