package com.github.loganag.shopApp.service;

import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.repos.GoodRepo;
import com.github.loganag.shopApp.service.GoodService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoodServiceImpl implements GoodService {
    private final GoodRepo goodRepo;
    @Override
    public Long saveGood(Good good) {
        return goodRepo.save(good).getId();
    }

    @Override
    public List<Good> findAll() {
        return goodRepo.findAll();
    }

    @Override
    public Optional<Good> findById(Long id) {
        return goodRepo.findById(id);
    }

}
