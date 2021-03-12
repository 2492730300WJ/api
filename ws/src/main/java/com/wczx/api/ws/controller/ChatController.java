package com.wczx.api.ws.controller;

import com.wczx.api.common.dto.request.chat.ChatRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.ws.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: wj
 */
@RestController
@RequestMapping("ws")
public class ChatController {

    @Resource
    ChatService chatService;

    /**
     * 好友
     */
    @PostMapping("/friend")
    public WorkResponse friend(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, chatService.friendList(userRequestDTO));
    }

    /**
     * 私聊
     */
    @PostMapping("/private-msg")
    public WorkResponse privateMsg( @RequestBody ChatRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, chatService.privateMsg(requestDTO));
    }
}
