package com.wczx.api.user.service;

import com.wczx.api.common.dto.request.user.UserRequestDTO;

/**
 * @author Administrator
 */
public interface UserService {

    public Object login(UserRequestDTO userRequestDTO);

    public Object info(UserRequestDTO userRequestDTO);

    Object userIn(Long fromUser);
}
