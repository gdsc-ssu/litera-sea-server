package com.server.literasea.entity;

import com.server.literasea.enums.ArticleCategory;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "article")
    private String article;

    @Column(name = "category")
    private ArticleCategory articleCategory;

    @Column(name = "answer")
    private String answer;
}