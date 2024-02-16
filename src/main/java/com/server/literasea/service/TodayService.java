package com.server.literasea.service;

import com.server.literasea.dto.SummaryResultDto;
import com.server.literasea.dto.TodayArticleDto;
import com.server.literasea.dto.UserSummaryDto;
import com.server.literasea.entity.Question;
import com.server.literasea.entity.Solve;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.QuestionRepository;
import com.server.literasea.repository.SolveRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayService {

    private final QuestionRepository questionRepository;
    private final SolveRepository solveRepository;

    public List<TodayArticleDto> findTodayArticles(Users user) throws RuntimeException {
        Long userDay = user.getDay();

        List<TodayArticleDto> todayArticleDtos = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
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

    public List<SummaryResultDto> todayPost(Users user, List<UserSummaryDto> userSummaryList) {
        List<SummaryResultDto> summaryResultList = new ArrayList<>();

        for (UserSummaryDto userSummaryDto : userSummaryList) {
            Integer score = calculateScore(userSummaryDto);

            saveSolving(user, userSummaryDto, score); // 이게맞나,,,

            Question question = questionRepository.findById(userSummaryDto.getArticleId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 지문입니다."));

            SummaryResultDto summaryResultDto = SummaryResultDto.builder()
                    .articleId(userSummaryDto.getArticleId())
                    .score(score)
                    .answer(question.getAnswer())
                    .build();

            summaryResultList.add(summaryResultDto);
        }

        return summaryResultList;
    }

    private Integer calculateScore(UserSummaryDto userSummaryDto) {
        return 0;
    }

    private void saveSolving(Users user, UserSummaryDto userSummaryDto, Integer score) {
        Question question = questionRepository.findById(userSummaryDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지문입니다."));

        Solve solve = Solve.builder()
                .question(question)
                .user(user)
                .summary(userSummaryDto.getSummary())
                .score(score)
                .build();

        solveRepository.save(solve);
    }
}
