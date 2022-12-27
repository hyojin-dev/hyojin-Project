package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.OrderStatus;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.ProductColor;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCash;
import com.example.janghj.repository.order.OrderRepository;
import com.example.janghj.repository.product.ProductRepository;
import com.example.janghj.repository.user.UserRepository;
import com.example.janghj.repository.userCash.UserCashRepository;
import com.example.janghj.web.dto.AddressDto;
import com.example.janghj.web.dto.OrderWebDto;
import com.example.janghj.web.dto.ProductDto;
import com.example.janghj.web.dto.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderservice;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    UserCashRepository userCashRepository;
    @Autowired
    ProductRepository productRepository;

    UserDetailsImpl userDetails;
    OrderWebDto orderWebDto;
    UserCash userCash;

    @BeforeEach
    void beforeEach() {
        UserDto userDto = new UserDto(
                "test1234", "password", "email",
                new AddressDto("city", "street", "zipcode"),
                false, "");
        User user = userService.registerUser(userDto);
        this.userDetails = new UserDetailsImpl(user);

        this.userCash = userService.depositUserCash(userDetails.getUser(), 20000);

        ProductDto productDto = new ProductDto("TestProduct", 1000, 1000, Category.TOP, ProductColor.RED, 130);
        productService.registerProduct(productDto);

        Product product = productRepository.findByName("TestProduct");

        Map orderList = new ConcurrentHashMap<String, Integer>();
        String productId = product.getId().toString();
        Integer quantity = 10;
        orderList.put(productId, quantity);

        this.orderWebDto = new OrderWebDto(orderList, new Address("city", "street", "zipcode"));
    }

    @Test
    @DisplayName("주문하기 성공")
    void order() throws Throwable {
        // given

        // when
        Order order = orderservice.order(userDetails, orderWebDto);

        // then
        Order findByOrder = orderservice.findByOrder(userDetails, order.getId());
        assertEquals("주문 수량이 1이어야 합니다.",
                order.getId(), findByOrder.getId());
        assertEquals("생성된 orderId 값과 찾아낸 orderId 값이 일치해야 합니다.",
                order.getId(), findByOrder.getId());
        assertEquals("생성된 order 상품들과 찾아낸 order 상품들의 값이 일치해야 합니다.",
                order.getOrderProduct(), findByOrder.getOrderProduct());
        assertEquals("생성된 order 상품의 값과 찾아낸 order 상품의 값이 일치해야 합니다.",
                order.getTotalAmount(), findByOrder.getTotalAmount());
    }

    @Test
    @DisplayName("주문 삭제 성공")
    void orderCancel() throws Throwable {
        // given
        Order order = orderservice.order(userDetails, orderWebDto);

        // when
        orderservice.cancelOrder(userDetails, order.getId());

        // then
        assertEquals("저장된 주문이 삭제되어 orderRepository 의 크기가 0 이 되어야 합니다.", orderRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("1개 상품 결제 성공")
    void payForTheOrder() throws Throwable {
        // given
        Order order = orderservice.order(userDetails, orderWebDto);

        // when
        orderservice.payForTheOrder(userDetails, order.getId());

        // then
        assertEquals("Order 주문 상태가 PaymentCompleted 로 변경되어야 합니다."
                , order.getOrderStatus(), OrderStatus.PaymentCompleted);
        assertEquals("구매한 상품의 수량이 줄어들어야 합니다. 1000 -> 990"
                , order.getOrderProduct().get(0).getProduct().getStockQuantity(), 990);
        assertEquals("상품을 구매한 user 보유 금액이 변경되어야 합니다. (사용자가 보유한 금액 - 구매한 상품 가격)"
                , userCash.getMoney(), 10000);
    }

    @Test
    @DisplayName("결제 전 전체 상품 결제하기")
    void findByOrders() throws Throwable {
        //given
        // 결제가 완료된 주문이 존재하는 상태 보유금액
        Order order1 = orderservice.order(userDetails, orderWebDto);
        orderservice.payForTheOrder(userDetails, order1.getId());
        // 현재 보유금액 = 10,000(20,000(기존보유금액)-10,000(order1상품금액))

        // order1 는 결제가 완료되어 제외하고 order2 만 결제 되어야 합니다.
        Order order2 = orderservice.order(userDetails, orderWebDto);

        // when
        orderservice.payForTheOrders(userDetails);

        // then
        assertEquals("결제 완료된 상품(order1)을 제외하고 결제가 안된(order2) 상품만 결제되어야 합니다."
                , userDetails.getUserCash().getMoney(), 0);
    }
}