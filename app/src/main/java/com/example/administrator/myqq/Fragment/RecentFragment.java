package com.example.administrator.myqq.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myqq.R;

/**
 * Created by Administrator on 2016/10/16.
 */
public class RecentFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment, container,false);
        return view;
    }
}
