package com.wczx.api.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wczx.api.common.dto.response.user.UserInfoResponseDTO;
import com.wczx.api.user.entity.User;

/**
 * @author dd
 * @since 2020-07-27
 */
public interface UserMapper extends BaseMapper<User> {
    UserInfoResponseDTO userInfo(Long userId);
}
