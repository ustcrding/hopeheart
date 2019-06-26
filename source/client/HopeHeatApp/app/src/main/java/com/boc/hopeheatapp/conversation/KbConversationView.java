package com.boc.hopeheatapp.conversation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.boc.hopeheatapp.ActivityJumper;
import com.boc.hopeheatapp.R;
import com.boc.hopeheatapp.adapter.EvaluateInterface;
import com.boc.hopeheatapp.adapter.LinkInterface;
import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.http.VoidResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.CatalogListEntity;
import com.boc.hopeheatapp.model.ChannelEntity;
import com.boc.hopeheatapp.model.HotQuestionEntity;
import com.boc.hopeheatapp.model.HotQuestionListEntity;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.service.biz.CatalogLoader;
import com.boc.hopeheatapp.service.biz.RobotLoader;
import com.boc.hopeheatapp.setting.BocSettings;
import com.boc.hopeheatapp.tts.TtsManager;
import com.boc.hopeheatapp.util.json.JsonUtils;
import com.boc.hopeheatapp.util.log.Logger;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.AnswerView;
import com.boc.hopeheatapp.widget.channel.GridItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;

/**
 * 知识库对话界面
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class KbConversationView extends ConversationView implements EvaluateInterface, LinkInterface {

    private static final String TAG = "KbConversationView";
    private final long OVER_TIME = 1000 * 60 * 60 * 24L;

    public KbConversationView(Context context) {
        super(context);
    }

    public KbConversationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KbConversationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initDefaultVoiceGuide() {
        String guide = getContext().getString(R.string.voice_assist_speaking_sample);
        String[] items = guide.split("\n");

        setVoiceGuide(Arrays.asList(items));
    }

    @Override
    protected void initVoiceGuide() {
        readHotQuestion();

        speakWelcome();
    }

    /**
     * 请求热门问题
     */
    @Override
    protected void requestHotQuestion() {
        RobotLoader robotLoader = new RobotLoader();
        robotLoader.hotQuestion(getChannelId()).subscribe(new Subscriber<HotQuestionListEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                handleRequestError(throwable);
            }

            @Override
            public void onNext(HotQuestionListEntity hotQuestionListEntity) {
                handleRequestSuccess(hotQuestionListEntity);
                saveHotQuestion(hotQuestionListEntity);
            }
        });
    }

    private void handleRequestSuccess(HotQuestionListEntity hotQuestionListEntity) {
        if (hotQuestionListEntity == null || hotQuestionListEntity.getList() == null) {
            return;
        }

        if (Logger.isDebugable()) {
            Logger.d(TAG, "handleRequestSuccess");
        }
        List<String> list = new ArrayList<>();
        for (HotQuestionEntity entity : hotQuestionListEntity.getList()) {
            list.add(entity.getQuestion());
        }

        setVoiceGuide(list);
    }

    private void handleRequestError(Throwable throwable) {
        if (Logger.isDebugable()) {
            Logger.e(TAG, "handleRequestError error" + throwable.getMessage(), throwable);
        }
        initDefaultVoiceGuide();
    }

    private void saveHotQuestion(HotQuestionListEntity hotQuestionListEntity) {
        if (hotQuestionListEntity == null || hotQuestionListEntity.getList() == null || hotQuestionListEntity.getList().isEmpty()) {
            return;
        }


        String json = JsonUtils.toJson(hotQuestionListEntity);
        BocSettings.getInstance().setSetting(getHotQuestionKey(), json);
        BocSettings.getInstance().setSetting(BocSettings.LAST_HOT_QUESTION_TIME_KB, System.currentTimeMillis());
    }

    private String getHotQuestionKey() {
        String catalogId  = channelEntity == null ? "" : channelEntity.getId();
        return BocSettings.HOT_QUESTION_KB + catalogId;
    }

    private void readHotQuestion() {
        // 读取缓存中的热门问题
        String json = BocSettings.getInstance().getString(getHotQuestionKey());
        if (StringUtil.isNotBlank(json)) {
            HotQuestionListEntity hotQuestion = JsonUtils.fromJson(json, HotQuestionListEntity.class);
            if (hotQuestion != null && hotQuestion.getList() != null && !hotQuestion.getList().isEmpty()) {
                handleRequestSuccess(hotQuestion);
            } else {
                initDefaultVoiceGuide();
            }
        } else {
            initDefaultVoiceGuide();
        }

        long lastRequestTime = BocSettings.getInstance().getLong(BocSettings.LAST_HOT_QUESTION_TIME_KB);
        if ((System.currentTimeMillis() - lastRequestTime) > OVER_TIME) {
            requestHotQuestion();
            return;
        }
    }

    // 请求答案
    @Override
    protected void requestQuestion(String content, int type, String questionId) {
        RobotLoader loader = new RobotLoader();

        loader.ask(content, type, questionId, getChannelId()).subscribe(new Subscriber<BaseResponse<AnswerEntity>>() {
            @Override
            public void onCompleted() {
                //addAnswer("请求已发送完成！");
            }

            @Override
            public void onError(Throwable throwable) {
                //addAnswer("异常信息：" + StringUtil.trimToEmpty(throwable.getMessage()));
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "requestQuestion.onError ", throwable);
                }

                if (getContext() == null) {
                    return;
                }

                showTip(ConversationExceptionHelper.genRandonErrorTip(getContext()));
            }

            @Override
            public void onNext(BaseResponse<AnswerEntity> response) {
                handleRequestResult(response);
            }
        });
    }

    private void handleRequestResult(BaseResponse<AnswerEntity> response) {
        if (getContext() == null) {
            return;
        }

        try {
            addAnswer(response);
            if (response.getCode() == BaseResponse.OK) {
                AnswerEntity answer = response.getData();
                List<AnswerItem> list = answer.getList();
                int size = list.size();
                AnswerItem item = null;
                if (size == 1) {
                    item = list.get(0);
                    TtsManager.getInstance(getContext().getApplicationContext()).speak(item.getAnswer(), null);
                } /*else if(size > 1) {
                            //addAnswer("找到多条答案：" + size);
                            //text = getString(R.string.text_advise);
                        } else {
                            //addAnswer("抱歉，没有找到答案");
                        }*/

            } else {
                //addAnswer("错误信息：" + response.getMessage());
                String errorMsg = response.getMessage();
                TtsManager.getInstance(getContext().getApplicationContext()).speak(errorMsg, null);
            }
        } catch (Exception e) {
            //addAnswer("异常信息：" + StringUtil.trimToEmpty(e.getMessage()));
            if (Logger.isDebugable()) {
                Logger.e(TAG, "requestQuestion.onError ", e);
            }
            Toast.makeText(getContext(), "异常信息：" + StringUtil.trimToEmpty(e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    // 赞踩
    private void markAnswer(String sessionId, String itemId, int flag) {
        RobotLoader loader = new RobotLoader();

        loader.mark(sessionId, itemId, flag).subscribe(new Subscriber<VoidResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "mark.onError ", throwable);
                }
            }

            @Override
            public void onNext(VoidResponse response) {
            }
        });
    }

    // 选择
    private void selectAnswer(String sessionId, String itemId, String questionId) {
        RobotLoader loader = new RobotLoader();

        loader.select(sessionId, itemId, questionId).subscribe(new Subscriber<VoidResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if (Logger.isDebugable()) {
                    Logger.e(TAG, "slect.onError ", throwable);
                }
            }

            @Override
            public void onNext(VoidResponse response) {
            }
        });
    }

    @Override
    public void answerLink(AnswerEntity answer, int index) {
        try {
            AnswerItem item = answer.getList().get(index);
            // request
            startRequest(item.getQuestion(), MsgEntity.SOURCE_LINK, item.getQuestionId());

            // select
            selectAnswer(answer.getSessionId(), item.getItemId(), item.getQuestionId());
        } catch (Exception e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "answerLink.onError ", e);
            }
        }
    }

    @Override
    public void evaluate(AnswerEntity answer, int flag) {
        try {
            AnswerItem item = answer.getList().get(0);
            markAnswer(answer.getSessionId(), item.getItemId(), flag);
        } catch (Exception e) {
            if (Logger.isDebugable()) {
                Logger.e(TAG, "evaluate.onError ", e);
            }
        }
    }

    private void addAnswer(BaseResponse<AnswerEntity> content) {
        MsgEntity msgEntity = MsgEntity.genAnswerMsg();
        msgEntity.setContent(content);
        AnswerView answerView = AnswerViewFactory.makeAnswerView(getContext(), content);
        answerView.setEvaluateInterface(this);
        answerView.setLinkInterface(this);

        addConversationView(answerView);
    }

    @Override
    public void initChannel() {
        /*ChannelEntity channelEntity1 = new ChannelEntity();
        channelEntity1.setName("信用卡");

        ChannelEntity channelEntity2 = new ChannelEntity();
        channelEntity2.setName("网点排队");
        channelEntity2.setType(ChannelEntity.TYPE_OPEN_URL);
        channelEntity2.setOpenUrl("http://mbs.boc.cn");

        ChannelEntity channelEntity3 = new ChannelEntity();
        channelEntity3.setName("信用卡");

        ChannelEntity channelEntity4 = new ChannelEntity();
        channelEntity4.setName("信用卡");

        ChannelEntity channelEntity5 = new ChannelEntity();
        channelEntity5.setName("信用卡");

        ChannelEntity channelEntity6 = new ChannelEntity();
        channelEntity6.setName("信用卡");

        ChannelEntity channelEntity7 = new ChannelEntity();
        channelEntity7.setName("信用卡");

        ChannelEntity channelEntity8 = new ChannelEntity();
        channelEntity8.setName("借记卡");

        ChannelEntity channelEntity9 = new ChannelEntity();
        channelEntity9.setName("更多");
        channelEntity9.setType(ChannelEntity.TYPE_MORE);

        List<ChannelEntity> list = new ArrayList<>();
        list.add(channelEntity1);
        list.add(channelEntity2);
        list.add(channelEntity3);
        list.add(channelEntity4);
        list.add(channelEntity5);
        list.add(channelEntity6);
        list.add(channelEntity7);
        list.add(channelEntity8);
        list.add(channelEntity9);

        setChannelData(list);*/

        setChannelItemClickListener(new GridItemClickListener() {
            @Override
            public void onGridItemClick(View view, ChannelEntity channelEntity) {
                if (getContext() != null) {
                    if (StringUtil.equals(channelEntity.getType(), ChannelEntity.TYPE_OPEN_URL)) {
                        ActivityJumper.startBrowserActivity(getContext(), channelEntity.getOpenUrl());
                    } else if (StringUtil.equals(channelEntity.getType(), ChannelEntity.TYPE_MORE)) {
                        ActivityJumper.startChannelListActivity(getContext(), null);
                    } else {
                        ActivityJumper.startChannelActivity(getContext(), channelEntity);
                    }
                }
            }
        });

        requestChannel();
    }

    private void requestChannel() {
        CatalogLoader catelogLoader = new CatalogLoader();
        catelogLoader.get().subscribe(new Subscriber<CatalogListEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(CatalogListEntity catalogListEntity) {
                //handleLoginSuccess(username, pwd, userEntity);
                if (catalogListEntity != null) {
                    ChannelEntity channelEntity = new ChannelEntity();
                    channelEntity.setName("更多");
                    channelEntity.setType(ChannelEntity.TYPE_MORE);
                    List<ChannelEntity> list = new ArrayList<>();
                    list.addAll(catalogListEntity.getList());
                    list.add(channelEntity);

                    setChannelData(list);
                }
            }
        });
    }
}
