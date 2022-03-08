package com.example.janghj.web;

import com.example.janghj.repository.OrderRepository;
import com.example.janghj.service.OrderService;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class OrderController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @Operation(description = "유저가 주문하기, 로그인 필요", method = "POST")
    @PostMapping("/order")
    public void order(@RequestBody OrderDto orderDto) {
        orderService.order(orderDto);
    }

//        @Operation(description = "유저 현금 결재하기, 로그인 필요", method = "POST")
//    @PostMapping("/user/payment/cash")
//    public ResponseEntity<?> paymentUserCash(@AuthenticationPrincipal UserDetailsImpl nowUser,
//                                             @RequestPart(required = false) int readyCash) {
//        userService.paymentUserCash(nowUser, readyCash);
//            return new ResponseEntity<>(HttpStatus.OK);
//    }
}
