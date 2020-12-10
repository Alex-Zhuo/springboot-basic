package com.abab.common.config;

import com.abab.common.entity.base.Result;
import com.abab.common.enums.ResponseCode;
import com.abab.common.exception.BusinessException;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 统一异常配置
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest request;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Void> validExceptionHandler(MethodArgumentNotValidException e) {
        String validFailedMsg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(",", "校验失败:", ""));

        return Result.error(ResponseCode.INVALID_PARAMETER.getCode(), validFailedMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getMessage()).append(",");
        }
        String message = strBuilder.toString();
        if (message.length() > 0) {
            message = message.substring(0, strBuilder.length() - 1);
        }
        return Result.error(ResponseCode.INVALID_PARAMETER.getCode(), message);
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> bindExceptionHandler(BindException e) {
        return Result.error(ResponseCode.INVALID_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result<Void> userExceptionHandler(IllegalArgumentException e) {
        return Result.error(ResponseCode.INVALID_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<Void> userExceptionHandler(MissingServletRequestParameterException e) {
        return Result.error(ResponseCode.INVALID_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Result<Void> tokenExpiredHandler(ExpiredJwtException e) {
        log.info("accessToken已失效：", e.getMessage());
        return Result.error(ResponseCode.INVALID_TOKEN);
    }

    @ExceptionHandler(ClaimJwtException.class)
    @ResponseBody
    public Result<Void> jwtVerificationException(ClaimJwtException e) {
        log.info("accessToken校验失败：", e.getMessage());
        return Result.error(ResponseCode.UN_AUTHENTICATION);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<Void> userExceptionHandler(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Void> unchangedExceptionHandler(Exception e) {
        log.error("请求url[{}],发生[{}]异常: {}", request.getRequestURL(), e.getClass().getName(), e);
        return Result.error(ResponseCode.SERVER_EXCEPTION);
    }

}
