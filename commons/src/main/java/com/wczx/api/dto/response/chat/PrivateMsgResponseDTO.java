package com.wczx.api.dto.response.chat;

import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class PrivateMsgResponseDTO {
    private Long fromUser;

    private Long toUser;

    private String message;

    private Integer type;

    private String isMy;

    private String time;

    private String id;

    private String avatar;
}
