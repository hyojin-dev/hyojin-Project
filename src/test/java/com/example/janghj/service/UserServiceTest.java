package com.example.janghj.service;

import com.example.janghj.domain.User.User;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.AddressDto;
import com.example.janghj.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    UserDto userDto;

    @BeforeEach
    void beforeEach() {
        this.userDto = new UserDto("username",
                "password",
                "email",
                new AddressDto("city", "street", "zipcode"));
    }

    @Test
    @DisplayName("유저 생성 성공")
    void registerUser() throws Exception {
        // given

        // when
        User user = userService.registerUser(userDto);

        // then
        User findByUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new NullPointerException("UserServiceTest - 저장된 유저를 찾을 수 없습니다."));

        assertEquals("유저의 ID 값이 저장되어야 한다.",
                user.getId(), findByUser.getId());
        assertEquals("유저의 ID 값이 저장되어야 한다.",
                user.getUserCash().getId(), findByUser.getUserCash().getId());
    }

    @Test
    @DisplayName("유저 삭제 성공")
    void deleteUser() throws Exception {
        // given
        User user = userService.registerUser(userDto);

        // when
        userService.deleteUser(user.getId());

        // then
        Optional<User> findByOrder = userRepository.findByUsername(user.getUsername());
        assertEquals("유저의 정보가 사라져야 한다.",
                userRepository.findAll().size(), 0);
    }
}
