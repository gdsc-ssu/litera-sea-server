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

    public List<TodayArticleDto> findTodayArticles() throws RuntimeException{
        Long userId = 1L; // 로그인 구현 전 임시 값
        Long userDay = 0L; // 로그인 구현 전 임시 값, 0인 경우는 아직 한번도 요약을 진행하지 않은 상태

        List<TodayArticleDto> todayArticleDtos = new ArrayList<>();

        for(int i = 1; i <= 5; i++) {
            userDay = userDay * 5 + i;
            Question question = questionRepository.findById(userDay)
                    .orElseThrow(() -> new RuntimeException("유효한 지문이 존재하지 않습니다.")); // 추후 커스텀 에러 고려 ;

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
