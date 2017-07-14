package com.focustv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BaseOnItemViewClickedListener;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnChildViewHolderSelectedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
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
    private final String TAG = "LiveActivity";
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 6;
    private ArrayObjectAdapter mRowsAdapter;
    private VerticalGridView mVerticalGridView;
    private ItemBridgeAdapter mItemBridgeAdapter;
    private CustomTabHost openTabHost;
    private CustomTabHost.Adapter mAdapter;
    private BaseOnItemViewClickedListener mOnItemViewClickedListener = new ItemViewClickedListener();
    //    private BaseOnItemViewSelectedListener mOnItemViewSelectedListener = new ItemViewSelectedListener();
    private OnChildViewHolderSelectedListener mRowSelectedListener = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            Log.d(TAG, "on child view holder:" + position);
            openTabHost.setCurrentTab(position);
        }
    };
    private ItemBridgeAdapter.AdapterListener mAdapterListener = new ItemBridgeAdapter.AdapterListener() {
        @Override
        public void onAddPresenter(Presenter presenter, int type) {
            super.onAddPresenter(presenter, type);
        }

        @Override
        public void onCreate(ItemBridgeAdapter.ViewHolder viewHolder) {
            super.onCreate(viewHolder);
        }

        @Override
        public void onBind(ItemBridgeAdapter.ViewHolder viewHolder) {
        }

        @Override
        public void onUnbind(ItemBridgeAdapter.ViewHolder viewHolder) {
            super.onUnbind(viewHolder);
        }

        @Override
        public void onAttachedToWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            RowPresenter rowPresenter = (RowPresenter) viewHolder.getPresenter();
            RowPresenter.ViewHolder rowVh = rowPresenter.getRowViewHolder(viewHolder.getViewHolder());
//            rowVh.setOnItemViewSelectedListener(mOnItemViewSelectedListener);
            rowVh.setOnItemViewClickedListener(mOnItemViewClickedListener);
        }

        @Override
        public void onDetachedFromWindow(ItemBridgeAdapter.ViewHolder viewHolder) {
            super.onDetachedFromWindow(viewHolder);
        }
    };

    @SuppressWarnings("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_live);
        mVerticalGridView = (VerticalGridView) findViewById(R.id.vertical_grid_view);
        mVerticalGridView.setOnChildViewHolderSelectedListener(mRowSelectedListener);
//        mVerticalGridView.setSelectedPosition();
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
        mItemBridgeAdapter.setAdapterListener(mAdapterListener);
        mVerticalGridView.setAdapter(mItemBridgeAdapter);
        openTabHost = (CustomTabHost) findViewById(R.id.open_tab_host);
        openTabHost.setHorizontalKeyInterceptListener(new CustomTabHost.OnKeyInterceptListener() {
            @Override
            public boolean executeKeyEvent(KeyEvent keyEvent, int keyCode) {
                return false;
            }
        });
        mAdapter = new CustomTabHost.Adapter() {

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
        openTabHost.setTabChangeListener(new CustomTabHost.TabChangeListener() {
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

    class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "click row:" + row);
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "selected row:" + row);
        }
    }
}
