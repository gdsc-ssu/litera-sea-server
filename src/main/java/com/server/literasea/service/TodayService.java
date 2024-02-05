package com.server.literasea.service;

import com.server.literasea.dto.TodayArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayService {

    public List<TodayArticleDto> findTodayArticles() {
        List<TodayArticleDto> todayArticleDtos = new ArrayList<>();
        throw new RuntimeException("dsfsgq");
//        return todayArticleDtos;
    }
}
