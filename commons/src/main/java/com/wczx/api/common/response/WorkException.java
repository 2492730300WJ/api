package com.wczx.api.common.response;

import lombok.Getter;

/**
 * 自定义异常
 * @author Administrator
 */
@Getter
public class WorkException extends RuntimeException {

    /**
     * 异常Code
     */
    private Integer exceptionCode;

    /**
     * 异常信息
     */
    private String exceptionMsg;


    public WorkException(WorkStatus workStatus){
        this.exceptionCode = workStatus.getWorkCode();
        this.exceptionMsg = workStatus.getWorkMsg();
    }

    public WorkException(Integer exceptionCode,String exceptionMsg){
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }
}
