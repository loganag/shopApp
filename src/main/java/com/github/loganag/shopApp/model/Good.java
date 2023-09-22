package com.github.loganag.shopApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    private Set<OrderGood> orderGoods;
}
