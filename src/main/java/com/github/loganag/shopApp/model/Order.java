package com.github.loganag.shopApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "order_time", nullable = false)
  LocalDateTime orderTime;

  @Column(name = "total_price", nullable = false)
  double totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonIgnore
  private Set<OrderGood> orderGoods;

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", orderTime="
        + orderTime
        + ", totalPrice="
        + totalPrice
        + ", status="
        + status
        + '}';
  }
}
