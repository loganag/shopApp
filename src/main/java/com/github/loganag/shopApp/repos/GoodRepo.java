package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Good;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodRepo extends CrudRepository<Good, Long> {
    List<Good> findAll();
}
