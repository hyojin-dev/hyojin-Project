package com.example.janghj.Repository.QueryDslTest;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Address;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.ProductColor;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.product.ProductRepository;
import com.example.janghj.repository.search.SearchRepositoryImpl;
import com.example.janghj.repository.user.UserRepository;
import com.example.janghj.repository.user.UserRepositoryImpl;
import com.example.janghj.repository.dto.UserOrderProductDto;
import com.example.janghj.repository.dto.UserOrderProductSearchDto;
import com.example.janghj.repository.order.OrderRepository;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class QueryDslUserOrderProductTest {
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
    @Autowired
    SearchRepositoryImpl searchRepositoryImpl;

    User user;
    UserDetailsImpl userDetails;
    Product product;
    Order order;

    @BeforeEach
    void beforeEach() throws Throwable {
        UserDto userDto = new UserDto("username",
                "password",
                "email",
                new AddressDto("city", "street", "zipcode"));

        this.user = userService.registerUser(userDto);
        this.userDetails = new UserDetailsImpl(user);

        ProductDto orderProductDto = new ProductDto("TestOrderProduct", 1000, 1000, Category.TOP, ProductColor.RED, 130);
        productService.registerProduct(orderProductDto);

        this.product = productRepository.findByName("TestOrderProduct");

        Map orderList = new ConcurrentHashMap<String, Integer>();
        String productId = product.getId().toString();
        Integer quantity = 10;
        orderList.put(productId, quantity);

        OrderWebDto orderWebDto = new OrderWebDto(orderList, new Address("city", "street", "zipcode"));
        this.order = orderservice.order(userDetails, orderWebDto);
    }

    @Test
    @DisplayName("QueryDsl user, Order, Product 검색 성공")
    void PageTest() {
        // given
        // QueryDsl 동적 쿼리로 조회할 저장되는 상품
        ProductDto productDto = new ProductDto("TestProduct", 1000, 1000, Category.TOP, ProductColor.RED, 130);
        productService.registerProduct(productDto);

        Product ProductSearch = productRepository.findByName("TestProduct");

        UserOrderProductSearchDto userOrderProductSearchDto = new UserOrderProductSearchDto(null, null, null, "TestProduct");

        // when
        List<UserOrderProductDto> userOrderProductDto = searchRepositoryImpl.SearchUserOrderProduct(userOrderProductSearchDto);

        // then
        assertEquals("QueryDsl 로 찾아온 user ID와 저장된 ID 값이 같아야 한다.",
                userOrderProductDto.get(0).getUser().getId(), user.getId());
        assertEquals("QueryDsl 로 찾아온 Order ID와 저장된 ID 값이 같아야 한다.",
                userOrderProductDto.get(0).getOrder().getId(), order.getId());
        assertEquals("QueryDsl 로 찾아온 OrderProduct ID와 저장된 ID 값이 같아야 한다.",
                userOrderProductDto.get(0).getOrderProduct().getId(), order.getOrderProduct().get(0).getId());
        assertEquals("QueryDsl 로 찾아온 Product ID와 저장된 ID 값이 같아야 한다.",
                userOrderProductDto.get(0).getProduct().getId(), ProductSearch.getId());
    }
}
