package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String message;
    private T data;

    @AllArgsConstructor
    public enum Code {
        SUCCESS_DEFAULT_200(200, "success"),
        FAIL_DEFAULT_500(500, "fail"),
        NO_PERMISSION_302(302, "no permission");


        private int code;
        private String message;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <V> Result<V> success(Code code, V v) {
        return new Result<>(code.code, code.message, v);
    }

    public static <V> Result<V> success(V v) {
        return success(Code.SUCCESS_DEFAULT_200, v);
    }

    public static Result<?> success(Code code) {
        return success(code, null);
    }

    public static Result<?> success() {
        return success(Code.SUCCESS_DEFAULT_200, null);
    }

    public static Result<?> fail(Code code) {
        return new Result<>(code.code, code.message);
    }

    public static Result<?> fail(Code code, String message) {
        return new Result<>(code.code, message);
    }


    public static Result<?> fail() {
        return fail(Code.FAIL_DEFAULT_500);
    }

}
