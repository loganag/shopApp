package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.Status;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends CrudRepository<Order, Long> {
    List<Order> findByOrderTimeBeforeAndStatus(LocalDateTime creationTime, Status status);

    Optional<Order> findById(Long id);


}
