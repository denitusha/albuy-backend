package com.albuy.backend.controller;

import com.albuy.backend.persistence.dto.ProductDto;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productDetailService;


    @GetMapping("/product/detail/{productId}/{userId}")
    public ResponseEntity<ProductDto> getProductDetail(@PathVariable Long productId, @PathVariable Long userId) {
        log.info("Request to get product detail for product id: {} and user id: {}", productId, userId);
        ProductDto productDetail = productDetailService.getProductDetail(productId, userId);

        log.info("Returning product detail: ");
        if (productDetail != null) {
            return ResponseEntity.ok(productDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        log.info("Request to get all products");
        List<ProductDto> products = productDetailService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/seller/{sellerId}")
    public ResponseEntity<List<ProductDto>> getProductsBySeller(@PathVariable Long sellerId) {
        log.info("Request to get all products by seller: {}", sellerId);
        List<ProductDto> products = productDetailService.findAllBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        log.info("Request to get all products");
        List<ProductDto> products = productDetailService.getProductsByNameContaining(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String categoryName) {
        log.info("Request to get all products by category: {}", categoryName);
        List<ProductDto> products = productDetailService.findAllByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product/save")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto) {
        log.info("Request to save product: {}", productDto);
        Product savedProduct = productDetailService.saveProduct(productDto); // Save the product and get the saved product DTO
        return ResponseEntity.ok(savedProduct); // Return the saved product DTO in the response
    }


    @DeleteMapping("/products/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        log.info("Request to delete product with id: {}", productId);
        productDetailService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
