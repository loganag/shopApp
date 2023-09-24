package com.github.loganag.shopApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Goods")
public class Good {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Column(name = "quantity", nullable = false)
  int quantity;

  @Column(name = "price", nullable = false)
  private double price;

  @OneToMany(mappedBy = "good")
  @JsonIgnore
  private Set<OrderGood> orderGoods;
}
