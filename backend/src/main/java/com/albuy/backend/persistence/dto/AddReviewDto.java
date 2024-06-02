package com.albuy.backend.persistence.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddReviewDto {


    private String description;

    private float rating;


    private Long user;



    private Long product;

}
