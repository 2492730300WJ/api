package com.wczx.api.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wczx.api.common.dto.response.chat.FriendResponseDTO;

import java.util.List;

/**
 * @Author: dd
 */
public interface UserUserRelationMapper extends BaseMapper<UserUserRelationMapper> {
    List<FriendResponseDTO> friendList(Long userId);
}
