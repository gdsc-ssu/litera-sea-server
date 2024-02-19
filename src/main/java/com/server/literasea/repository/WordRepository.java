package com.server.literasea.repository;

import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAllByUsers(Users users);
}
