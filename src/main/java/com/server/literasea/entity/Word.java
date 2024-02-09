package com.server.literasea.entity;

import com.server.literasea.dto.WordInfoDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
public class Word {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private Users users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column
    private String koreanWord;

    @Column
    private String chineseWord;

    @Column
    private String mean;

    public static Word from(WordInfoDto wordInfoDto){
        return Word.builder()
                .koreanWord(wordInfoDto.getKoreanWord())
                .chineseWord(wordInfoDto.getChineseWord())
                .mean(wordInfoDto.getMean())
                .build();
    }
}
