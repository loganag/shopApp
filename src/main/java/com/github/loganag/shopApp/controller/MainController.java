package com.github.loganag.shopApp.controller;

import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderRequest;
import com.github.loganag.shopApp.model.Status;
import com.github.loganag.shopApp.service.GoodServiceImpl;
import com.github.loganag.shopApp.service.OrderServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class MainController {
  GoodServiceImpl goodService;
  OrderServiceImpl orderService;

  @PostMapping("/add")
  public Long addGood(@RequestBody Good good) {
    return goodService.saveGood(good);
  }

  @GetMapping("/all")
  public List<Good> getAllGoods() {
    return goodService.findAll();
  }

  @PostMapping("/place")
  public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
    try {
      orderService.placeOrder(orderRequest);
      return ResponseEntity.ok("Order placed successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping("/{orderId}/markAsPaid")
  public ResponseEntity<String> markOrderAsPaid(@PathVariable Long orderId) {
    // Retrieve the order from the database
    Optional<Order> optionalOrder = orderService.findById(orderId);

    if (optionalOrder.isPresent()) {
      Order order = optionalOrder.get();

      // Check if the order is not already paid
      if (order.getStatus() != Status.PAID) {
        order.setStatus(Status.PAID);
        orderService.save(order);
        return new ResponseEntity<>("Order marked as paid", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Order is already paid", HttpStatus.BAD_REQUEST);
      }
    } else {
      return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
    }
  }
}
