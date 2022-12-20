package com.example.janghj.repository.userCart;

import com.example.janghj.domain.User.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long> {
    List<UserCart> findAllByUserId(Long userId);
    Optional<UserCart> findAllByUserIdAndProductId(Long userId, Long productId);
    Optional<UserCart> findByUserIdAndProductId(Long userId, Long productId);
}
