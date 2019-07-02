package com.boc.hopeheatapp.fragement;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.widget.PreferenceItemView;
import com.boc.hopeheatapp.widget.PreferenceItemView2;

import butterknife.BindView;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MyFragment extends BaseFragment {


    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.iv_user_sex)
    ImageView tvUserSex;

    @BindView(R.id.tv_user_addr)
    TextView tvUserAddr;

    @BindView(R.id.view_user_id_type)
    PreferenceItemView2 viewUserIdType;

    @BindView(R.id.view_user_phone)
    PreferenceItemView2 viewUserPhone;

    @BindView(R.id.view_user_id)
    PreferenceItemView2 viewUserId;

    @BindView(R.id.view_user_reg_date)
    PreferenceItemView2 viewUserRegDate;

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
        initView();
    }

    private void initView() {
        viewUserIdType.setNameAndValue(R.string.user_id_type, R.string.user_card_id);
        viewUserId.setNameAndValue(getString(R.string.user_id_type), "342401199001011234");
        viewUserPhone.setNameAndValue("联系方式", "18912341234");
        viewUserRegDate.setNameAndValue("登记日期", "2019-06-25");
    }
}
