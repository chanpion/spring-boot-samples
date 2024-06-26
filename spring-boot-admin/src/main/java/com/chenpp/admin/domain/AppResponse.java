package com.chenpp.admin.domain;

import lombok.Data;

/**
 * @author April.Chen
 * @date 2024/6/13 15:08
 */
@Data
public class AppResponse<T> {
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = -1;
    private static final String SUCCESS_MSG = "success";

    private int code;
    private String msg;
    private T data;
    private boolean success;

    public AppResponse() {
        this.success = true;
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
    }

    public AppResponse(String msg) {
        this.code = FAIL_CODE;
        this.success = false;
        this.msg = msg;
    }

    public AppResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppResponse(T data) {
        this.success = true;
        this.code = SUCCESS_CODE;
        this.data = data;
        this.msg = SUCCESS_MSG;
    }

    public static <T> AppResponse<T> success(T data) {
        return new AppResponse<>(data);
    }

    public static <T> AppResponse<T> success() {
        return new AppResponse<>();
    }

    public static <T> AppResponse<T> fail(String msg) {
        return new AppResponse<>(msg);
    }
}
