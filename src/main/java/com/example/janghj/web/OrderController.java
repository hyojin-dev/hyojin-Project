package com.example.janghj.web;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Order;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.service.OrderService;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class OrderController {

    private final OrderService orderService;


    @Operation(description = "주문하기, 로그인 필요", method = "POST")
    @PostMapping("/order")
    public void order(@RequestBody OrderDto orderDto) {
        orderService.order(orderDto);
    }

    @Operation(description = "주문 1개 조회하기 , 로그인 필요", method = "GET")
    @GetMapping("/order/{orderId}")
    public void getOrder(@PathVariable Long orderId) {
        orderService.getOrder(orderId);
    }

    @Operation(description = "나의 주문 전체 보기 , 로그인 필요", method = "GET")
    @GetMapping("/orders")
    public void cancelOrder(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        List<Order> orderList = orderService.getOrders(nowUser);
    }

    @Operation(description = "주문취소, 로그인 필요", method = "DELETE")
    @DeleteMapping("/order")
    public void orderCancel(@PathVariable Long orderId) {
        orderService.orderCancel(orderId);
    }


//        @Operation(description = "유저 현금 결재하기, 로그인 필요", method = "POST")
//    @PostMapping("/user/payment/cash")
//    public ResponseEntity<?> paymentUserCash(@AuthenticationPrincipal UserDetailsImpl nowUser,
//                                             @RequestPart(required = false) int readyCash) {
//        userService.paymentUserCash(nowUser, readyCash);
//            return new ResponseEntity<>(HttpStatus.OK);
//    }
}
