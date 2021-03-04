package com.wczx.api.common.dto.response.chat;

import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class FriendResponseDTO {
    private Long friendId;
    private String avatar;
    private String friendName;
    private String remark;
}
