package com.server.literasea.dto;

import com.server.literasea.entity.Badge;
import lombok.Builder;

@Builder
public class BadgeInfoDto {
    private String name;
    private String image;

    public static BadgeInfoDto badgeToDto(Badge badge){
        return BadgeInfoDto.builder()
                .name(badge.getName())
                .image(badge.getImage())
                .build();
    }
}
