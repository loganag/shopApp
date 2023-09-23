package com.github.loganag.shopApp.controller;

import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.model.OrderRequest;
import com.github.loganag.shopApp.service.GoodServiceImpl;
import com.github.loganag.shopApp.service.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class MainController {
    GoodServiceImpl goodService;
    OrderServiceImpl orderService;

    @GetMapping
    public Good getGood() {
        return new Good(1L, "Iphone 11", 1, 102.2, new HashSet<>() {
        });
    }

    @PostMapping
    public Long addGood(@RequestBody Good good) {
        return goodService.saveGood(good);
    }

    @GetMapping("/all")
    public List<Good> getAllGoods() {
        List<Good> goods = goodService.findAll();
        return goods;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        //System.out.println(orderRequest);
        try {
            orderService.placeOrder(orderRequest);
            return ResponseEntity.ok("Order placed successfully");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
