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

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(Long id) {
        Product product = (Product) productRepository.getById(id);

        if (product == null) {
            throw new NullPointerException("해당 상품이 존재하지 않습니다. productId = " + id);
        }
        return product;
    }

    public List<Product> getProducts(ProductSearchDto productSearchDto) {
        //    1.최신순(default), 2.상품만 검색, 3.상품 검색 + 최신순 검색
        if (productSearchDto.getCategory() != null && productSearchDto.getSort() == null) {
            return productRepository.findAllByCategory(productSearchDto.getCategory());
        } else if (productSearchDto.getCategory() != null && productSearchDto.getSort().equals("date")) {
            return productRepository.findAllByCategory(productSearchDto.getCategory(), Sort.by(Sort.Direction.DESC, "CreatedAt"));
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
