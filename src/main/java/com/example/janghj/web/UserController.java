package com.example.janghj.web;

import com.example.janghj.config.jwt.JwtTokenUtil;
import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.userCart.UserCartRepositoryImpl;
import com.example.janghj.repository.userCart.dto.UserCartDto;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.JwtTokenDto;
import com.example.janghj.web.dto.UserDto;
import com.example.janghj.web.dto.basic.BasicResponse;
import com.example.janghj.web.dto.basic.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.janghj.web.dto.basic.BasicResponse.build;
import static com.example.janghj.web.dto.basic.StatusCode.OK;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserCartRepositoryImpl userCartRepository;

    @Operation(description = "test", method = "GET")
    @GetMapping("/test")
    public ResponseEntity<?> tset(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        try {
            System.out.println("===================쿼리 몇번?===================");
            System.out.println("===================쿼리 몇번?===================");
            List<UserCartDto> allByUserCartDto = userCartRepository.findAllByUserCartDto(nowUser.getId());
            System.out.println("===================쿼리 종료====================");
            System.out.println("===================쿼리 종료====================");
            return ok().body(build(OK, "유저의 장바구니 쿼리 테스트", allByUserCartDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(build(StatusCode.BAD_REQUEST, e.getMessage()));
        }
    }

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
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal UserDetailsImpl nowUser,
                                              @RequestBody Address address) {
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

    @Operation(description = "유저의 장바구니 정보 전체 정보 확인", method = "GET")
    @GetMapping("/user/carts")
    public ResponseEntity<?> getUserCarts(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        try {
//            List<UserCart> userCarts = userService.getUserCarts(nowUser);
            return ok().body(build(OK, "유저의 장바구니에 담겨있는지 확인 성공"));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(build(StatusCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @Operation(description = "유저의 장바구니에 포함된 상태인지 확인", method = "GET")
    @GetMapping("/user/cart/{productId}")
    public ResponseEntity<?> getUserCart(@AuthenticationPrincipal UserDetailsImpl nowUser,
                                         @PathVariable Long productId) {

        try {
            boolean checkUserCart = userService.checkUserCart(nowUser, productId);
            return ok().body(build(OK, "유저의 장바구니에 담겨있는지 확인 성공", checkUserCart));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(build(StatusCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/user/cart/{productId}")
    @Operation(description = "유저의 장바구니 기능(이미 장바구니에 담겨있을 경우 장바구니에서 삭제)", method = "POST")
    public ResponseEntity<?> putInAndOutCart(@AuthenticationPrincipal UserDetailsImpl nowUser,
                                             @PathVariable Long productId) throws Exception {
        try {
            boolean checkUserCart = userService.putInAndOutCart(nowUser, productId);
            return ok().body(build(OK, "유저의 장바구니 기능 정상 동작", checkUserCart));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BasicResponse.build(StatusCode.BAD_REQUEST, e.getMessage()));
        }
    }


    @GetMapping("/user/login/kakao")
    public String kakaoLogin(String code) {
        userService.kakaoLogin(code);
        return "redirect:/";
    }
}



