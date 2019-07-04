package com.yuntongxun.voipdemo;

import android.content.Context;
import android.widget.ImageView;

import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.plugin.common.common.utils.ImageLoaderUtils;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.voip.CallFinishEntry;
import com.yuntongxun.plugin.voip.IVoipCallBack;

import java.util.Random;

/**
 * Created by WJ on 2016/11/29.
 */

public class VoipImpl implements IVoipCallBack {
    private static final String TAG = "VoipImpl";
    private static VoipImpl instance;

    public static VoipImpl getInstance() {
        if (instance == null) {
            instance = new VoipImpl();
        }
        return instance;
    }

    /**
     * 通话状态信息
     *
     * @param voIPCall 通话状态信息
     */
    @Override
    public void onCallEvents(ECVoIPCallManager.VoIPCall voIPCall) {
        LogUtil.d(TAG, "[onCallEvents] call:" + voIPCall.callState + ",reason:" + voIPCall.reason);
    }

    /**
     * 通话结束
     * <p>
     * int STATE_100 = 100;//正常通话结束(通话时长)
     * int STATE_101 = 101;//主动挂机并且未接通 ===已取消
     * int STATE_102 = 102;//被叫方拒绝===对方已拒绝
     * int STATE_201 = 201;//主叫方取消并且未接通===对方已取消
     * int STATE_202 = 202;//自己拒绝==已拒绝
     *
     * @param entry 通话信息
     */
    @Override
    public void onCallFinish(CallFinishEntry entry) {
        LogUtil.d(TAG, "[onCallFinish] entry:" + entry);
    }

    /**
     * 通话页面信息绑定接口
     *
     * @param userId 传出的id 开发者可根据此id来从自己数据库等中查询对应的名字和头像
     * @param avater 头像的imageview控件
     */
    @Override
    public void onVoipBindView(Context context, String userId, ImageView avater) {
        //代码示例
        int random = mRandom.nextInt(mAvatarArray.length);
        LogUtil.e(TAG, "onVoipBindView userId:" + userId + " avater:" + avater + ",random:" + random);
        try {
            ImageLoaderUtils.loadImage(R.mipmap.ic_launcher, mAvatarArray[random], avater);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Random mRandom = new Random();
    private final String[] mAvatarArray = new String[]{
            "http://123.57.204.169:8888/vtm/8ab3bdf156e3e63d0156e43bb86a0006/rxhf12583/1500020454268021169.png_thum",
            "http://123.57.204.169:8888/vtm/8ab3bdf156e3e63d0156e43bb86a0006/rxhf12815/1490594791988598468.png_thum",
            "http://123.57.204.169:8888/vtm/8ab3bdf156e3e63d0156e43bb86a0006/rxhf12888/1499959725365913560.jpeg_thum",
            "http://123.57.204.169:8888/vtm/8ab3bdf156e3e63d0156e43bb86a0006/rxhf12926/1485158266332197170.png_thum",
            "http://123.57.204.169:8888/vtm/8ab3bdf156e3e63d0156e43bb86a0006/rxhf12843/1485159616218188773.png_thum"
    };

}
