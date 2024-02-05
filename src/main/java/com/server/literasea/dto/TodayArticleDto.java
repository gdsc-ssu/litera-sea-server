package com.server.literasea.dto;

import com.server.literasea.enums.ArticleCategory;
import lombok.Builder;

@Builder
public class TodayArticleDto {
    private Integer articleId;
    private ArticleCategory articleCategory;
    private String article;

}
