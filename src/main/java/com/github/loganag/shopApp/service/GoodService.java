package com.github.loganag.shopApp.service;

import com.github.loganag.shopApp.model.Good;

import java.util.List;

public interface GoodService {
    Long saveGood(Good good);
    List<Good> findAll();
}
