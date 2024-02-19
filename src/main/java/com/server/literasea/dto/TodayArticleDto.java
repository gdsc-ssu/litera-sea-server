package com.server.literasea.dto;

import com.server.literasea.enums.ArticleCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TodayArticleDto {

    List<TodayArticle> todayArticleList;

    @Builder
    @Getter
    public static class TodayArticle {
        private Long articleId;
        private ArticleCategory articleCategory;
        private String article;
    }

}
