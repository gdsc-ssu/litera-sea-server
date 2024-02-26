package com.server.literasea.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.literasea.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);


    @Query("SELECT u FROM Users u JOIN FETCH u.words WHERE u.email = :email")
    Optional<Users> findByEmailWithJPQL(@Param("email") String email);
}
