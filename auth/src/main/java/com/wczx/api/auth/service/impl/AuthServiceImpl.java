package com.wczx.api.auth.service.impl;

import com.wczx.api.auth.service.AuthService;
import com.wczx.api.common.dto.request.auth.AuthRequest;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkStatus;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author wj
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    TokenServiceImpl tokenServiceImpl;

    @Override
    public Object auth(AuthRequest request) {
        // 从 http 请求头中取出 token
        String token = request.getToken();
        System.out.println(token);
        // 执行认证 先校验短token
        // 注意：如果jwt已经过期了，这里会抛出jwt过期异常
        try {
            Claims c = tokenServiceImpl.parseJWT(token);
            System.out.println(c.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            // 通过短Token查询长Token
            throw new WorkException(WorkStatus.LOGIN_TIME_OUT);
        }
        return true;
//        ApiDTO apiDTO = baseService.getApiByUrlAndMethod(url,httpMethod);
//        if(apiDTO == null){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("API不存在");
//
//            return result;
//        }
//
//        if(apiDTO.getStatus() != Constant.Status.ABLE){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("API暂时不开放");
//
//            return result;
//        }
//
//        ApiModuleDTO apiModuleDTO = baseService.getModuleByApiId(apiDTO.getId());
//        if(apiDTO == null){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("该API不属于任何模块");
//
//            return result;
//        }
//
//        UserModuleDTO userModuleDTO = baseService.getByModuleIdAndAccessId(apiModuleDTO.getId(),accessId);
//        if(apiDTO == null){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("accessId非法");
//
//            return result;
//        }
//        if (userModuleDTO.getStatus() != Constant.Status.ABLE){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("accessId被禁用，请联系管理员处理");
//
//            return result;
//        }
//
//        if(!DateUtil.isExpire(userModuleDTO.getExpireTime())){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("使用期限超期，请重新申请"+apiModuleDTO.getName()+"模块");
//
//            return result;
//        }
//
//        String compareSign = SignUtil.generateSignature(userModuleDTO.getSecret(), stringToSign);
//        if (!StringUtils.equals(sign, compareSign)) {
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("签名错误，请确认key或签名方式是否正确");
//
//            return result;
//        }
//
//        UserDTO userDTO = baseService.getUserById(userModuleDTO.getUserId());
//        if(apiDTO == null){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("用户不存在");
//
//            return result;
//        }
//        if(userDTO.getStatus() != Constant.Status.ABLE){
//            result.setStatus(ErrorCode.DATA_NULL);
//            result.setDescription("账户已冻结");
//
//            return result;
//        }
//
//        result.setUserCode(userDTO.getUsercode());
//        result.setUsername(userDTO.getUsername());
//        result.setServiceName(apiDTO.getServiceName());
//        result.setStatus(ErrorCode.SUCCESS);
    }
}
