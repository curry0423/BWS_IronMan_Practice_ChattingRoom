package com.ood.waterball.teampathy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ood.waterball.teampathy.SinglePageArchitecture;

/**
    在 Single Activity Architecture結構下，放置在Base Activity中的Fragment
 */

public abstract class ActivityBaseFragment extends Fragment {

    protected SinglePageArchitecture singlePage;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SinglePageArchitecture) {
            singlePage = getParentSinglePage(context); //取得 Base Activity
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SinglePageArchitecture");
        }
    }

    protected SinglePageArchitecture getParentSinglePage(Context context){
        SinglePageArchitecture singlePageArchitecture = (SinglePageArchitecture) context;
        //Log("根頁面: " + singlePageArchitecture.getPageName());
        return singlePageArchitecture;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onFetchData(savedInstanceState,getArguments());
    }


    protected abstract void onFetchData(@Nullable Bundle savedInstanceState , @Nullable Bundle arguBundle);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), container, false);
    }

    protected abstract int getLayoutResource();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFindViews(view);
        onControlViews();
    }

    protected abstract void onFindViews(View parentView);
    protected abstract void onControlViews();

    protected SinglePageArchitecture getSinglePage(){
        return singlePage;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        singlePage = null;
    }
}