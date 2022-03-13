package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.config.security.kakao.KakaoOAuth2;
import com.example.janghj.config.security.kakao.KakaoUserInfo;
import com.example.janghj.config.util.S3Manager;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCash;
import com.example.janghj.domain.User.UserMileage;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.UserCashRepository;
import com.example.janghj.repository.UserMileageRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String ADMIN_TOKEN = "GMe3md5MK542K45M2ag32K252m22k2mGLWklrnYxKZ0aHgTBG30hfh90H";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserCashRepository userCashRepository;
    private final UserMileageRepository userMileageRepository;

    private final S3Manager s3Manager;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;


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
                .address(userDto.getAddress())
                .build();
        userRepository.save(user);

        userCashRepository.save(new UserCash(user));
        userMileageRepository.save(new UserMileage(user));
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

    public void userSetProfileImgUrl(UserDetailsImpl nowUser, MultipartFile multipartFile) {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId =" + nowUser.getId()));
    }

    @Transactional(rollbackFor = Throwable.class) // default : Unchecked Exception -> Throwable
    public UserCash depositUserCash(UserDetailsImpl nowUser, int readyCash) {
        UserCash userCash = userCashRepository.findByUserId(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 보유한 UserCash 을(를) 찾을 수 없습니다. userId = " + nowUser.getId()));
        userCash.depositUserCash(readyCash);
        return userCash;
    }

    @Transactional(rollbackFor = Throwable.class) // default : Unchecked Exception -> Throwable
    public Boolean paymentUserCash(UserDetailsImpl nowUser, int paymentAmount) {
        UserCash userCash = userCashRepository.findByUserId(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 보유한 UserCash 을(를) 찾을 수 없습니다. userId = " + nowUser.getId()));

        if (!nowUser.getId().equals(userCash.getUser().getId())) { // 현재 로그인 사용자 ID != 현금 충전하려는 사용자 ID 예외처리 및 대응 업데이트 예정
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 다른 유저(" + userCash.getUser().getId() + ")에 접근하여 캐쉬(을)를 수정하려고 합니다.");
        }
        if (userCash.withdrawalUserCash(paymentAmount)) {
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = Throwable.class) // default : Unchecked Exception -> Throwable
    public UserMileage depositUserMileage(UserDetailsImpl nowUser, int readyMileage) {
        UserMileage userMileage = userMileageRepository.findByUserId(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 보유한 UserMileage 을(를) 찾을 수 없습니다. userId = " + nowUser.getId()));
        userMileage.depositUserMileage(readyMileage);
        return userMileage;
    }

    @Transactional(rollbackFor = Throwable.class) // default : Unchecked Exception -> Throwable
    public UserMileage paymentUserMileage(UserDetailsImpl nowUser, int paymentAmount) {
        UserMileage userMileage = userMileageRepository.findByUserId(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 보유한 UserMileage 을(를) 찾을 수 없습니다. userId = " + nowUser.getId()));

        if (!nowUser.getId().equals(userMileage.getUser().getId())) { // 현재 로그인 사용자 ID != 현금 충전하려는 사용자 ID 예외처리 및 대응 업데이트 예정
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 다른 유저(" + userMileage.getUser().getId() + ")에 접근하여 마일리지(을)를 수정하려고 합니다.");
        }
        userMileage.withdrawalUserMileage(paymentAmount);

        return userMileage;
    }

    @Transactional(rollbackFor = Throwable.class) // default : Unchecked Exception -> Throwable
    public User setUserAddress(UserDetailsImpl nowUser, Address address, MultipartFile multipartFile) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 사용자가 없습니다. userId =" + nowUser.getId()));

        if (address != null) {
            user.setAddress(address);
        }
        if (multipartFile != null) {
            String profileImgUrl = s3Manager.upload(multipartFile, "profile"); // S3 profile 폴더에 저장하고 클라우드 프론트 url 반환
            user.setProfileImgUrl(profileImgUrl);
        }
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

            kakaoUser = new User(kakaoId, username, encodedPassword, email);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}