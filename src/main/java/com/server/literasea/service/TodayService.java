package com.server.literasea.service;

import com.server.literasea.dto.TodayArticleDto;
import com.server.literasea.entity.Question;
import com.server.literasea.entity.Solve;
import com.server.literasea.repository.QuestionRepository;
import com.server.literasea.repository.SolveRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayService {

    private final SolveRepository solveRepository;
    private final QuestionRepository questionRepository;

    public List<TodayArticleDto> findTodayArticles() {
        Long userId = 1L; // 로그인 구현 전 임시 값

        List<TodayArticleDto> todayArticleDtos = new ArrayList<>();

        List<Solve> solves = solveRepository.findAllById(userId);
        List<Long> solvedIds = new ArrayList<>();
        List<Question> todayQuestions = new ArrayList<>();
        List<Question> allQuestions = questionRepository.findAll();

        for(Solve solve: solves) {
            solvedIds.add(solve.getId());
        }

        for(Question question : allQuestions) {
            if (todayQuestions.size() > 5) {
                break;
            }

            if (!solvedIds.contains(question.getId())) {
                todayQuestions.add(question);
            }
        }

        for(Question question : todayQuestions) {
            TodayArticleDto todayArticleDto = TodayArticleDto.builder()
            .articleId(question.getId())
            .articleCategory(question.getArticleCategory())
            .article(question.getArticle())
            .build();

            todayArticleDtos.add(todayArticleDto);
        }

        return todayArticleDtos;
    }
}
