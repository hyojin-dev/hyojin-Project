package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.config.security.kakao.KakaoOAuth2;
import com.example.janghj.config.security.kakao.KakaoUserInfo;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCart;
import com.example.janghj.domain.User.UserCash;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.userCart.UserCartRepository;
import com.example.janghj.repository.user.UserRepository;
import com.example.janghj.repository.userCash.UserCashRepository;
import com.example.janghj.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String ADMIN_TOKEN = "GMe3md5MK542K45M2ag32K252m22k2mGLWklrnYxKZ0aHgTBG30hfh90H";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserCashRepository userCashRepository;
    private final UserCartRepository userCartRepository;

    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;

    public List<User> test() {
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    public User registerUser(UserDto userDto) {
        UserRole userRole = UserRole.USER;
        if (userDto.isAdmin()) {
            if (userDto.getAdminToken().equals(ADMIN_TOKEN)) {
                userRole = UserRole.ADMIN;
            }
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(userRole)
                .address(new Address(userDto.getAddressDto()))
                .userCash(new UserCash())
                .build();
        userRepository.save(user);
        return user;
    }

    public Boolean validationUserId(String username) {
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
                () -> new NullPointerException("해당 사용자가 없습니다. userName = " + userDto.getUsername())
        );
        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserCash depositUserCash(User user, int readyCash) {
        UserCash userCash = userCashRepository.findByUserId(user.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 보유한 캐시를(을) 찾을 수 없습니다. userId = " + user.getId()));
        userCash.depositUserCash(readyCash);
        userCashRepository.save(userCash);
        return userCash;
    }

    @Transactional(rollbackFor = Throwable.class)
    public User setUserAddress(UserDetailsImpl nowUser, Address address) {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId =" + nowUser.getId()));

        if (address != null) {
            user.setAddress(address);
        }

        userRepository.save(user);
        return user;
    }

//    public List<UserCart> getUserCarts(UserDetailsImpl nowUser) {
//        List<UserCart> userCarts = userCartRepository.findAllByUserId(nowUser.getId());
//
//    }

    public boolean checkUserCart(UserDetailsImpl nowUser, Long productId) {
        Optional<UserCart> checkUserLike = userCartRepository.findAllByUserIdAndProductId(
                nowUser.getId(), productId);
        if (checkUserLike.isEmpty())
            return false;
        return checkUserLike.get().isLikeIt();
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean putInAndOutCart(UserDetailsImpl nowUser, Long productId) {
        Optional<UserCart> userCart = userCartRepository.findByUserIdAndProductId(
                nowUser.getId(), productId);
        if (userCart.isEmpty()) {
            userCartRepository.save(new UserCart(nowUser.getUser(), productId, true));
            return true;
        }
        if (userCart.get().setLikeIt()) {
            userCartRepository.save(userCart.get());
            return true;
        } else {
            userCartRepository.delete(userCart.get());
            return false;
        }
    }

    public User findByUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId =" + userId));
    }

    public void saveUser(User user) {
        userRepository.save(user);
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

            kakaoUser = new User(kakaoId, username, encodedPassword, email);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}