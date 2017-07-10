package com.focustv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.focustv.R;

/**
 * Created by qili on 2017/7/9.
 */

public class FragmentA extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        View temp = view.findViewById(R.id.t1);
        temp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundColor(getResources().getColor(R.color.yellow));
                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.fastlane_background));
                }
            }
        });
        return view;
    }
}
