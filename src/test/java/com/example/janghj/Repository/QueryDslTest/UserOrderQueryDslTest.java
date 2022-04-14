package com.example.janghj.Repository.QueryDslTest;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.ProductColor;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.repository.UserRepositoryImpl;
import com.example.janghj.repository.dto.UserOrderDto;
import com.example.janghj.repository.dto.UserOrderSearchDto;
import com.example.janghj.service.OrderService;
import com.example.janghj.service.ProductService;
import com.example.janghj.service.UserService;
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
public class UserOrderQueryDslTest {
    @Autowired
    UserRepositoryImpl userRepositoryImpl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderservice;
    @Autowired
    OrderRepository orderRepository;

    User user;
    UserDetailsImpl userDetails;
    UserDto userDto;

    Order order;
    OrderWebDto orderWebDto;

    @BeforeEach
    void beforeEach() {
        this.userDto = new UserDto("username",
                "password",
                "email",
                new AddressDto("city", "street", "zipcode"));

        this.user = userService.registerUser(userDto);
        this.userDetails = new UserDetailsImpl(user);

        ProductDto productDto = new ProductDto("TestProduct", 1000, 1000, Category.TOP, ProductColor.RED, 130);
        productService.registerProduct(productDto);

        Product product = productRepository.findByName("TestProduct");

        Map orderList = new ConcurrentHashMap<String, Integer>();
        String productId = product.getId().toString();
        Integer quantity = 10;
        orderList.put(productId, quantity);

        this.orderWebDto = new OrderWebDto(orderList, new Address("city", "street", "zipcode"));
        this.order = orderservice.order(userDetails, orderWebDto);
    }

    @Test
    @DisplayName("QueryDsl 동적 쿼리로 조회 성공")
    void order() throws Exception {
        // given
        UserOrderSearchDto userOrderSearchDto = new UserOrderSearchDto(user.getId(), order.getId());

        // when
        UserOrderDto userOrderDto = userRepositoryImpl.userOrderSearch(userOrderSearchDto);

        // then
        assertEquals("기존에 생성된 userId 값과 QueryDsl 로 조회한 userId 값이 일치해야 합니다.",
                userOrderDto.getUser().getId(), user.getId());
        assertEquals("기존에 생성된 orderId 값과 QueryDsl 로 조회한 orderId 값이 일치해야 합니다.",
                userOrderDto.getOrder().getId(), order.getId());
    }
}