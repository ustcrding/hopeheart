package com.boc.hopeheatapp.conversation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.model.NaviEntity;
import com.boc.hopeheatapp.navi.NaviInterface;
import com.boc.hopeheatapp.service.biz.RobotLoader;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.AnswerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * 语音导航对话页面
 *
 * @author dwl
 * @date 2018/2/12.
 */
public class VoiceNaviView extends ConversationView {

    public static final String TAG = "VoiceNaviView";

    public VoiceNaviView(Context context) {
        super(context);

        initNavi();
    }

    public VoiceNaviView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initNavi();
    }

    protected void initNavi() {
        navigator = new HashMap<String, NaviInterface>();

        NaviInterface openSetting = new NaviInterface() {
            @Override
            public void navigate(NaviEntity navi) {
                String param = navi.getMenu();
                ActivityJumper.startSettingActivity(getContext());
                String tip = String.format(getContext().getString(R.string.open_format_style), "设置");
                addAnserString(tip);
            }
        };
        navigator.put("设置", openSetting);

        NaviInterface openSchedule = new NaviInterface() {
            @Override
            public void navigate(NaviEntity navi) {
                String param = navi.getMenu();
                ActivityJumper.startScheduleSystemctivity(getContext());
                String tip = String.format(getContext().getString(R.string.open_format_style), "排班表");
                addAnserString(tip);
            }
        };
        navigator.put("排班", openSchedule);
        navigator.put("班表", openSchedule);

        NaviInterface openWorkbench = new NaviInterface() {
            @Override
            public void navigate(NaviEntity navi) {
                String param = navi.getMenu();
                String tip = String.format(getContext().getString(R.string.open_format_style), "工作台");
                ActivityJumper.startWorkbenchActivity(getContext());
                addAnserString(tip);
            }
        };
        navigator.put("工作台", openWorkbench);
    }

    @Override
    protected void requestQuestion(final String content, int type, String questionId) {
        RobotLoader loader = new RobotLoader();

        loader.navi(content).subscribe(new Subscriber<BaseResponse<NaviEntity>>() {


            @Override
            public void onCompleted() {
                //addAnswer("请求已发送完成！");
            }

            @Override
            public void onError(Throwable throwable) {
                showTip(ConversationExceptionHelper.genRandonErrorTip(getContext()));
            }

            @Override
            public void onNext(BaseResponse<NaviEntity> response) {
                handleResponse(content, response);
            }
        });
    }

    private void handleResponse(String content, BaseResponse<NaviEntity> response) {
        try {
            if (response.getCode() == BaseResponse.OK) {
                for (Map.Entry<String, NaviInterface> entry : navigator.entrySet()) {
                    if (StringUtil.contains(content, entry.getKey())) {
                        entry.getValue().navigate(response.getData());
                        break;
                    }
                }
            } else {
                String tip = getContext().getString(R.string.tip_unsupport_operator);
                addAnserString(tip);
            }
        } catch (Exception e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "hijackRequest.onError ", e);
            }
            showTip(ConversationExceptionHelper.genRandonErrorTip(getContext()));
        }
    }

    @Override
    protected void initVoiceGuide() {
        String guide = getContext().getString(R.string.vocice_navi_sample);
        String[] items = guide.split("\n");

        setVoiceGuide(Arrays.asList(items));
    }

    private void addAnserString(String tip) {
        BaseResponse<AnswerEntity> response = new BaseResponse<>();
        response.setCode(BaseResponse.OK);
        response.setMessage("success");

        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContentType(AnswerEntity.CONTENT_TYPE_VOICE_NAVE);
        AnswerItem answerItem = new AnswerItem();
        answerItem.setAnswer(tip);
        List<AnswerItem> list = new ArrayList<>();
        list.add(answerItem);
        answerEntity.setList(list);
        response.setData(answerEntity);
        addAnswer(response);
        TtsManager.getInstance(getContext().getApplicationContext()).speak(tip, null);
    }

    private void addAnswer(BaseResponse<AnswerEntity> content) {
        MsgEntity msgEntity = MsgEntity.genAnswerMsg();
        msgEntity.setContent(content);
        AnswerView answerView = AnswerViewFactory.makeAnswerView(getContext(), content);

        addConversationView(answerView);
    }

}
