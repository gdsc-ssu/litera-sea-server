package com.server.literasea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.literasea.entity.Users;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
