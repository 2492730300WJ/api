package com.wczx.api.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wczx.api.common.dto.request.chat.ChatRequestDTO;
import com.wczx.api.common.dto.response.chat.PrivateMsgResponseDTO;
import com.wczx.api.ws.entity.PrivateMsg;

import java.util.List;

/**
 * @Author: dd
 */
public interface PrivateMsgMapper extends BaseMapper<PrivateMsg> {

    List<PrivateMsgResponseDTO> privateMsg(ChatRequestDTO requestDTO);
}
