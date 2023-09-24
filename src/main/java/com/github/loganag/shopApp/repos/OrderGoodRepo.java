package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderGood;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderGoodRepo extends CrudRepository<OrderGood, Long> {
    List<OrderGood> findByOrder(Order order);
}
