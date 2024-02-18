package com.server.literasea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDto {

    List<UserSummary> userSummaryList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSummary {
        Long articleId;
        String summary;
    }
}
