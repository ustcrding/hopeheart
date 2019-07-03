package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.fragement.BaseFragment;
import com.boc.hopeheatapp.fragement.HomeFragment;
import com.boc.hopeheatapp.fragement.MyFragment;
import com.boc.hopeheatapp.model.UserEntity;
import com.boc.hopeheatapp.service.biz.UserLoader;
import com.boc.hopeheatapp.user.UserManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.bottombar.BottomBarItem;
import com.boc.hopeheatapp.widget.bottombar.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @author dwl
 * @date 2018/2/8.
 */
public class WelcomeActivity extends TitleColorActivity {
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private LinearLayout mContent;
    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean first = getIntent().getBooleanExtra(ActivityJumper.EXTRA_FIRST_MARK, false);

        setContentView(R.layout.activity_welcome);

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mContent = (LinearLayout) findViewById(R.id.ll_content);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);

        if (first) {
            mContent.setVisibility(View.GONE);
            mFragmentList.add(buildHomeFragment(savedInstanceState));

            mViewPager.setAdapter(new WelcomeActivity.HomeTabAdapter(getSupportFragmentManager()));
            mViewPager.setOffscreenPageLimit(5);
        } else {
            mFragmentList.add(buildHomeFragment(savedInstanceState));
            mFragmentList.add(buildMyFragment(savedInstanceState));

            mViewPager.setAdapter(new WelcomeActivity.HomeTabAdapter(getSupportFragmentManager()));
            mViewPager.setOffscreenPageLimit(5);
            mBottomBarLayout.setViewPager(mViewPager);

            ((HomeFragment)mFragmentList.get(0)).setFirst(false);
        }
    }

    private BaseFragment buildHomeFragment(Bundle savedInstanceState) {
        HomeFragment home = null;
        if (savedInstanceState != null) {
            home = (HomeFragment) getSupportFragmentManager().findFragmentByTag(
                    HomeFragment.class.getSimpleName());
            Logger.d(TAG, "buildMsgFragment from savedInstanceState");
        }

        /*重建对象*/
        if (home == null) {
            home = HomeFragment.newInstance();
        }
        return home;
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
}
