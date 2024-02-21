package com.server.literasea.dto;

import com.server.literasea.entity.Word;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WordInfoDto {
    private String koreanWord;
    private String mean;
    private Long wordId;

    public static WordInfoDto from(Word word){
        return WordInfoDto.builder()
                .koreanWord(word.getKoreanWord())
                .mean(word.getMean())
                .wordId(word.getId())
                .build();
    }
}
