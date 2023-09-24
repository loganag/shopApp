package com.github.loganag.shopApp.service;

import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderRequest;
import java.util.Optional;

public interface OrderService {
  void placeOrder(OrderRequest orderRequest);

  Optional<Order> findById(Long id);

  Order save(Order order);
}
