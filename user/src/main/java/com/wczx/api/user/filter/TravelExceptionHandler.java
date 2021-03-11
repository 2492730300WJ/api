package com.wczx.api.user.filter;

import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理类
 * @author Administrator
 */
@ControllerAdvice
public class TravelExceptionHandler {

    /**
     * WorkException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = WorkException.class)
    @ResponseBody
    public WorkResponse bizExceptionHandle(WorkException e) {
        e.printStackTrace();
        WorkResponse response = new WorkResponse(e.getExceptionCode(),e.getExceptionMsg());
        return response;
    }


    /**
     * 所有异常处理处理类
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WorkResponse ExceptionHandle(Exception e) {
        e.printStackTrace();
        WorkStatus workStatus = WorkStatus.FAIL;
        WorkResponse response = new WorkResponse(workStatus);
        return response;
    }


}
