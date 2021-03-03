package com.wczx.api.ws.filter;

import com.wczx.api.response.WorkException;
import com.wczx.api.response.WorkResponse;
import com.wczx.api.response.WorkStatus;
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
        WorkStatus workStatus = e.getWorkStatus();
        WorkResponse response = new WorkResponse(workStatus);
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
