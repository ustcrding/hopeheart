package com.boc.hopeheatapp.adapter;


import com.boc.hopeheatapp.model.AnswerEntity;

/**
 * Created by qsy on 2018/02/08.
 */

public interface LinkInterface {
    /**
     * 通过链接提问
     *
     * @param answer
     * @param index
     */
    public void answerLink(AnswerEntity answer, int index);
}
