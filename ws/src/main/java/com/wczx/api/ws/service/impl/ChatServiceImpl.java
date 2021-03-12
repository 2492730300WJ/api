package com.wczx.api.ws.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.common.dto.request.chat.ChatRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.dto.response.chat.FriendResponseDTO;
import com.wczx.api.common.dto.response.chat.PrivateMsgResponseDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.ws.mapper.PrivateMsgMapper;
import com.wczx.api.ws.mapper.UserUserRelationMapper;
import com.wczx.api.ws.service.ChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: dd
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    UserUserRelationMapper userUserRelationMapper;

    @Resource
    PrivateMsgMapper privateMsgMapper;


    @Override
    public JSONObject friendList(UserRequestDTO userRequestDTO) {
        if (null == userRequestDTO.getUserId()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        List<FriendResponseDTO> friendList = userUserRelationMapper.friendList(userRequestDTO.getUserId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("friends", friendList);
        return jsonObject;
    }

    @Override
    public JSONObject privateMsg(ChatRequestDTO requestDTO) {
        if (null == requestDTO.getFromUser() || null == requestDTO.getToUser()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        List<PrivateMsgResponseDTO> list = privateMsgMapper.privateMsg(requestDTO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", list);
        return jsonObject;
    }
}
