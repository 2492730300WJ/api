package com.wczx.api.ws.ws;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.common.constant.Constant;
import com.wczx.api.common.dto.response.user.UserInfoResponseDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.common.util.TimeUtil;
import com.wczx.api.ws.entity.PrivateMsg;
import com.wczx.api.ws.feign.UserFeignClient;
import com.wczx.api.ws.mapper.PrivateMsgMapper;
import com.wczx.api.ws.ws.dto.CommonMsgRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Administrator
 * @ServerEndpoint 这个注解有什么作用？
 * <p>
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 */

@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocket {

    @Resource
    UserFeignClient userFeignClient;

    @Resource
    PrivateMsgMapper privateMsgMapper;

    private static WebSocket webSocket;


    @PostConstruct
    public void init() {
        webSocket = this;
        // 初使化时将已静态化的configParam实例化
        webSocket.userFeignClient = this.userFeignClient;
        webSocket.privateMsgMapper = this.privateMsgMapper;
    }

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) {
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：{}", webSocketSet.size());
    }


    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：{}", webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message) {
        CommonMsgRequestDTO dto = JSONObject.parseObject(message, CommonMsgRequestDTO.class);
        log.info("[WebSocket] 收到消息：{}", message);
        // 私聊
        if (1 == dto.getMsgType()) {
            PrivateMsg privateMsg = new PrivateMsg();
            privateMsg.setFromUser(dto.getFromUser());
            privateMsg.setToUser(dto.getToUser());
            privateMsg.setMessage(dto.getMessage());
            privateMsg.setType(dto.getType());
            privateMsg.setIsDelete("N");
            privateMsg.setIsRead("N");
            privateMsg.setIsOld("N");
            privateMsg.setTime(TimeUtil.getNormalTime());
            webSocket.privateMsgMapper.insert(privateMsg);
            JSONObject fromMsg = JSONObject.parseObject(message);
            JSONObject toMsg = JSONObject.parseObject(message);
            WorkResponse workResponse = webSocket.userFeignClient.infoIn(dto.getFromUser());
            if (!WorkStatus.SUCCESS.equals(workResponse.getCode())){
                throw new WorkException(WorkStatus.FAIL);
            }
            UserInfoResponseDTO from = JSONObject.parseObject((byte[]) workResponse.getData(),UserInfoResponseDTO.class);
            toMsg.put("isMy", "N");
            toMsg.put("avatar", from.getAvatar());
            toMsg.put("time", privateMsg.getTime());
            toMsg.put("id",privateMsg.getId());
            AppointSending(dto.getToUser().toString(), toMsg.toJSONString());
            fromMsg.put("isMy", "Y");
            fromMsg.put("avatar", from.getAvatar());
            fromMsg.put("id",privateMsg.getId());
            fromMsg.put("time", privateMsg.getTime());
            AppointSending(dto.getFromUser().toString(), fromMsg.toJSONString());

        }
        // 群聊
        if (2 == dto.getMsgType()) {
            log.info("[WebSocket] 群聊开发中");
        }

    }

    /**
     * 群发
     *
     * @param message
     */
    public void GroupSending(String message) {
        for (String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.info("[WebSocket] 发送私聊失败 --> " + e.getMessage());
            }
        }
    }

    /**
     * 指定发送
     *
     * @param name
     * @param message
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
            log.info("[WebSocket] 发送私聊成功");
        } catch (Exception e) {
            log.info("[WebSocket] 不在线 ");
            e.printStackTrace();
        }
    }
}
