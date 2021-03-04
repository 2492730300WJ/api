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

    /**
     * enum
     */
    private WorkStatus workStatus;

    public WorkException(WorkStatus workStatus){
        super(workStatus.getWorkMsg());
        this.workStatus = workStatus;
        this.exceptionCode = workStatus.getWorkCode();
        this.exceptionMsg = workStatus.getWorkMsg();
    }
}
