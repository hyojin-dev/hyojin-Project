package com.example.janghj.web;

import com.example.janghj.config.jwt.JwtTokenUtil;
import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.User.User;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.JwtTokenDto;
import com.example.janghj.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Operation(description = "회원가입", method = "POST")
    @PostMapping("/user/signup")
    public String createUser(@RequestBody UserDto userDto) throws Exception {
        userService.registerUser(userDto);
        return "'" + userDto.getUsername() + "'님 회원가입을 축하드립니다!";
    }

    @Operation(description = "로그인", method = "POST")
    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        if (userService.confirmPassword(userDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDto(token, userDetails.getUsername()));
    }

    @Operation(description = "회원가입 시 아이디 유효성 검사", method = "POST")
    @PostMapping("/user/signup/check")
    public String checkUser(@RequestBody UserDto userDto) {
        JSONObject response = new JSONObject();
        try {
            userService.checkExist(userDto);
        } catch (IllegalArgumentException e) {
            response.put("exists", Boolean.TRUE);
            return response.toString();
        }
        response.put("exists", Boolean.FALSE);
        return response.toString();
    }

    @Operation(description = "프로필 설정, 로그인 필요", method = "POST")
    @PostMapping("/user")
    public User updateProfile(@RequestPart(required = false) String nickname,
                              @RequestPart(name = "profileImgUrl", required = false) MultipartFile multipartFile,
                              @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userService.updateProfile(nowUser);
    }

    @Operation(description = "유저 삭제", method = "DELETE")
    @DeleteMapping("/user")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        userService.deleteUser(nowUser);
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);
        return "redirect:/";
    }
}



