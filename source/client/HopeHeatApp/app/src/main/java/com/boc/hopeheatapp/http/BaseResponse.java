package com.boc.hopeheatapp.http;

import android.text.TextUtils;

/**
 * Created by qsy on 2018/1/19.
 */

public class BaseResponse<T> {
    public static final int OK = 0;
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void throwExceptionIfError() {
        if (code != OK && !TextUtils.isEmpty(message)) {
            throw new RuntimeException(message);
        }
    }
}
