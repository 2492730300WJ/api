package com.wczx.api.schedule.controller;

import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author: wj
 */
@RestController
@RequestMapping("schedule")
public class ScheduleController {

    /**
     * 获取定时任务列表
     */
    @PostMapping("/list")
    public WorkResponse getTaskList(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 保存定时任务状态
     */
    @PostMapping("/status")
    public WorkResponse saveTaskStatus(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

}
