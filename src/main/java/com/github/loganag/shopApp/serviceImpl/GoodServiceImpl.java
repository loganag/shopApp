package com.github.loganag.shopApp.serviceImpl;

import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.repos.GoodRepo;
import com.github.loganag.shopApp.service.GoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GoodServiceImpl implements GoodService {
    GoodRepo goodRepo;
    @Override
    public Long saveGood(Good good) {
        return goodRepo.save(good).getId();
    }

    @Override
    public List<Good> findAll() {
        return goodRepo.findAll();
    }
}
