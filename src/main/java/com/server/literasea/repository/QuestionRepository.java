package com.server.literasea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.literasea.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAll();
}