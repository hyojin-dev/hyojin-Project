package com.example.janghj.repository;

import com.example.janghj.domain.User.UserMileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMileageRepository extends JpaRepository<UserMileage, Long> {
    Optional<UserMileage> findByUserId(Long userId);
}
