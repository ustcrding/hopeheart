package com.boc.hopeheatapp.adapter;


import com.boc.hopeheatapp.model.AnswerEntity;

/**
 * Created by qsy on 2018/02/08.
 */

public interface EvaluateInterface {
    /**
     * 赞
     *
     */
    public final int FLAG_LAUD = 1;

    /**
     * 踩
     *
     */
    public final int FLAG_TREAD = 0;

    /**
     * 用户赞踩
     *
     * @param answer
     * @param flag
     */
    public void evaluate(AnswerEntity answer, int flag);
}