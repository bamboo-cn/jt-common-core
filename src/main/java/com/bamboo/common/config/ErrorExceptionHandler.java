package com.bamboo.common.config;

import com.bamboo.common.constant.PublicCode;
import com.bamboo.common.exception.GlobException;
import com.bamboo.common.exception.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
public class ErrorExceptionHandler {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GlobException.class)
    public Object exceptionHandler(HttpServletRequest request, GlobException e) {
        return new ResponseEntity<Error>(e.getError(),  HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object exceptionCatchedHandler(HttpServletRequest request, Exception e) {
        log.error("Exception", e);
        return new ResponseEntity<Error>(PublicCode.system_error.error(), this.getStatus(request));
    }

    //获取HttpStatus
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
