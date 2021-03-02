package com.wczx.api.ws.ws.dto;


import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class CommonMsgRequestDTO {
    private Long fromUser;

    private Long toUser;

    private String message;

    private Integer type;

    private Integer msgType;

    private String isMy;
}
