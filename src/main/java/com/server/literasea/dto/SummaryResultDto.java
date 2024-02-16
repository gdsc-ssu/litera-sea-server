package com.server.literasea.dto;

import lombok.Builder;

@Builder
public class SummaryResultDto {
    Long articleId;
    Integer score;
    String answer;
}
