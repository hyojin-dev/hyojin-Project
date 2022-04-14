package com.example.janghj.web;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.UserRole;
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

    @Operation(description = "상품 등록하기, 로그인 필요", method = "POST")
    @PostMapping("/product")
    public ResponseEntity<?> registerProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @RequestBody ProductDto productDto) {
        /* Product 엔티티가 증가할 수록 해당 코드가 함께 증가합니다.
         * 개선되어야 할 코드 리팩토링 예정입니다.*/
        if (nowUser.getUserRole() == UserRole.USER) {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productService.registerProduct(productDto);
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
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long productId) {
        if (nowUser.getUserRole() != UserRole.ADMIN) {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        productService.deleteProduct(nowUser, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
