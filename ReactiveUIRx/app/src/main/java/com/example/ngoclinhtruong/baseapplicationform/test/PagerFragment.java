package com.example.ngoclinhtruong.baseapplicationform.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ngoclinhtruong.baseapplicationform.R;
import com.example.utils.LogUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * Created by Truong on 9/20/16 - 23:07.
 * Description:
 */

@EFragment
public class PagerFragment extends Fragment {

  /*  public static PagerFragment newInstance(int pos) {

        Bundle args = new Bundle();
        args.putInt("pos", pos);

        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }*/
    @FragmentArg
    int position;

    private View mRootView;
    private TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("onCreateView");
        mRootView = inflater.inflate(R.layout.pager_fragment, null, false);
        mTitle = (TextView) mRootView.findViewById(R.id.title);

        int pos = getArguments().getInt("pos");

        mTitle.setText(pos + "");

        return mRootView;
    }

    @AfterViews
    void afterView() {
        LogUtils.d("afterViews annotation");
    }

}
