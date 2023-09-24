package com.github.loganag.shopApp.service;

import com.github.loganag.shopApp.exceptions.GoodNotFoundException;
import com.github.loganag.shopApp.exceptions.InsufficientQuantityException;
import com.github.loganag.shopApp.model.*;
import com.github.loganag.shopApp.repos.OrderGoodRepo;
import com.github.loganag.shopApp.repos.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final GoodServiceImpl goodService;
    private final OrderGoodRepo orderGoodRepo;
    private final OrderRepo orderRepo;



    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        double totalPrice = calculateTotalPrice(orderRequest);

        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        orderRepo.save(order);

        for (OrderGood orderGood : orderRequest.getOrderGoods()) {
            Long goodId = orderGood.getGood().getId();
            int quantityOrdered = orderGood.getQuantity();

            Good good = goodService.findById(goodId)
                    .orElseThrow(() -> new GoodNotFoundException("Good not found with id: " + goodId));

            if (good.getQuantity() < quantityOrdered) {
                throw new InsufficientQuantityException("Not enough quantity available for the good with id: " + goodId);
            }

            //Update the quantity of the current good
            good.setQuantity(good.getQuantity() - quantityOrdered);

            // Save the updated Good entity to the database
            goodService.saveGood(good);

            //Create and save an OrderGood entity
            OrderGood newOrderGood = new OrderGood();
            newOrderGood.setOrder(order);
            newOrderGood.setGood(good);
            newOrderGood.setQuantity(quantityOrdered);
            //Save the orderGood item to the database
            orderGoodRepo.save(newOrderGood);

        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public double calculateTotalPrice(OrderRequest orderRequest) {
        double totalPrice = 0.0;
        for (OrderGood orderGood : orderRequest.getOrderGoods()) {
            System.out.println(orderGood);
            Long goodId;

            goodId = orderGood.getGood().getId();
            Good good = goodService.findById(goodId)
                    .orElseThrow(() -> new GoodNotFoundException("Good not found with id: " + goodId));

            double goodSubtotal = good.getPrice() * orderGood.getQuantity();
            totalPrice += goodSubtotal;
        }
        return totalPrice;
    }

    @Scheduled(fixedRate = 60000)   //Runs every minute
    public void deleteUnpaidOrders(){
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        List<Order> unpaidOrders = orderRepo.findByOrderTimeBeforeAndStatus(tenMinutesAgo, Status.PENDING);

        for(Order order : unpaidOrders){
            List<OrderGood> unpaidOrderGoods = orderGoodRepo.findByOrder(order);

            for (OrderGood orderGood: unpaidOrderGoods) {
                orderGoodRepo.delete(orderGood);
            }

            orderRepo.delete(order);
        }
    }
}
