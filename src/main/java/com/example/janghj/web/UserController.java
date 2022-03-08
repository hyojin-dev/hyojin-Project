package com.example.janghj.web;

import com.example.janghj.config.jwt.JwtTokenUtil;
import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.JwtTokenDto;
import com.example.janghj.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Operation(description = "회원가입 시 아이디 유효성 검사", method = "GET")
    @GetMapping("/users/signup/check")
    public ResponseEntity<?> validationUserId(@RequestBody UserDto userDto) {
        if (userService.validationUserId(userDto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(description = "회원가입", method = "POST")
    @PostMapping("/users")
    public String registerUser(@RequestBody UserDto userDto) throws Exception {
        userService.registerUser(userDto);
        return "'" + userDto.getUsername() + "'님 회원가입을 축하드립니다!";
    }

    @Operation(description = "회원탈퇴", method = "DELETE")
    @DeleteMapping("/users")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        userService.deleteUser(nowUser.getUser().getId());
    }

    @Operation(description = "로그인", method = "POST")
    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        if (!userService.confirmPassword(userDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDto(token, userDetails.getUsername()));
    }

    @Operation(description = "유저 주소 설정, 로그인 필요", method = "PUT")
    @PutMapping("/users")
    public User updateProfile(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody Address address,
                              @RequestPart(name = "profileImgUrl", required = false) MultipartFile multipartFile) throws IOException {
        return userService.setUserAddress(nowUser, address);
    }

    @Operation(description = "유저 현금 입금하기, 로그인 필요", method = "POST")
    @PostMapping("/users/cash")
    public ResponseEntity<?> depositUserCash(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestPart(required = false) int readyCash) throws IOException {
//        AOP 작업 예정 - try 문(03.08 장효진)
        try {
            userService.depositUserCash(nowUser, readyCash);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Throwable ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Operation(description = "유저 현금 결재하기, 로그인 필요", method = "POST")
//    @PostMapping("/users/payment/cash")
//    public ResponseEntity<?> paymentUserCash(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestPart(required = false) int readyCash) throws IOException {
//        userService.paymentUserCash(nowUser, readyCash);
//            return new ResponseEntity<>(HttpStatus.OK);
//    }

//    테스트
//    @Operation(description = "유저 마일리지 추가, 로그인 필요", method = "POST")
//    @PostMapping("/users/payment/mileage")
//    public void addUserMileage(@RequestBody TestDto test) throws IOException {
//        userService.addUserMileage(test);
//    }
    @Operation(description = "유저 마일리지 추가하기, 로그인 필요", method = "POST")
    @PostMapping("/users/mileage")
    public ResponseEntity<?> addUserMileage(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestPart(required = false) int readyMileage) throws IOException {
        try {
            userService.depositUserMileage(nowUser, readyMileage);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Throwable ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }














    //   <button id="login-kakao-btn"
    //	onclick="location.href='https://kauth.kakao.com/oauth/authorize?client_id=e81c288c3e5afca68f122b4db3bc314f&" +
    //			"redirect_uri=http://localhost:8080/user/kakao/callback&response_type=code'">
    //	카카오로 로그인하기
    //    </button>
    @GetMapping("/users/kakao")
    public String kakaoLogin(String code) {
        userService.kakaoLogin(code);
        return "redirect:/";

    }
}



