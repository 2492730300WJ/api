package com.wczx.api.common.dto.request.user;

import lombok.Data;

/**
 * @author dd
 */
@Data
public class UserRequestDTO {
    Long userId;
    String userCard;
    String password;
    String token;
    String refreshToken;
    Long phone;
}
