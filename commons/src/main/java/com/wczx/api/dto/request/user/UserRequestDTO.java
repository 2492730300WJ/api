package com.wczx.api.dto.request.user;

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
