package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.DeliveryStatus;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.ProductColor;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCash;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.repository.UserCashRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.AddressDto;
import com.example.janghj.web.dto.OrderWebDto;
import com.example.janghj.web.dto.ProductDto;
import com.example.janghj.web.dto.UserDto;
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

    @BeforeEach
    void beforeEach() {
        UserDto userDto = new UserDto(
                "test1234", "password", "email",
                new AddressDto("city", "street", "zipcode"),
                false, "");
        User user = userService.registerUser(userDto);
        this.userDetails = new UserDetailsImpl(user);

        ProductDto productDto = new ProductDto("TestProduct", 1000, 1000, Category.TOP, ProductColor.RED, 130);
        productService.registerProduct(productDto);

        Product product = productRepository.findByName("TestProduct");

        String productId = product.getId().toString();
        Integer quantity = 10;

        Map orderList = new ConcurrentHashMap<String, Integer>();
        orderList.put(productId, quantity);

        this.orderWebDto = new OrderWebDto(orderList, new Address("city", "street", "zipcode"));
    }

    @Test
    @DisplayName("주문하기 성공")
    void order() throws Exception {
        // given
        Order order = orderservice.order(userDetails, orderWebDto);

        // when
        Order findByOrder = orderservice.findByOrder(userDetails, order.getId());

        // then
        assertEquals("생성된 orderId 값과 찾아낸 orderId 값이 일치해야 합니다.",
                order.getId(), findByOrder.getId());
        assertEquals("생성된 order 상품들과 찾아낸 order 상품들의 값이 일치해야 합니다.",
                order.getOrderProduct(), findByOrder.getOrderProduct());
        assertEquals("생성된 order 상품의 값과 찾아낸 order 상품의 값이 일치해야 합니다.",
                order.getTotalAmount(), findByOrder.getTotalAmount());
    }

    @Test
    @DisplayName("주문 삭제 성공")
    void orderCancel() throws Exception {
        // given
        Order order = orderservice.order(userDetails, orderWebDto);

        // when
        orderservice.orderCancel(userDetails, order.getId());

        // then
        assertEquals("저장된 주문이 삭제되어 orderRepository 의 크기가 0 이 되어야 합니다.", orderRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("10개 상품 주문 및 결재 성공")
    void payForTheOrder() throws Exception {
        // given
        UserCash userCash = userService.depositUserCash(userDetails.getUser(), 20000);
        Order order = orderservice.order(userDetails, orderWebDto);

        // when
        orderservice.payForTheOrder(userDetails, order.getId());

        // then
        assertEquals("Order 주문 상태가 PaymentCompleted 로 변경되어야 합니다."
                , order.getDelivery().getStatus(), DeliveryStatus.PaymentCompleted);
        assertEquals("상품을 구매한 User 보유 금액이 변경되어야 합니다. (사용자가 보유한 금액 - 구매한 상품 가격)"
                , userCash.getMoney(), 10000);
        assertEquals("구매한 상품의 수량이 줄어들어야 합니다. 1000 -> 990"
                , order.getOrderProduct().get(0).getProduct().getStockQuantity(), 990);
    }
}
