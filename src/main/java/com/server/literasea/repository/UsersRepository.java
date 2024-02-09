package com.server.literasea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.literasea.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    
}
