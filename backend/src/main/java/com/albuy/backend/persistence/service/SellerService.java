package com.albuy.backend.persistence.service;

import com.albuy.backend.persistence.dto.SellerStats;

import java.math.BigDecimal;

public interface SellerService {

    SellerStats getSellerStats(Long sellerId);
}
