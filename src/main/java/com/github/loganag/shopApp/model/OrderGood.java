package com.github.loganag.shopApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderGoods")
public class OrderGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public String toString() {
        return "OrderGood{" +
                "id=" + id +
                ", good=" + good +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}
