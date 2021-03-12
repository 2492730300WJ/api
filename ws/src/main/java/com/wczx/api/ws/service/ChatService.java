package com.wczx.api.ws.service;


import com.alibaba.fastjson.JSONObject;
import com.wczx.api.common.dto.request.chat.ChatRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;

/**
 * @author Administrator
 */
public interface ChatService {

    public JSONObject friendList(UserRequestDTO userRequestDTO);

    JSONObject privateMsg(ChatRequestDTO requestDTO);
}
