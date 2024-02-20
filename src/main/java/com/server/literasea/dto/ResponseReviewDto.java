package com.server.literasea.dto;

import com.server.literasea.entity.Question;
import com.server.literasea.entity.Solve;
import com.server.literasea.enums.ArticleCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseReviewDto {
    private String article;
    private String answer;
    private ArticleCategory articleCategory;
    private String userSummary;

    public static ResponseReviewDto toDto(Question question, Solve solve){
        return ResponseReviewDto.builder()
                .article(question.getArticle())
                .answer(question.getAnswer())
                .articleCategory(question.getArticleCategory())
                .userSummary(solve.getSummary())
                .build();
    }
}
