package com.albuy.backend.persistence.service;

import com.albuy.backend.persistence.dto.Purchase;
import com.albuy.backend.persistence.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
