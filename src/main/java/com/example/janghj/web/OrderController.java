package com.example.janghj.web;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Order;
import com.example.janghj.service.OrderService;
import com.example.janghj.service.UserService;
import com.example.janghj.web.dto.OrderWebDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class OrderController {

    private final OrderService orderService;

//    401(권한 없음) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    402(결제 필요) return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
//    416(처리할 수 없는 요청범위) return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
//    500(서버 오류) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    @Operation(description = "주문하기, 로그인 필요", method = "POST")
    @PostMapping("/order")
    public void order(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody OrderWebDto orderWebDto) {
        orderService.order(nowUser, orderWebDto);
    }

    @Operation(description = "주문 1개 조회하기 , 로그인 필요", method = "GET")
    @GetMapping("/order/{orderId}")
    public void getOrder(@PathVariable Long orderId) {
        orderService.getOrder(orderId);
    }

    @Operation(description = "나의 주문 전체 보기 , 로그인 필요", method = "GET")
    @GetMapping("/orders")
    public List<Order> cancelOrder(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        return orderService.getOrders(nowUser);
    }

    @Operation(description = "주문 취소, 로그인 필요", method = "DELETE")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> orderCancel(@PathVariable Long orderId) {
        try {
            orderService.orderCancel(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
