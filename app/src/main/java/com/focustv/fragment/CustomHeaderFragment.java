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
import android.support.v17.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.focustv.*;
import com.focustv.CustomRowHeaderPresenter;

import java.lang.reflect.Field;


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

    @SuppressWarnings("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = init(inflater, container);
        getVerticalGridView().setFocusScrollStrategy(1);
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


    private View init(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.header_fragment, container, false);
        Class cls = null;
        try {
            cls = Class.forName("android.support.v17.leanback.app.BaseRowFragment");
            Field field = cls.getDeclaredField("mVerticalGridView");
            field.setAccessible(true);
            field.set(this, (VerticalGridView) view.findViewById(R.id.browse_headers));
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
}
