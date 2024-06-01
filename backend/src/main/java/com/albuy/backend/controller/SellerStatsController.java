package com.albuy.backend.controller;

import com.albuy.backend.persistence.dto.SellerStats;
import com.albuy.backend.persistence.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SellerStatsController {

    private final SellerService sellerService;

    @GetMapping("/seller-stats/{sellerId}")
    public SellerStats getSellerStats(@PathVariable Long sellerId) {
        log.info("Getting seller stats");
        return sellerService.getSellerStats(sellerId);
    }
}
