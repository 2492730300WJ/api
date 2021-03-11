package com.wczx.api.common.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * 记录信息
 * @author Administrator
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SessionUser 在 session 中的名字
     */
    public static final String SESSION_INFO = "session_info";

    /**
     * 用户账号
     */
    private String userCard;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 头像
     */
    private String avatar;



    /**
     * 姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     *  邮箱
     */
    private String userEmail;

    /**
     * 角色类型
     */
    private Integer userRole;

    /**
     *  头像
     */
    private String userUrl;

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 从 session 中获取用户信息
     */
    public static SessionInfo getInstance(){
        try{
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            return (SessionInfo) session.getAttribute(SESSION_INFO);
        } catch (Exception e) {
            throw e;
        }
    }

}
