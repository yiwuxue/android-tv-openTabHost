package com.focustv.widget;

import android.content.Context;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by liqi on 2017/7/17 0017.
 */

public class BrowseRelativeLayout extends RelativeLayout {
    public BrowseRelativeLayout(Context context) {
        super(context);
    }

    public BrowseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrowseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }

        @Override
        public void setMarginStart(@Px int start) {
            super.setMarginStart(0);
        }
    }
}
