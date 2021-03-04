package com.wczx.api.common.dto.response.app;

import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class AppUpdateResponseDTO {
    private String status;

    private String wgtUrl;

    private String pkgUrl;

    private String versionName;

    private String tips;
}
