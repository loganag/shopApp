package com.github.loganag.shopApp.service;

import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
