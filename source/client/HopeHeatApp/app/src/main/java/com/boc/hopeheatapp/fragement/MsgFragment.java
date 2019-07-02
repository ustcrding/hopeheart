package com.boc.hopeheatapp.fragement;

import android.os.Bundle;

import com.boc.hopeheatapp.R;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MsgFragment extends BaseFragment {

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void afterCreate(Bundle bundle) {

    }
}
