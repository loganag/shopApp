package com.github.loganag.shopApp.controller;

import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.model.OrderGood;
import com.github.loganag.shopApp.repos.GoodRepo;
import com.github.loganag.shopApp.serviceImpl.GoodServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class MainController {
    GoodServiceImpl goodService;

    @GetMapping
    public Good getGood(){
        return new Good(1L, "Iphone 11", 1, 102.2, new HashSet<>() {});
    }
    @PostMapping
    public Long addGood(@RequestBody Good good){
        return goodService.saveGood(good);
    }
    @GetMapping("/all")
    public List<Good> getAllGoods(){
        List<Good> goods = goodService.findAll();
        return goods;
    }
}
