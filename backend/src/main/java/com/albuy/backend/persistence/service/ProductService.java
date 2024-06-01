package com.albuy.backend.persistence.service;

import com.albuy.backend.persistence.dto.ProductDto;
import com.albuy.backend.persistence.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto getProductDetail(Long ProductId, Long UserId);

    List<ProductDto> getProducts();

    List<ProductDto> getProductsByNameContaining(String name);

    Product saveProduct(ProductDto productDto);

    void deleteProduct(Long productId);

    List<ProductDto> findAllByCategoryName(String categoryName);
    List<ProductDto> findAllBySellerId(Long sellerId);

}
