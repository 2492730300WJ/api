package com.wczx.api.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wczx.api.common.constant.CacheConstant;
import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.common.session.SessionInfo;
import com.wczx.api.feign.client.AuthClient;
import com.wczx.api.feign.client.CacheClient;
import com.wczx.api.user.entity.User;
import com.wczx.api.user.mapper.UserMapper;
import com.wczx.api.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.wczx.api.common.dto.response.user.UserInfoResponseDTO;

import javax.annotation.Resource;


/**
 * @Author: dd
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthClient authClient;

    @Resource
    private CacheClient cacheClient;

    @Override
    public Object login(UserRequestDTO userRequestDTO) {
        JSONObject jsonObject = new JSONObject();
        User userInfo = null;
        // 手机号登录
        if (null != userRequestDTO.getPhone()) {
            userInfo = userMapper.selectOne(new QueryWrapper<User>().eq("phone", userRequestDTO.getPhone()));
            if (userInfo == null) {
                throw new WorkException(WorkStatus.PASSWORD_IS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(userRequestDTO.getUserCard())) {
            userInfo = userMapper.selectOne(new QueryWrapper<User>().eq("user_card", userRequestDTO.getUserCard()).eq("user_password", userRequestDTO.getPassword()));
            if (userInfo == null) {
                throw new WorkException(WorkStatus.PASSWORD_IS_ERROR);
            }
        }

        // 存入session
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(userInfo.getUserId());
        sessionInfo.setUserName(userInfo.getUserName());
        sessionInfo.setUserCard(userInfo.getUserCard());
        sessionInfo.setAvatar(userInfo.getAvatar());

        // 登陆时获取长Token和短Token
        String token = authClient.token(sessionInfo).getData().toString();
        String refreshToken = authClient.refreshToken(sessionInfo).getData().toString();
        jsonObject.put("token", token);
        jsonObject.put("refreshToken", refreshToken);
        // 存入redis
        CacheCommonRequestDTO requestDTO = new CacheCommonRequestDTO();
        requestDTO.setKey(CacheConstant.TOKEN_PREFIX + userInfo.getUserId().toString());
        requestDTO.setExpireSeconds(86400);
        requestDTO.setValue(token);
        cacheClient.set(requestDTO);
        requestDTO.setKey(CacheConstant.REFRESH_TOKEN_PREFIX + userInfo.getUserId().toString());
        requestDTO.setExpireSeconds(604800);
        requestDTO.setValue(refreshToken);
        cacheClient.set(requestDTO);
        jsonObject.put("user", sessionInfo);
        return jsonObject;
    }

    @Override
    public JSONObject info(UserRequestDTO userRequestDTO) {
        if (null == userRequestDTO.getUserId()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        UserInfoResponseDTO userInfo = userMapper.userInfo(userRequestDTO.getUserId());
        JSONObject jsonObject = new JSONObject();
        // 计算成长值百分比
        int progress = Math.round(userInfo.getGrow() / userInfo.getLevel());
        userInfo.setProgressWidth(progress + "%");
        jsonObject.put("user", userInfo);
        return jsonObject;
    }

    @Override
    public Object userIn(Long fromUser) {
        UserInfoResponseDTO userInfo = userMapper.userInfo(fromUser);
        return userInfo;
    }
}
