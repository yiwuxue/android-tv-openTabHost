package com.focustv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by liqi on 2017/7/7 0007.
 */

public class OpenTabHost extends LinearLayout {
    private static final String TAG = "OpenTabHost";
    //是否在左右移动时候会调focusSearch方法
    private boolean mIsInvokeFocusSearch = false;
    //上一次获取焦点的View
    private View mLastFocusedView = null;
    //适配器
    private Adapter mAdapter;
    //当前选中tab
    private int mCurrentTab = -1;

    private TabChangeListener mTabChangeListener;

    private OnFocusChangeListener mFocusChangeListener;

    public OpenTabHost(Context context) {
        super(context);
        init();
    }

    public OpenTabHost(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenTabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        mFocusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mIsInvokeFocusSearch || v == mLastFocusedView) {
                        sendFocusChangeListenerToChild(v, hasFocus);
                    } else {
                        if (mLastFocusedView != null) {
                            mIsInvokeFocusSearch = false;
                            mLastFocusedView.requestFocus();
                            return;
//                            sendFocusChangeListenerToChild(mLastFocusedView, hasFocus);
                        }
                    }
                    mIsInvokeFocusSearch = false;
                    mLastFocusedView = v;
                } else {
                    sendFocusChangeListenerToChild(v, hasFocus);
                }
            }
        };
    }

    @Override
    public View focusSearch(View focused, int direction) {
        if (direction == View.FOCUS_RIGHT || direction == FOCUS_LEFT) {
            mIsInvokeFocusSearch = true;
        } else {
            mIsInvokeFocusSearch = false;
        }
        return super.focusSearch(focused, direction);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        if (mAdapter != adapter) {
            removeAllViews();
            mAdapter = adapter;
        }
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                View view = mAdapter.getView(this, i);
                view.setOnFocusChangeListener(mFocusChangeListener);
                addView(view);
            }
        }
    }

    /**
     * 指定position位置child获取焦点
     *
     * @param position
     */
    public void selectPosition(int position) {
        if (position > -1 && position < getChildCount()) {
            mIsInvokeFocusSearch = true;
            mCurrentTab = position;
            mLastFocusedView = getChildAt(position);
            mLastFocusedView.requestFocus();
        }
    }

    public void setTabChangeListener(TabChangeListener tabChangeListener) {
        mTabChangeListener = tabChangeListener;
    }

    public static abstract class Adapter {
        public abstract View getView(ViewGroup parent, int position);

        public abstract int getItemCount();

        /**
         * （不要自己去为子View设置onFocusChangeListener,OpenTabHost会在子View调用
         * onFocusChange先做预处理，在处理完了会调用Adapter getFocusChangeListener方法
         * 给用户消化焦点改变事件）
         * 设置用户需要消化焦点改变事件监听器
         *
         * @return
         */
        public abstract OnFocusChangeListener getFocusChangeListener();
    }

    private void sendFocusChangeListenerToChild(View v, boolean hasFocus) {
        if (mAdapter != null && mAdapter.getFocusChangeListener() != null) {
            mAdapter.getFocusChangeListener().onFocusChange(v, hasFocus);
        }
        if (hasFocus) {
            int child = indexOfChild(v);
            if (child != -1) {
                mCurrentTab = child;
                if (mTabChangeListener != null) {
                    mTabChangeListener.onTabChange(child);
                }
            }
        }
    }

    public interface TabChangeListener {
        void onTabChange(int position);
    }

}
