package com.afagoal.exception;

import com.afagoal.util.Response;
import com.fasterxml.jackson.core.JsonProcessingException;


import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.joining;

/**
 * Created by BaoCai on 18/2/11.
 * Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({DataAccessException.class})
    @ResponseBody
    public Response db(DataAccessException e) {
//        log.error("db异常: ", e);
        e.printStackTrace();
        return Response.errorDb;
    }

    @ExceptionHandler({JsonProcessingException.class})
    @ResponseBody
    public Response jackson(JsonProcessingException e) {
//        log.error("json异常", e);
        e.printStackTrace();
        return Response.errorJson;
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseBody
    public Response bindException(BindException ex) {
        ex.printStackTrace();
        List<ObjectError> allErrors = ex.getAllErrors();
        String s = allErrors.stream().map(ObjectError::getDefaultMessage).collect(joining("\n"));
        return Response.error(s);
    }

}
