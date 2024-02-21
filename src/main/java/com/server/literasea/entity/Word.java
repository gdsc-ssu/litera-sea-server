package com.server.literasea.entity;

import com.server.literasea.dto.WordInfoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    @ManyToOne(fetch = FetchType.LAZY)
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

    public static Word from(String requestWord, String mean){
        return Word.builder()
                .koreanWord(requestWord)
                .mean(mean)
                .build();
    }
}
