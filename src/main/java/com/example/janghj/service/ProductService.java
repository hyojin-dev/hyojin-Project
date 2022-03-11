package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.web.dto.ProductSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void getProduct(Long id) {
        Optional product = productRepository.findById(id);
//
    }

    //    default = 최신순
    //    if (카테고리 검색 조건 || 최신순)
    //    else if (카테고리 검색 조건 && 최신순)
    public List<Product> getProducts(ProductSearchDto productSearchDto) {
        if (productSearchDto.getCategory() != null && productSearchDto.getSort().equals("date")) {
            return productRepository.findAllByCategory(productSearchDto.getCategory());
        } else if (productSearchDto.getCategory() != null) {
            return productRepository.findAllByCategory(productSearchDto.getCategory());
        }
        return productRepository.findAllByCategory(productSearchDto.getCategory(), Sort.by(Sort.Direction.DESC, "CreatedAt"));
    }

    public void deleteProduct(UserDetailsImpl nowUser, Long itemId) {
        if (!nowUser.getUserRole().equals(UserRole.ADMIN)) {
            return;
        }
        productRepository.deleteById(itemId);
    }
}
