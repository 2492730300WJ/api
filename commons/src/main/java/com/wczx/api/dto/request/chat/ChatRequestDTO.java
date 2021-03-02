package com.wczx.api.dto.request.chat;

import com.wczx.api.util.page.PageRequest;
import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class ChatRequestDTO extends PageRequest {
    private Long fromUser;
    private Long toUser;

}
