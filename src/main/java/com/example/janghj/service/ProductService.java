package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

//    401(권한 없음) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    402(결제 필요) return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
//    500(서버 오류) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    public Product getProduct(Long id) {
        Product product = (Product) productRepository.getById(id);

        if (product == null) {
            throw new NullPointerException("해당 상품이 존재하지 않습니다. productId = " + id);
        }
        return product;
    }

    public List<Product> getProducts(Category category, String sort) {
        //    1.최신순(default), 2.상품만 검색, 3.상품 검색 + 최신순 검색
        if (category != null && sort == null) {
            return productRepository.findAllByCategory(category);
        } else if (category != null && sort.equals("date")) {
            return productRepository.findAllByCategory(category, Sort.by(Sort.Direction.DESC, "CreatedAt"));
        }
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "CreatedAt"));
    }

    public void deleteProduct(UserDetailsImpl nowUser, Long itemId) {
        if (!nowUser.getUserRole().equals(UserRole.ADMIN)) {
            return;
        }
        productRepository.deleteById(itemId);
    }
}
