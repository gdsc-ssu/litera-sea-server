package com.server.literasea.entity;

import jakarta.persistence.*;

@Entity
public class Solve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solve_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "summary")
    private String summary;

    @Column(name = "score")
    private Integer score;

}