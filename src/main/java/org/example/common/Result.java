package org.example.common;

import lombok.Data;

/**
 * @ClassName Result
 * @Author lsh
 * @Date 2023/7/17 15:50
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T obj;

    public Result() {
    }

    public Result(Integer code, String msg, T obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public static <T> Result<T> success(T obj) {
        return new Result<>(200, "success", obj);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> failed() {
        return new Result<>(500, "failed", null);
    }

    public static <T> Result<T> failed(T msg) {
        return new Result<>(500, "failed", msg);
    }
}
