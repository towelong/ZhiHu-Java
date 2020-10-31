/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/14 22:22
 */
package com.towelong.zhihu.common.core;

import com.towelong.zhihu.common.UnifyResponse;
import com.towelong.zhihu.common.configuration.CodeMessageConfiguration;
import com.towelong.zhihu.common.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
* 全局异常处理
* */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private CodeMessageConfiguration codeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(Exception e) {
        UnifyResponse message = new UnifyResponse(9999,"服务器开小差了");
        System.out.println(e);
        return message;
    }

    @ExceptionHandler(HttpException.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handleHttpException(HttpException e) {
        UnifyResponse message = new UnifyResponse(e.getCode(),codeConfiguration.getMessage(e.getCode()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        assert httpStatus != null;
        return new ResponseEntity<>(message,headers,httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public UnifyResponse handleBeanException(MethodArgumentNotValidException e) {
        List<ObjectError> errors =  e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorsMessages(errors);
        return new UnifyResponse(10001, message);
    }

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        return new UnifyResponse(10001, formatConstraintException(errors));
    }



    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public UnifyResponse handleNoHandlerFoundException(){
        return new UnifyResponse(10002,"资源未找到");
    }




    private String formatAllErrorsMessages(List<ObjectError> errors){
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(','));
        return errorMsg.toString();
    }

    private String formatConstraintException(Set<ConstraintViolation<?>> constraintViolations){
        StringBuilder builder = new StringBuilder();
        constraintViolations.forEach(constraintViolation -> builder.append(constraintViolation.getMessage()));
        return builder.toString();
    }
}
