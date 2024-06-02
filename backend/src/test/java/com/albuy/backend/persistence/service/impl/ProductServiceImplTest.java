package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.ProductDto;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.entity.ProductCategory;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.ProductCategoryRepository;
import com.albuy.backend.persistence.repository.ProductRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllBySellerIdReturnsEmptyListWhenNoProductsFound() {
        when(productRepository.findAllBySellerId(anyLong())).thenReturn(List.of());
        assertTrue(productService.findAllBySellerId(1L).isEmpty());
    }

    @Test
    public void findAllBySellerIdReturnsProductListWhenProductsFound() {

        Product product = Product.builder()
                .id(1L)
                .title("Product")
                .description("Description")
                .price(BigDecimal.valueOf(100.0))
                .image("image.jpg")
                .sellerId(1L)
                .category(new ProductCategory())
                .build();
        product.setReviews(new ArrayList<>());

        when(productRepository.findAllBySellerId(anyLong())).thenReturn(List.of(product));

        assertFalse(productService.findAllBySellerId(1L).isEmpty());
    }

    @Test
    public void findAllByCategoryNameReturnsEmptyListWhenNoCategoryFound() {
        when(productCategoryRepository.findByCategoryName(anyString())).thenReturn(Optional.empty());
        assertTrue(productService.findAllByCategoryName("Electronics").isEmpty());
    }

    @Test
    public void deleteProductCallsDeleteByIdOnProductRepository() {
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void saveProductSavesNewCategoryWhenCategoryNotFound() {
        ProductDto productDto = new ProductDto();
        productDto.setCategory("Electronics");
        when(productCategoryRepository.findByCategoryName(anyString())).thenReturn(Optional.empty());
        productService.saveProduct(productDto);
        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
    }



    @Test
    public void getProductDetailReturnsProductDtoWhenProductFound() {
        // Create a Product object with a non-null reviews field
        Product product = Product.builder()
                .id(1L)
                .title("Product")
                .description("Description")
                .price(BigDecimal.valueOf(100.0))
                .image("image.jpg")
                .sellerId(1L)
                .category(new ProductCategory())
                .build();
        product.setReviews(new ArrayList<>());


        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class), anyLong(), anyLong())).thenReturn(true);
        // Use the product object in your mock
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        assertNotNull(productService.getProductDetail(1L, 1L));
    }

    @Test
    public void hasUserBoughtProductReturnsFalseWhenNoPurchaseFound() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Boolean.class), anyLong(), anyLong())).thenReturn(false);
        assertFalse(productService.hasUserBoughtProduct(1L, 1L));
    }
}
