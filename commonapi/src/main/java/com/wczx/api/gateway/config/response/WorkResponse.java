package com.wczx.api.gateway.config.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.MDC;

/**
 * @Author: dd
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkResponse {
    /**
     * 异常Code
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;

    /**
     * JSONObject
     */
    private JSONObject data;

    /**
     * 操作ID
     */
    private String operatingId = MDC.get("operatingId");
    /**
     * enum
     */
    @JsonIgnore
    private WorkStatus workStatus;

    public WorkResponse(WorkStatus workStatus,JSONObject data){
        this.data = data;
        this.workStatus = workStatus;
        if(workStatus ==null){
            workStatus = WorkStatus.SUCCESS;
        }
        this.code = workStatus.getWorkCode();
        this.msg = workStatus.getWorkMsg();
    }

    public WorkResponse(WorkStatus workStatus){
        this.workStatus = workStatus;
        if(workStatus ==null){
            workStatus = WorkStatus.SUCCESS;
        }
        this.code = workStatus.getWorkCode();
        this.msg = workStatus.getWorkMsg();
    }
}
