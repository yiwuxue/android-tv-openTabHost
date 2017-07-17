package com.focustv.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.focustv.R;

import java.lang.reflect.Field;

/**
 * Created by liqi on 2017/7/14 0014.
 */

public class CustomRowFragment extends RowsFragment {
    public CustomRowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = init(inflater, container);
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

    private View init(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.rows_fragment, container, false);
        Class cls = null;
        try {
            cls = Class.forName("android.support.v17.leanback.app.BaseRowFragment");
            Field field = cls.getDeclaredField("mVerticalGridView");
            field.setAccessible(true);
            field.set(this, findGridViewFromRoot(view));
            Field field1 = cls.getDeclaredField("mPendingTransitionPrepare");
            field1.setAccessible(true);
            boolean prepare = (boolean) field1.get(this);
            if (prepare) {
                field1.set(this, false);
                onTransitionPrepare();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    protected VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(R.id.container_list);
    }

}
