package com.example.janghj.web;


import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.Pants;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.Shoes;
import com.example.janghj.domain.Product.Top;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.service.ProductService;
import com.example.janghj.web.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

//    401(권한 없음) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    402(결제 필요) return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
//    404(값이 없음) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    406(사용자로부터 받는 값 부족) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    416(처리할 수 없는 요청범위) return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
//    500(서버 오류) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    @Operation(description = "상품 등록하기, 로그인 필요", method = "POST")
    @PostMapping("/product")
    public ResponseEntity<?> registerProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody ProductDto productDto) {
        /* Product 엔티티가 증가할 수록 해당 코드가 함께 증가합니다.
         * 개선되어야 할 코드 리팩토링 예정입니다.*/
        if (nowUser.getUserRole() != UserRole.ADMIN) {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (productDto.getCategory().toString().equals("TOP")) {
            Top top = new Top(productDto);
            productRepository.save(top);
        } else if (productDto.getCategory().toString().equals("PANTS")) {
            Pants pants = new Pants(productDto);
            productRepository.save(pants);
        } else if (productDto.getCategory().toString().equals("SHOES")) {
            Shoes shoes = new Shoes(productDto);
            productRepository.save(shoes);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "특정 상품 조회하기(단품)", method = "GET")
    @GetMapping("/product")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @Operation(description = "상품 업데이트, 로그인 필요", method = "PUT")
    @PutMapping("/product")
    public Product updateProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long productId, @RequestBody ProductDto productDto) {
        if (nowUser.getUserRole() != UserRole.ADMIN) {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return productService.updateProduct(nowUser, productId, productDto);
    }

    @Operation(description = "상품 조회하기(카테고리 검색 + 최신순)", method = "GET")
    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam String category, @RequestParam String sort) {
        Category categoryValue = Category.valueOf(category);
        return productService.getProducts(categoryValue, sort);
    }

    @Operation(description = "상품 삭제하기, 로그인 필요", method = "DELETE")
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long itemId) {
        if (nowUser.getUserRole() != UserRole.ADMIN) {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        productService.deleteProduct(nowUser, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
