package com.focustv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by liqi on 2017/7/7 0007.
 */

public class CustomTabHost extends LinearLayout {
    private static final String TAG = "OpenTabHost";
    //是否在左右移动时候会调focusSearch方法
    private boolean mIsInvokeFocusSearch = false;
    //上一次获取焦点的View
    private View mLastFocusedView = null;
    //适配器
    private Adapter mAdapter;
    //当前选中tab
    private int mCurrentTab = -1;

    //Tab切换监听器
    private TabChangeListener mTabChangeListener;

    //CustomTabHost每个子View的OnFocusChangeListener
    private OnFocusChangeListener mFocusChangeListener;

    //焦点水平移动时候事件拦截监听器
    private OnKeyInterceptListener mHorizontalKeyInterceptListener;

    //焦点竖直移动时候事件拦截监听器
    private OnKeyInterceptListener mVerticalKeyInterceptListener;

    //在CustomTabHost失去焦点的时候是否保持最后一个获取焦点的View在选中的状态
    private boolean mIsKeepFocus = true;

    //CustomTabHost仅仅失去焦点没有获取到焦点
    private boolean mIsOnlyUnFocus = false;

    public CustomTabHost(Context context) {
        super(context);
        init();
    }

    public CustomTabHost(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
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
                        }
                    }
                    mIsInvokeFocusSearch = false;
                    mLastFocusedView = v;
                } else {
                    if (!mIsKeepFocus || !mIsOnlyUnFocus) {
                        sendFocusChangeListenerToChild(v, hasFocus);
                    }
                    mIsOnlyUnFocus = false;
                }
            }
        };
    }

    @Override
    public View focusSearch(View focused, int direction) {
        if (getOrientation() == HORIZONTAL) {
            if (direction == View.FOCUS_RIGHT || direction == FOCUS_LEFT) {
                mIsInvokeFocusSearch = true;
            } else {
                mIsOnlyUnFocus = true;
                mIsInvokeFocusSearch = false;
            }
        } else {
            if (direction == View.FOCUS_RIGHT || direction == FOCUS_LEFT) {
                mIsOnlyUnFocus = true;
                mIsInvokeFocusSearch = false;
            } else {
                mIsInvokeFocusSearch = true;
            }
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
         */
    }

    private void sendFocusChangeListenerToChild(View v, boolean hasFocus) {
        int index = indexOfChild(v);
        if (hasFocus) {
            if (index != -1) {
                mCurrentTab = index;
            }
        }
        invokeTabChangeListener(v, index, hasFocus);
    }

    public interface TabChangeListener {
        void onTabChange(View parentView, TextView title, int position, boolean hasFocus);
    }

    /**
     * 设置当前选中的Tab
     *
     * @param position tab的position
     */
    public void setCurrentTab(int position) {
        if (position > -1 && position < getChildCount() && position != mCurrentTab) {
            if (mLastFocusedView != null) {
                invokeTabChangeListener(mLastFocusedView, mCurrentTab, false);
            }
            mCurrentTab = position;
            mLastFocusedView = getChildAt(position);
            invokeTabChangeListener(mLastFocusedView, mCurrentTab, true);
        }
    }


    private void invokeTabChangeListener(View parentView, int index, boolean hasFocus) {
        if (mTabChangeListener != null) {
            TextView title = (TextView) parentView.findViewById(R.id.title);
            mTabChangeListener.onTabChange(parentView, title, index, hasFocus);
        }
    }


    public interface OnKeyInterceptListener {
        boolean executeKeyEvent(KeyEvent keyEvent, int keyCode);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled) {
            return handled;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mHorizontalKeyInterceptListener != null) {
                    return mHorizontalKeyInterceptListener.executeKeyEvent(event, event.getKeyCode());
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mVerticalKeyInterceptListener != null) {
                    return mVerticalKeyInterceptListener.executeKeyEvent(event, event.getKeyCode());
                }
                break;
        }
        return handled;
    }

    public void setHorizontalKeyInterceptListener(OnKeyInterceptListener horizontalKeyInterceptListener) {
        mHorizontalKeyInterceptListener = horizontalKeyInterceptListener;
    }

    public void setVerticalKeyInterceptListener(OnKeyInterceptListener verticalKeyInterceptListener) {
        mVerticalKeyInterceptListener = verticalKeyInterceptListener;
    }
}
