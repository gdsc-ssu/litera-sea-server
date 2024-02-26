package com.server.literasea.entity;

import com.server.literasea.dto.ResponseMainPageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "user_inventory_id", referencedColumnName = "inventory_id")
    private Inventory inventory;

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Word> words = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solve> solves = new ArrayList<>();

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "location")
    private Integer location;

    @Column(name = "level")
    private Integer level;

    @Column(name = "exp")
    private Integer exp;

    @Column(name = "user_day")
    private Long day;

    @Column(name = "subject_score")
    private Integer subjectScore;

    @Column(name = "grammar_score")
    private Integer grammarScore;

    @Column(name = "voca_score")
    private Integer vocaScore;


    public static ResponseMainPageDto usersToDto(Users users) {
        return ResponseMainPageDto.builder()
                .nickname(users.nickname)
                .location(users.location)
                .level(users.level)
                .exp(users.exp)
                .build();
    }

    public void userDayPlusOne() {
        day++;
    }

    @Override
    public ArrayList<GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("User"));
        return auth;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addWord(Word word) {
        this.words.add(word);
        word.setUsers(this);
    }

    public void setInventory(Inventory inventory){
        this.inventory=inventory;
    }
}
