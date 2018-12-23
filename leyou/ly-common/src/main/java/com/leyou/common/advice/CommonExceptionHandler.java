package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//拦截所有controller的类
public class CommonExceptionHandler {
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handleException(LyException e){
        //ExceptionEnum em=e.getExceptionEnum();
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
