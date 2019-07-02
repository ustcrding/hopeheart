package com.boc.hopeheatapp.fragement;

import android.os.Bundle;

import com.boc.hopeheatapp.R;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MyFragment extends BaseFragment {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void afterCreate(Bundle bundle) {

    }
}
