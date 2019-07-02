package com.boc.hopeheatapp.fragement;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boc.hopeheatapp.util.DialolgUtil;
import com.github.johnpersano.supertoasts.SuperToast;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author dingr
 * @date 2017/11/11.
 */
public abstract class BaseFragment extends Fragment {

    public static final String CONTENT = "content";

    private Dialog progressDialog = null;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    protected void showMessage(CharSequence msg) {
        if (!TextUtils.isEmpty(msg) && isAdded()) {
            SuperToast.cancelAllSuperToasts();
            SuperToast.create(getContext(), msg, SuperToast.Duration.SHORT).show();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle bundle);

//    @Inject
//    DataLayer mDataLayer;
//
//    private CompositeSubscription mCompositeSubscription;


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        if (mCompositeSubscription != null) {
//            mCompositeSubscription.unsubscribe();
//        }
        dismissProgressBar();
    }

//    public void bindLifecycle(Subscription s) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(s);
//    }
//
//    public BaseFragment() {
//        AppComponent.Instance.get().inject(this);
//    }
//
//    public DataLayer getDataLayer() {
//        return mDataLayer;
//    }

    public void onEvent(Object event){

    }

    protected void showProgressBar(String title) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = DialolgUtil.createLoadingDialog(getActivity(), title);
            progressDialog.show();
        }
    }

    protected void dismissProgressBar() {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean hasReady() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            return true;
        }
        return false;
    }
}

