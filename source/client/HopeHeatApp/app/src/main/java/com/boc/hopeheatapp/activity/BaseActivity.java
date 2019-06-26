package com.boc.hopeheatapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.boc.hopeheatapp.model.event.EventNetStatusChanged;
import com.iflytek.sunflower.FlowerCollector;

import de.greenrobot.event.EventBus;

/**
 * @author dwl
 * @date 2018/2/12.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //友盟统计
        //MobclickAgent.onResume(this);

        //讯飞统计sdk
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        // MobclickAgent.onPause(this);
        //讯飞统计sdk
        FlowerCollector.onPageEnd(getClass().getSimpleName());
        FlowerCollector.onPause(this);
    }

    /**
     * 注册事件监听
     */
    private void registerEventBus() {
        // 注册监听骑行状态变化
        EventBus.getDefault().register(this);
    }

    /**
     * 注销事件监听
     */
    private void unregisterEventBus() {
        // 注册监听骑行状态变化
        EventBus.getDefault().unregister(this);
    }

    /**
     * 处理网络状态变化
     *
     * @param event
     */
    public void onEvent(final EventNetStatusChanged event) {

    }
}
