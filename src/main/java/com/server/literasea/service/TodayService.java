package com.server.literasea.service;

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.gson.Gson;
import com.server.literasea.dto.GenAIResponse;
import com.server.literasea.dto.SummaryResultDto;
import com.server.literasea.dto.TodayArticleDto;
import com.server.literasea.dto.UserSummaryDto;
import com.server.literasea.entity.Question;
import com.server.literasea.entity.Solve;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.QuestionRepository;
import com.server.literasea.repository.SolveRepository;

import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;

import com.server.literasea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayService {

    private final QuestionRepository questionRepository;
    private final SolveRepository solveRepository;
    private final UserRepository userRepository;

    public TodayArticleDto findTodayArticles(Users user) throws RuntimeException {
        Long todayArticleId = user.getDay() * 5;

        List<TodayArticleDto.TodayArticle> todayArticleList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Question question = questionRepository.findById(todayArticleId + i)
                    .orElseThrow(() -> new RuntimeException("유효한 지문이 존재하지 않습니다.")); // 추후 커스텀 에러 고려 ;

            TodayArticleDto.TodayArticle todayArticle = TodayArticleDto.TodayArticle.builder()
                    .articleId(question.getId())
                    .articleCategory(question.getArticleCategory())
                    .article(question.getArticle())
                    .build();

            todayArticleList.add(todayArticle);
        }

        return new TodayArticleDto(todayArticleList);
    }

    public SummaryResultDto todayPost(Users user, UserSummaryDto userSummaryDto) throws IOException {
        List<SummaryResultDto.SummaryResult> summaryResultList = new ArrayList<>();

        for (UserSummaryDto.UserSummary userSummary : userSummaryDto.getUserSummaryList()) {
            Question question = questionRepository.findById(userSummary.getArticleId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 지문입니다."));

            GenAIResponse genAIResponse = calculateScore(userSummary.getSummary(), question.getArticle());

            Solve solve = saveSolving(user, userSummary, genAIResponse); // 이게맞나,,,

            SummaryResultDto.SummaryResult summaryResult = SummaryResultDto.SummaryResult.builder()
                    .articleId(userSummary.getArticleId())
                    .score1(solve.getScore1())
                    .score2(solve.getScore2())
                    .score3(solve.getScore3())
                    .score4(solve.getScore4())
                    .score5(solve.getScore5())
                    .score6(solve.getScore6())
                    .answer(question.getAnswer())
                    .build();

            summaryResultList.add(summaryResult);
        }

        userDayIncrease(user);

        return new SummaryResultDto(summaryResultList);
    }

    private void userDayIncrease(Users user) {
        user.userDayPlusOne();
        userRepository.save(user);
    }

    private GenAIResponse calculateScore(String userWriting, String articleText) throws IOException {

        String instance =
                "{ \"prompt\": " + "\"origin_text 에 대한 user_text 의 코사인 유사도를 score 에 알려주세요. 그리고 score 감점에 영향을 미친 부분을 카테고리에서 고른 후 영향도를 0에서 100 사이의 %로 나타내 주세요.\n" +
                        "카테고리는 아래와 같으며, 순서대로 criteria1, criteria2, criteria3, criteria4, criteria5, criteria6 에 해당합니다.\n" +
                        "-한글 맞춤법 및 띄어쓰기 오류 \n" +
                        "-단어 선택 오류 \n" +
                        "-비문\n" +
                        "-미완성 또는 불완전한 문장 \n" +
                        "-키워드 또는 중요 내용 오류 \n" +
                        "-유사한 내용 반복\n" +
                        "\n" +
                        "답변은 score, criteria1, criteria2, criteria3, criteria4, criteria5, criteria6 총 7개의 값을 포함하는 JSON 형식으로 작성하세요. markdown은 사용하지 마세요.\n" +
                        "origin_text :\n" + articleText +
                        "user_text : \n" + userWriting +
                        "score : \n" +
                        "category:\"}";
        String parameters =
                "{\n"
                        + "  \"temperature\": 0.1,\n"
                        + "  \"maxOutputTokens\": 1024,\n"
                        + "  \"topP\": 1,\n"
                        + "  \"topK\": 0\n"
                        + "}";
        String project = "youtube-comments-386405";
        String location = "asia-northeast3";
        String publisher = "google";
        String model = "text-bison";

        String endpoint = String.format("%s-aiplatform.googleapis.com:443", location);
        PredictionServiceSettings predictionServiceSettings =
                PredictionServiceSettings.newBuilder().setEndpoint(endpoint).build();

        try (PredictionServiceClient predictionServiceClient =
                     PredictionServiceClient.create(predictionServiceSettings)) {
            final EndpointName endpointName =
                    EndpointName.ofProjectLocationPublisherModelName(project, location, publisher, model);

            Value.Builder instanceValue = Value.newBuilder();
            JsonFormat.parser().merge(instance, instanceValue);
            List<Value> instances = new ArrayList<>();
            instances.add(instanceValue.build());

            Value.Builder parameterValueBuilder = Value.newBuilder();
            JsonFormat.parser().merge(parameters, parameterValueBuilder);
            Value parameterValue = parameterValueBuilder.build();

            PredictResponse predictResponse =
                    predictionServiceClient.predict(endpointName, instances, parameterValue);

            String generatedResult = predictResponse.getPredictionsList().get(0)
                    .getStructValue().getFieldsMap().get("content").getStringValue();

            Gson gson = new Gson();
            GenAIResponse genAIResponse = gson.fromJson(generatedResult, GenAIResponse.class);
            return genAIResponse;
        }
    }

    private Solve saveSolving(Users user, UserSummaryDto.UserSummary userSummary, GenAIResponse genAIResponse) {
        Question question = questionRepository.findById(userSummary.getArticleId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지문입니다."));

        Solve solve = Solve.builder()
                .question(question)
                .user(user)
                .summary(userSummary.getSummary())
                .score1(100 - genAIResponse.getCriteria1())
                .score2(100 - genAIResponse.getCriteria2())
                .score3(100 - genAIResponse.getCriteria3())
                .score4(100 - genAIResponse.getCriteria4())
                .score5(100 - genAIResponse.getCriteria5())
                .score6(100 - genAIResponse.getCriteria6())
                .build();

        solveRepository.save(solve);

        return solve;
    }
}
