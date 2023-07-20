package org.example.advice;

import org.example.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @ClassName ControllerAdvice
 * @Author lsh
 * @Date 2023/7/18 16:09
 */
@RestControllerAdvice(basePackages = {"org.example"})
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        ex.printStackTrace();
        return Result.failed(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new Result<>(HttpStatus.FORBIDDEN.value(), "用户权限不足", null);
    }

    @ExceptionHandler({RuntimeException.class})
    public Result<String> handleException(RuntimeException ex) {
        ex.printStackTrace();
        return Result.failed(ex.getMessage());
    }
}
