package com.server.literasea.repository;

import java.util.List;

import com.server.literasea.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.literasea.entity.Solve;

public interface SolveRepository extends JpaRepository<Solve, Long> {

    List<Solve> findAllByUser(Users user);

    List<Solve> findAllByUserAndCreateAt(Users user, String createAt);

    Solve findByIdAndUser(Long solveId, Users user);
}