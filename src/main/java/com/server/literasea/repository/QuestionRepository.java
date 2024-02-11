package com.server.literasea.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.literasea.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
}