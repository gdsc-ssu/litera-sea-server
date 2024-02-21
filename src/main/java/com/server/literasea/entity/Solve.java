package com.server.literasea.entity;

import com.server.literasea.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Solve extends Datetime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solve_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "summary")
    private String summary;

    @Column(name = "score1")
    private Integer score1;

    @Column(name = "score2")
    private Integer score2;

    @Column(name = "score3")
    private Integer score3;

    @Column(name = "score4")
    private Integer score4;

    @Column(name = "score5")
    private Integer score5;

    @Column(name = "score6")
    private Integer score6;

}