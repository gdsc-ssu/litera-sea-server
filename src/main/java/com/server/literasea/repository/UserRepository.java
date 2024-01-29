package com.server.literasea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.literasea.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
