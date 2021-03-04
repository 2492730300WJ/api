package com.wczx.api.common.dto.request.chat;

import com.wczx.api.common.util.page.PageRequest;
import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class ChatRequestDTO extends PageRequest {
    private Long fromUser;
    private Long toUser;

}
