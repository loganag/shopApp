package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderGood;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface OrderGoodRepo extends CrudRepository<OrderGood, Long> {
  List<OrderGood> findByOrder(Order order);
}
