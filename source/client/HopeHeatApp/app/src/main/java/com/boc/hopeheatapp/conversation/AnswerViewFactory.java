package com.boc.hopeheatapp.conversation;

import android.content.Context;

import com.boc.hopeheatapp.http.BaseResponse;
import com.boc.hopeheatapp.model.AnswerEntity;
import com.boc.hopeheatapp.model.AnswerItem;
import com.boc.hopeheatapp.model.MsgEntity;
import com.boc.hopeheatapp.util.string.StringUtil;
import com.boc.hopeheatapp.widget.AnswerView;
import com.boc.hopeheatapp.widget.ErrorAnswerView;
import com.boc.hopeheatapp.widget.HtmlAnswerView;
import com.boc.hopeheatapp.widget.MultiAnswerView;
import com.boc.hopeheatapp.widget.PictureAnswerView;
import com.boc.hopeheatapp.widget.SingleAnswerNoZcView;
import com.boc.hopeheatapp.widget.SingleAnswerView;

import java.util.ArrayList;
import java.util.List;

/**
 * AnswerView工厂类
 *
 * @author dwl
 * @date 2018/2/11.
 */
public class AnswerViewFactory {

    /**
     * 根据结果生成answerview
     *
     * @param context
     * @param response
     * @return
     */
    public static AnswerView makeAnswerView(Context context, BaseResponse<AnswerEntity> response) {
        if (context == null || response == null) {
            return null;
        }

        AnswerView answerView = null;
        if (response.getCode() == BaseResponse.OK) {
            MsgEntity msgEntity = MsgEntity.genAnswerMsg();
            msgEntity.setContent(response);

            AnswerEntity answer = response.getData();
            List<AnswerItem> list = answer.getList();
            if (list == null || list.isEmpty()) {
                answerView = new ErrorAnswerView(context);
            } else if (list.size() == 1) {
                answerView = handelSingleAnswer(context, answer);
            } else {
                answerView = new MultiAnswerView(context);
            }
            answerView.setMsg(msgEntity);
        } else {
            answerView = new ErrorAnswerView(context);
            //String tip = String.format(context.getString(R.string.voice_error_format), response.getMessage());
            String tip = response.getMessage();
            answerView.setErrorMsg(tip);
        }

        return answerView;
    }

    /**
     * 根据结果生成answerview
     *
     * @param context
     * @param content
     * @return
     */
    public static AnswerView makeAnswerView(Context context, String content) {
        if (context == null || StringUtil.isEmpty(content)) {
            return null;
        }

        BaseResponse<AnswerEntity> response = new BaseResponse<>();
        response.setCode(BaseResponse.OK);
        response.setMessage("success");

        AnswerEntity answerEntity = new AnswerEntity();
        AnswerItem answerItem = new AnswerItem();
        answerItem.setAnswer(content);
        List<AnswerItem> list = new ArrayList<>();
        list.add(answerItem);
        answerEntity.setList(list);
        response.setData(answerEntity);

        AnswerView answerView = new SingleAnswerNoZcView(context);

        MsgEntity msgEntity = MsgEntity.genAnswerMsg();
        msgEntity.setContent(content);
        answerView.setMsg(msgEntity);
        return answerView;
    }

    private static AnswerView handelSingleAnswer(Context context, AnswerEntity answer) {
        AnswerView answerView = null;
        AnswerItem answerItem = answer.getList().get(0);
        if (answer.getContentType() == AnswerEntity.CONTENT_TYPE_GREET) {
            answerView = new SingleAnswerNoZcView(context);
        } else if (answer.getContentType() == AnswerEntity.CONTENT_TYPE_VOICE_NAVE) {
            answerView = new SingleAnswerNoZcView(context);
        } else if (StringUtil.isNotBlank(answerItem.getAnswerUrl())) {
            String url = answerItem.getAnswerUrl();
            if (StringUtil.contains(url, ".html") || StringUtil.contains(url, ".htm")) {
                answerView = new HtmlAnswerView(context);
            } else {
                answerView = new PictureAnswerView(context);
            }
        } else {
            answerView = new SingleAnswerView(context);
        }
        return answerView;
    }
}
