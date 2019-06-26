package com.boc.hopeheatapp.conversation;

import android.content.Context;

import com.boc.hopeheatapp.R;

import java.util.Random;

/**
 * 回话异常信息处理类
 *
 * @author dwl
 * @date 2018/3/19.
 */
public class ConversationExceptionHelper {

    /**
     * 生产随机的错误提示
     *
     * @param context
     * @return
     */
    public static String genRandonErrorTip(Context context) {
        String[] errorTips = context.getResources().getStringArray(R.array.error_tips);
        Random random = new Random();
        int idx = random.nextInt(errorTips.length);
        return errorTips[idx];
    }
}
