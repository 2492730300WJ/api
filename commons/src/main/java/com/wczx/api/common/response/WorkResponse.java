package com.wczx.api.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Object data;
    /**
     * enum
     */
    @JsonIgnore
    private WorkStatus workStatus;

    public WorkResponse(WorkStatus workStatus,Object data){
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

    public WorkResponse(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
