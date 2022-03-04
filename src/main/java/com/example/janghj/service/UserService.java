package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.config.security.kakao.KakaoOAuth2;
import com.example.janghj.config.security.kakao.KakaoUserInfo;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;


    public User registerUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(UserRole.USER)
                .address(userDto.getAddress())
                .build();
        userRepository.save(user);
        return user;
    }

    public Boolean checkExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean confirmPassword(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId = " + userDto.getUsername())
        );
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }

    @Transactional
    public User userSetAddress(UserDetailsImpl nowUser, Address address) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId =" + nowUser.getId()));
        user.setAddress(address);
        userRepository.save(user);
        return user;
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;
            kakaoUser = new User(kakaoId, username, encodedPassword, email, role);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}