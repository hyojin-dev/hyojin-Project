package com.example.janghj.web;


import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.Pants;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Product.Shoes;
import com.example.janghj.domain.Product.Top;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.service.ProductService;
import com.example.janghj.web.dto.ProductDto;
import com.example.janghj.web.dto.ProductSearchDto;
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

    @Operation(description = "상품 등록하기, 로그인 필요", method = "POST")
    @PostMapping("/item")
    public ResponseEntity<?> registerProduct(@RequestBody ProductDto productDto) {
        /* Product 엔티티가 증가할 수록 해당 코드가 함께 증가합니다.
         * 개선되어야 할 코드 리팩토링 예정입니다.*/
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

    @Operation(description = "특정 상품 조회하기(카테고리 검색 + 최신순)", method = "GET")
    @GetMapping("/item")
    public List<Product> getProduct(ProductSearchDto productSearchDto) {
        return productService.getProducts(productSearchDto);
    }


    @Operation(description = "상품 삭제하기", method = "DELETE")
    @DeleteMapping("/item")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long itemId) {
        productService.deleteProduct(nowUser, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
