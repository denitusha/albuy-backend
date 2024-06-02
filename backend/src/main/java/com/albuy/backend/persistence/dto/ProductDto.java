package com.albuy.backend.persistence.dto;

import com.albuy.backend.persistence.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


    private Long id;

    private String category;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Boolean canReview;


    private Long sellerId;
    private String companyName;

    private List<ReviewDto> reviews;


    public static ProductDto fromProduct(Product product) {


        List<ReviewDto> reviews = new ArrayList<>();
        product.getReviews().forEach(review -> {
            reviews.add(ReviewDto.fromReview(review));
        });
        return ProductDto.builder()
                .id(product.getId())
                .category(product.getCategory().getCategoryName())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .reviews(reviews)
                .sellerId(product.getSellerId())
                .build();
    }


}
