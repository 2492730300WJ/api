package com.wczx.api.common.response;

import lombok.Getter;

/**
 * @Author: dd
 */
@Getter
public enum WorkStatus {
    // 200 : auth and user
    SUCCESS(200, "请求成功"),
    FAIL(201,"系统出现异常"),
    PASSWORD_IS_ERROR(201,"用户名或密码错误"),
    THIS_ROLE_DO_NOT_LOGIN(203,"该角色无法登录此系统"),
    LOGIN_TIME_OUT(204,"登录已过期，请重新登录"),
    PLEASE_LOGIN(205,"请先登录您的账号"),
    CHECK_PARAM(206,"请检查传入参数"),
    AUTH_ERROR(207,"权限校验异常"),
    // 300 : article
    ARTICLE_NOT_FOUND(300,"未找到该文章"),
    ARTICLE_STAR_EXIST(301,"您已经点赞过了")
    ;

    /**
     * 标准响应状态码
     */
    private Integer workCode;

    /**
     * 响应状态描述
     */
    private String workMsg;

    WorkStatus(int workCode, String workMsg) {
        this.workCode = workCode;
        this.workMsg = workMsg;
    }

    public void setWorkCode(Integer workCode) {
        this.workCode = workCode;
    }

    public void setWorkMsg(String workMsg) {
        this.workMsg = workMsg;
    }
}
