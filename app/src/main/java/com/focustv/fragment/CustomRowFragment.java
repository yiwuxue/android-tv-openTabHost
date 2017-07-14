package com.focustv.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liqi on 2017/7/14 0014.
 */

public class CustomRowFragment extends RowsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        getVerticalGridView().setFocusScrollStrategy(1);
        return view;
    }

    @Override
    public void setAlignment(int windowAlignOffsetFromTop) {
        super.setAlignment(0);
    }

    @Override
    public void setEntranceTransitionState(boolean afterTransition) {
        super.setEntranceTransitionState(afterTransition);
        VerticalGridView verticalView = getVerticalGridView();
        if (verticalView != null) {
            final int count = verticalView.getChildCount();
            for (int i = 0; i < count; i++) {
                ItemBridgeAdapter.ViewHolder ibvh = (ItemBridgeAdapter.ViewHolder)
                        verticalView.getChildViewHolder(verticalView.getChildAt(i));
                RowPresenter rowPresenter = (RowPresenter) ibvh.getPresenter();
                RowPresenter.ViewHolder vh = rowPresenter.getRowViewHolder(ibvh.getViewHolder());
                vh.getHeaderViewHolder().view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
