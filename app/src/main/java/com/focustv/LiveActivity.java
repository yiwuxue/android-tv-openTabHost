package com.focustv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by liqi on 2017/7/12 0012.
 */

public class LiveActivity extends Activity {
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 6;
    private ArrayObjectAdapter mRowsAdapter;
    private VerticalGridView mVerticalGridView;
    private ItemBridgeAdapter mItemBridgeAdapter;
    private OpenTabHost openTabHost;
    private OpenTabHost.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_live);
        mVerticalGridView = (VerticalGridView) findViewById(R.id.vertical_grid_view);
        initView();
    }

    private void initView() {
        List<Movie> list = MovieList.setupMovies();
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        int i;
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
//            HeaderItem header = new HeaderItem(i, LiveList.CATEGORIES[i]);
//            mRowsAdapter.add(new ListRow(header, listRowAdapter));
            mRowsAdapter.add(new ListRow(listRowAdapter));
        }
        mItemBridgeAdapter = new ItemBridgeAdapter(mRowsAdapter);
        mVerticalGridView.setAdapter(mItemBridgeAdapter);
        openTabHost = (OpenTabHost) findViewById(R.id.open_tab_host);
        mAdapter = new OpenTabHost.Adapter() {

            @Override
            public View getView(ViewGroup parent, int position) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.item_vertical_title, parent, false);
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setText(LiveList.CATEGORIES[position]);
                return view;
            }

            @Override
            public int getItemCount() {
                return LiveList.CATEGORIES.length;
            }
        };
        openTabHost.setAdapter(mAdapter);
        openTabHost.setTabChangeListener(new OpenTabHost.TabChangeListener() {
            @Override
            public void onTabChange(View parentView, TextView title, int position, boolean hasFocus) {
                if (title != null) {
                    if (hasFocus) {
                        title.setTextColor(getResources().getColor(R.color.fastlane_background));
                    } else {
                        title.setTextColor(getResources().getColor(R.color.yellow));
                    }
                }
            }
        });
        openTabHost.selectPosition(0);
    }
}
