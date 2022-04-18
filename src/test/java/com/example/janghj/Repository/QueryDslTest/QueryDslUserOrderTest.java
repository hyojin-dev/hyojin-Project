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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class QueryDslUserOrderTest {
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
    @DisplayName("QueryDsl 동적 쿼리로 유저 주문 1개 조회 성공")
    void findOneUserOrder() throws Exception {
        // given
        UserOrderSearchDto userOrderSearchDto = new UserOrderSearchDto(user.getId(), order.getId());

        // when
        UserOrderDto userOrderDto = userRepository.findOneUserOrder(userOrderSearchDto);

        // then
        assertEquals("기존에 생성된 userId 값과 QueryDsl 로 조회한 userId 값이 일치해야 합니다.",
                userOrderDto.getUser().getId(), user.getId());
        assertEquals("기존에 생성된 orderId 값과 QueryDsl 로 조회한 orderId 값이 일치해야 합니다.",
                userOrderDto.getOrder().getId(), order.getId());
    }

    @Test
    @DisplayName("QueryDsl 동적 쿼리로 유저 주문 전체 조회 성공")
    void findAllUserOrders() throws Exception {
        // given
        UserOrderSearchDto userOrderSearchDto = new UserOrderSearchDto(user.getId());

        // when
        List<UserOrderDto> findAllUserOrders = userRepository.findAllUserOrders(userOrderSearchDto);

        // then
        assertEquals("userRepository 에서 찾아온 정보의 크기가 1이 되어야 합니다.",
                findAllUserOrders.size(), 1);
    }

    @Test
    @DisplayName("QueryDsl userName 조회 성공")
    void getUserName() {
        // given

        // when
        List<User> allUser = userRepository.findAllUser();

        // then
        assertEquals("userRepository 에서 찾아온 정보의 크기가 1이 되어야 합니다.",
                allUser.size(), 1);
    }

    @Test
    @DisplayName("Spring Data Page 를 이용하여 User, Order 조회 성공")
    void PageTest() {
        // given
        for (int i = 0; i < 10; i++) {
            this.order = orderservice.order(userDetails, orderWebDto);
        }
        UserOrderSearchDto userOrderSearchDto = new UserOrderSearchDto(user.getId());
        PageRequest page = PageRequest.of(0, 3);

        // when
        Page<UserOrderDto> findPageUserOrders = userRepository.findPageUserOrders(userOrderSearchDto, page);

        // then
        assertEquals("Page 로 찾아온 정보의 수만큼 정보를 가져와야 한다.",
                findPageUserOrders.getSize(), 3);
        assertEquals("Page 로 찾아온 정보와 저장했던 값이 같아야 한다.",
                findPageUserOrders.getContent().get(2).getUser().getUsername(), "username");
    }
}
