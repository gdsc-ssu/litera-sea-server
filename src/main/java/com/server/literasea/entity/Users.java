package com.server.literasea.entity;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BoatInfoDto;
import com.server.literasea.dto.ResponseMainPageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Users {
    @OneToOne(fetch = FetchType.LAZY)  //TODO:단방향, 유저 생성(회원가입)시 인벤토리 생성 필수(null로)!
    @JoinColumn(name="user_inventory_id", referencedColumnName = "inventory_id")
    private Inventory inventory;

    @Builder.Default
    @OneToMany(mappedBy = "users")
    private List<Word> words=new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private int location;

    @Column
    private int level;

    @Column
    private String nickname;

    @Column
    private int subjectScore;

    @Column
    private int grammarScore;

    @Column
    private int vocaScore;

    @Column
    private int exp;

    public Users(){};

    public static ResponseMainPageDto usersToDto(Users users){
        return ResponseMainPageDto.builder()
                .nickname(users.nickname)
                .location(users.location)
                .level(users.level)
                .exp(users.exp)
                .build();
    }

    public List<BadgeInfoDto> getBadges(Users users){
        return users.inventory.getBadgeInfoDtos();
    }

    public void addWord(Word word){
        this.words.add(word);
        word.setUsers(this);
    }
}
