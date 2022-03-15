package com.example.janghj.web;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Order;
import com.example.janghj.service.OrderService;
import com.example.janghj.web.dto.OrderWebDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class OrderController {

    private final OrderService orderService;

//    401(권한 없음) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    402(결제 필요) return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
//    404(값이 없음) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    406(사용자로부터 받는 값 부족) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    416(처리할 수 없는 요청범위) return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
//    500(서버 오류) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    @Operation(description = "주문 하기, 로그인 필요, 결재 필요", method = "POST")
    @PostMapping("/order")
    public ResponseEntity<?> order(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody OrderWebDto orderWebDto) {
        if (nowUser.getAddress() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        orderService.order(nowUser, orderWebDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "주문 수정하기, 로그인 필요", method = "PUT")
    @PutMapping("/order")
    public void updateOrder(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long orderId, @RequestBody OrderWebDto orderWebDto) {
        orderService.updateOrder(nowUser, orderId, orderWebDto);
    }

    @Operation(description = "나의 주문 1개 조회하기 , 로그인 필요", method = "GET")
    @GetMapping("/order/{orderId}")
    public void findByOrder(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long orderId) {
        orderService.findByOrder(nowUser, orderId);
    }

    @Operation(description = "주문 취소, 로그인 필요", method = "DELETE")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> orderCancel(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long orderId) {
        try {
            orderService.orderCancel(nowUser, orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "나의 주문 전체 보기 , 로그인 필요", method = "GET")
    @GetMapping("/orders")
    public List<Order> findByOrders(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        return orderService.findByOrders(nowUser);
    }

    @Operation(description = "1개 주문 결재 하기, 로그인 필요,", method = "POST")
    @PostMapping("/order/payment")
    public ResponseEntity<?> payForTheOrder(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long orderId) {
        try { // AOP 작업 예정
            orderService.payForTheOrder(nowUser, orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ArithmeticException e) { // 결재 금액 부족
            return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
        } catch (AccessDeniedException e) { // 권한 없음
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException e) { // 찾는 값이 없음
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Throwable e) { // 알 수 없는 에러(개발자가 예상하지 못한 에러는 없어야한다.)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "전체 주문 결재 하기, 로그인 필요,", method = "POST")
    @PostMapping("/orders/payment")
    public void payForTheOrders(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        orderService.payForTheOrders(nowUser);
    }

    @Operation(description = "배송 시작, 로그인 필요", method = "POST")
    @PostMapping("/order/delivery")// 이곳은 택배 or 라이더에게 받는 요청으로 가정하고 결재가 완료된 order 의 주문상태를 변경한다.
    public void orderDeliveryStart(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        orderService.deliveryStart(nowUser);
    }

    @Operation(description = "배송 도착, 로그인 필요", method = "GET")
    @GetMapping("/order/delivery")// 이곳은 택배 or 라이더에게 받는 요청으로 가정하고 결재가 완료된 order 의 주문상태를 변경한다.
    public void orderDeliveryArrived(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        orderService.deliveryArrive(nowUser);
    }
}
