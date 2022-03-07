package com.example.janghj.repository;

import com.example.janghj.domain.User.UserCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCashRepository extends JpaRepository<UserCash, Long> {
    Optional<UserCash> findByUserId(Long userId);

}
