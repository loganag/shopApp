package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {

}
