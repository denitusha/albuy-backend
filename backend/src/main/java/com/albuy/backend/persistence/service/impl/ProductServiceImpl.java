package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.ProductDto;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.entity.ProductCategory;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.ProductCategoryRepository;
import com.albuy.backend.persistence.repository.ProductRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.persistence.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;


    @Override
    public List<ProductDto> findAllBySellerId(Long sellerId) {
        return productRepository.findAllBySellerId(sellerId).stream().map(ProductDto::fromProduct).toList();
    }

    @Override
    public List<ProductDto> findAllByCategoryName(String categoryName) {
        ProductCategory productCategory = productCategoryRepository.findByCategoryName(categoryName).orElse(null);
        if(productCategory == null){
            return List.of();
        }
        return productRepository.findAllByCategoryCategoryName(categoryName).stream().map(ProductDto::fromProduct).toList();
    }

    @Override
    public void deleteProduct(Long productId) {
         productRepository.deleteById(productId);
    }

    @Override
    public Product saveProduct(ProductDto productDto) {

        ProductCategory category = productCategoryRepository.findByCategoryName(productDto.getCategory()).orElse(null);
        if(category == null){
            category = new ProductCategory();
            category.setCategoryName(productDto.getCategory());
            category = productCategoryRepository.save(category);
        }

        Product product = Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .image(productDto.getImage())
                .category(category) // Set the saved category directly
                .sellerId(productDto.getSellerId())
                .build();

        return productRepository.save(product);
    }


    @Override
    public List<ProductDto> getProductsByNameContaining(String name) {
        return productRepository.findByTitleContaining(name).stream().map(ProductDto::fromProduct).toList();
    }

    @Override
    public List<ProductDto> getProducts() {

        return productRepository.findAll().stream().map(ProductDto::fromProduct).toList();
    }

    @Override
    public ProductDto getProductDetail(Long ProductId, Long UserId) {
        log.info("Fetching product detail for product id: {} and user id: {}", ProductId, UserId);
        Product product = productRepository.findById(ProductId).orElse(null);
        User seller = userRepository.findById(product.getSellerId()).orElse(null);
        if (product != null) {
            log.info("Product found: {}", product);
            ProductDto productDto = ProductDto.fromProduct(product);
            productDto.setCanReview(hasUserBoughtProduct(UserId, ProductId));
            productDto.setCompanyName(seller.getCompany_name());

            log.info("Returning product detail: {}", productDto);
            return productDto;
        } else {
            log.warn("Product with id {} not found", ProductId);
            return null;
        }
    }




    public boolean hasUserBoughtProduct(long userId, long productId) {
        String sql = "SELECT COUNT(*) > 0 " +
                "FROM orders o " +
                "INNER JOIN order_item oi ON o.id = oi.order_id " +
                "WHERE o.buyer_id = ? " +
                "AND oi.product_id = ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productId);
    }
}
