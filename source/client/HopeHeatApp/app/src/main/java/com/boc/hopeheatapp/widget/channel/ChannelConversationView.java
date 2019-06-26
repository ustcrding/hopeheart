package com.boc.hopeheatapp.widget.channel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.boc.hopeheatapp.conversation.KbConversationView;

/**
 * 频道页面
 *
 * @author dwl
 * @date 2018/5/22.
 */
public class ChannelConversationView extends KbConversationView {

    public ChannelConversationView(Context context) {
        super(context);
    }

    public ChannelConversationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelConversationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initChannel() {
        showOrHideChannel(false);
    }

    /**
     * 请求热门问题
     */
    @Override
    protected void requestHotQuestion() {
        super.requestHotQuestion();
    }

    /**
     * 二级频道不播报欢迎语
     */
    @Override
    protected void speakWelcome() {
        // 此处不需要播报
    }
}
