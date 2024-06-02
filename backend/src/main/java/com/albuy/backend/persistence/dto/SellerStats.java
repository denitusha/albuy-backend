package com.albuy.backend.persistence.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SellerStats {

    private int totalUnitsSold;
    private BigDecimal totalRevenue;
}
