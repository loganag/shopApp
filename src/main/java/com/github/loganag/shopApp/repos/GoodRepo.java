package com.github.loganag.shopApp.repos;

import com.github.loganag.shopApp.model.Good;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GoodRepo extends CrudRepository<Good, Long> {
  List<Good> findAll();
}
