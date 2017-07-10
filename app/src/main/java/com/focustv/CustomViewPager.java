package com.focustv;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;

import static android.view.KeyEvent.KEYCODE_DPAD_LEFT;
import static android.view.KeyEvent.KEYCODE_DPAD_RIGHT;

/**
 * Created by qili on 2017/7/9.
 */

public class CustomViewPager extends ViewPager {
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KEYCODE_DPAD_LEFT || event.getKeyCode() == KEYCODE_DPAD_RIGHT) {
            View currentFocused = findFocus();
            int direction;
            if (event.getKeyCode() == KEYCODE_DPAD_LEFT) {
                direction = FOCUS_LEFT;
            } else {
                direction = FOCUS_RIGHT;
            }
            View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                    direction);
            if (nextFocused == null || nextFocused == currentFocused) {
                return true;
            }
        }
        return super.executeKeyEvent(event);
    }
}
