package com.server.literasea.dto;

import com.server.literasea.entity.Word;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WordInfoDto {
    private String koreanWord;
    private String chineseWord;
    private String mean;

    public static WordInfoDto from(Word word){
        return WordInfoDto.builder()
                .koreanWord(word.getKoreanWord())
                .chineseWord(word.getChineseWord())
                .mean(word.getMean())
                .build();
    }
}
