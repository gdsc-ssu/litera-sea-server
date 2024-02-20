package com.server.literasea.service;

import com.server.literasea.dto.ResponseReviewDto;
import com.server.literasea.entity.Question;
import com.server.literasea.entity.Solve;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final SolveRepository solveRepository;

    @Transactional(readOnly = true)
    public List<String> getSolveDateListByUser(Users user) {
        List<Solve> userSolveList=solveRepository.findAllByUser(user);
        List<String> userSolveDateList=new ArrayList<>();
        for(Solve solve:userSolveList){
            userSolveDateList.add(solve.getCreateAt());
        }
        //중복제거
        List<String> newList = userSolveDateList.stream().distinct().collect(Collectors.toList());
        //오름차순정렬
        Collections.sort(newList);
        return newList;
    }


    @Transactional(readOnly = true)
    public List<Long> getSolveIdListByDay(Users user, String createAt) {
        List<Solve> userSolveList=solveRepository.findAllByUserAndCreateAt(user, createAt);
        List<Long> userSolveIdList=new ArrayList<>();
        for(Solve solve : userSolveList){
            userSolveIdList.add(solve.getId());
        }
        Collections.sort(userSolveIdList);
        return userSolveIdList;
    }

    @Transactional(readOnly = true)
    public ResponseReviewDto getReviewDtoBySolveId(Users user, Long solveId) {
        Solve chosenSolve=solveRepository.findByIdAndUser(solveId, user);
        Question chosenQuestion= chosenSolve.getQuestion();
        return ResponseReviewDto.toDto(chosenQuestion, chosenSolve);
    }
}
