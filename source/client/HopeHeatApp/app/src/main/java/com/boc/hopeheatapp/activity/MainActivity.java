package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.fragement.BaseFragment;
import com.boc.hopeheatapp.fragement.MarkupFragment;
import com.boc.hopeheatapp.fragement.MsgFragment;
import com.boc.hopeheatapp.fragement.MyFragment;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.widget.bottombar.BottomBarItem;
import com.boc.hopeheatapp.widget.bottombar.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dwl
 * @date 2019/7/2.
 */
public class MainActivity extends TitleColorActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragmentList = new ArrayList<>();

    private int backPressCount = 0;
    private Handler handler = new Handler();

    private final int IDX_MSG = 0;
    private final int IDX_MY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initialTabs(savedInstanceState);
        initView();
    }

    private void initView() {
        mVpContent.setAdapter(new HomeTabAdapter(getSupportFragmentManager()));
        mVpContent.setOffscreenPageLimit(5);
        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {

            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                onBottomItemSelected(bottomBarItem, position);

//                if (position == IDX_MEETING_GUIDE) {
//                    saveMeetingGuideTime();
//                }

//                logBottomBarClickEvent(position);
            }
        });
    }


    private void initialTabs(Bundle savedInstanceState) {
        buildFragment(savedInstanceState);
    }

    /**
     * 创建首页Fragment
     *
     * @param savedInstanceState
     */
    private void buildFragment(Bundle savedInstanceState) {
        mFragmentList.add(buildMsgFragment(savedInstanceState));
        mFragmentList.add(buildMarkupFragment(savedInstanceState));
        mFragmentList.add(buildMyFragment(savedInstanceState));
    }

    private BaseFragment buildMsgFragment(Bundle savedInstanceState) {
        MsgFragment home = null;
        if (savedInstanceState != null) {
            home = (MsgFragment) getSupportFragmentManager().findFragmentByTag(
                    MsgFragment.class.getSimpleName());
            Logger.d(TAG, "buildMsgFragment from savedInstanceState");
        }

        /*重建对象*/
        if (home == null) {
            home = MsgFragment.newInstance();
        }
        return home;
    }

    private BaseFragment buildMarkupFragment(Bundle savedInstanceState) {
        MarkupFragment markupFragment = null;
        if (savedInstanceState != null) {
            markupFragment = (MarkupFragment) getSupportFragmentManager().findFragmentByTag(
                    MarkupFragment.class.getSimpleName());
            Logger.d(TAG, "buildMyFragment from savedInstanceState");
        }

        /*重建对象*/
        if (markupFragment == null) {
            markupFragment = MarkupFragment.newInstance();
        }
        return markupFragment;
    }

    private BaseFragment buildMyFragment(Bundle savedInstanceState) {
        MyFragment myFragment = null;
        if (savedInstanceState != null) {
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(
                    MyFragment.class.getSimpleName());
            Logger.d(TAG, "buildMyFragment from savedInstanceState");
        }

        /*重建对象*/
        if (myFragment == null) {
            myFragment = MyFragment.newInstance();
        }
        return myFragment;
    }

    class HomeTabAdapter extends FragmentStatePagerAdapter {

        public HomeTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private void onBottomItemSelected(BottomBarItem bottomBarItem, int position) {
//        if (position == 1) {
//            updateInteraceReddot();
//        } else if (position == 3) {
//            updateAppearanceReddot();
//        }

//        if (position == IDX_QRCODE) {
//            ActivityJumper.startCaptureActivity(getApplicationContext());
//        }
    }

}
