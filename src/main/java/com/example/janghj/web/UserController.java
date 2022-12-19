package com.example.janghj.web;

import com.example.janghj.config.jwt.JwtTokenUtil;
import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.JwtTokenDto;
import com.example.janghj.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Operation(description = "회원가입 시 아이디 유효성 검사", method = "GET")
    @GetMapping("/users/signup/check")
    public ResponseEntity<?> validationUserId(@RequestBody UserDto userDto) {
        if (userService.validationUserId(userDto.getUsername())) {
            throw new AccessDeniedException("해당 로그인에 접근할 권한이 없습니다.");
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(description = "회원가입", method = "POST")
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception {
        User user = userService.registerUser(userDto);
        if (user.getRole().equals(UserRole.USER)) {
            return ResponseEntity.ok().body(user);
        } else if (user.getRole().equals("Admin")) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        throw new AccessDeniedException("해당 로그인에 접근할 권한이 없습니다.");
    }

    @Operation(description = "회원탈퇴", method = "DELETE")
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        userService.deleteUser(nowUser.getUser().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "로그인", method = "GET")
    @GetMapping("/user")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        if (!userService.confirmPassword(userDto)) {
            throw new AccessDeniedException("해당 로그인에 접근할 권한이 없습니다.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDto(token, userDetails.getUsername()));
    }

    @Operation(description = "유저 주소 변경, 로그인 필요", method = "PUT")
    @PutMapping("/user")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody Address address) {
        User user = userService.setUserAddress(nowUser, address);
        return ResponseEntity.ok().body(user);
    }

    @Operation(description = "유저 현금 충전하기, 로그인 필요", method = "POST")
    @PostMapping("/user/cash")
    public ResponseEntity<?> depositUserCash(@AuthenticationPrincipal UserDetailsImpl nowUser,
                                             @RequestPart(required = false) int readyCash) {
        try {
            userService.depositUserCash(nowUser.getUser(), readyCash);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new AccessDeniedException("해당 유저에 충전 기능에 접근할 권한이 없습니다.");
        } catch (Throwable ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/login/kakao")
    public String kakaoLogin(String code) {
        userService.kakaoLogin(code);
        return "redirect:/";
    }
}



