package com.albuy.backend.security.service;

import com.webproject.foodservice.dto.Purchase;
import com.webproject.foodservice.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
