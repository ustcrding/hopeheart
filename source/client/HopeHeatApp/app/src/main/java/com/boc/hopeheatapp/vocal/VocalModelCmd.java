package com.boc.hopeheatapp.vocal;

/**
 * @author dwl
 * @date 2018/2/22.
 */
public class VocalModelCmd {

    // 查询模型
    public static final int MODEL_QUE = 0;

    // 删除模型
    public static final int MODEL_DEL = 1;

    // 查询
    public static final String QUERY = "query";

    // 删除
    public static final String DELETE = "delete";

    // 声纹模型状态未知
    public static final int STATUS_UNKNOWN = 0;

    // 声纹模型存在
    public static final int STATUS_EXISTED = 1;

    // 声纹模型不存在
    public static final int STATUS_UNEXISTED = 2;
}
