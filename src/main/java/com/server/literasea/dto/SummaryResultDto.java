package com.server.literasea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResultDto {

    List<SummaryResult> summaryResultList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryResult {
        Long articleId;
        Integer score1;
        Integer score2;
        Integer score3;
        Integer score4;
        Integer score5;
        Integer score6;
        String answer;
    }

}
