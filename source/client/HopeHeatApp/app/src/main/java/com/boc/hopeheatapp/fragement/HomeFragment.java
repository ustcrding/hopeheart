package com.boc.hopeheatapp.fragement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.activity.KnowledgeActivity;
import com.boc.hopeheatapp.activity.WelcomeActivity;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.string.StringUtil;

import rx.Subscriber;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class HomeFragment extends BaseFragment {

    private TextView tvHomeMsg;
    private Button btHistory;
    private Button btInput;
    private Button btRead;

    private boolean mShowFirst = true;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void afterCreate(Bundle bundle) {

        tvHomeMsg = (TextView) getActivity().findViewById(R.id.tv_home_msg);
        btHistory = (Button) getActivity().findViewById(R.id.bt_history);
        btInput = (Button) getActivity().findViewById(R.id.input_personal_info);
        btRead = (Button) getActivity().findViewById(R.id.read_from_card);

        getActivity().findViewById(R.id.read_from_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                doUserBind();
            }
        });

        getActivity().findViewById(R.id.input_personal_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startUserInfoActivity(getActivity());
            }
        });

        getActivity().findViewById(R.id.ignore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 查询救援知识
        getActivity().findViewById(R.id.btn_search_rescue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startKnowledgeActivity(getActivity(), KnowledgeActivity.TYPE_RESCUE);
            }
        });

        // 查询心理知识
        getActivity().findViewById(R.id.btn_search_psychology).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startKnowledgeActivity(getActivity(), KnowledgeActivity.TYPE_PSYCHOLOGY);
            }
        });

        getActivity().findViewById(R.id.bt_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityJumper.startConsultHistoryActivity(getActivity());
            }
        });

        if (mShowFirst) {
            tvHomeMsg.setText(R.string.welcome_msg);
            btHistory.setVisibility(View.GONE);
            btInput.setVisibility(View.VISIBLE);
            btRead.setVisibility(View.VISIBLE);
        } else {
            tvHomeMsg.setText(R.string.welcome_msg2);
            btHistory.setVisibility(View.VISIBLE);
            btInput.setVisibility(View.INVISIBLE);
            btRead.setVisibility(View.GONE);
        }
    }

    public void setFirst(boolean first) {
        mShowFirst = first;
    }

    private void doUserBind() {
        UserLoader userLoader = new UserLoader();
        userLoader.userBind("" + UserManager.getInstance().getUser().getUserId()).subscribe(new Subscriber<UserEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getActivity(), "未找到用户", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserEntity user) {
                if (user != null && StringUtil.equals(user.getPreserve(), "0")) {
                    UserManager.getInstance().getUser().setPreserve(user.getPreserve());
                    UserManager.getInstance().saveUser();
                    Toast.makeText(getActivity(), "已发现用户身份", Toast.LENGTH_LONG).show();
//                    ActivityJumper.startMainActivity(getActivity());
                } else {
                    Toast.makeText(getActivity(), "未找到用户", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
