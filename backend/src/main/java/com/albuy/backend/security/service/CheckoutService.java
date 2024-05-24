package com.albuy.backend.security.service;


import com.albuy.backend.persistence.dto.Purchase;
import com.albuy.backend.persistence.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
