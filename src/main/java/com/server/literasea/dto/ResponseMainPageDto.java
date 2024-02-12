package com.server.literasea.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseMainPageDto {
    private String nickname;
    private int location;
    private int level;
    private int exp;
}
