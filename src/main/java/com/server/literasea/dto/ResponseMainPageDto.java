package com.server.literasea.dto;

import lombok.Builder;

@Builder
public class ResponseMainPageDto {
    private String nickname;
    private int location;
    private int level;
    private int exp;
}
