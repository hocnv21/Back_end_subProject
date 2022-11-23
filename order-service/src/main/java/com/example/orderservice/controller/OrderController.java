package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.modal.Order;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    /**
     * create order
     * @param orderRequest
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    /**
     * get all order
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllProducts() {
        return orderService.getAllOrder();
    }

    /**
     * get order by id
     */

    @GetMapping("/{id}")
    @Cacheable(value = "order", key = "#id")
    public Order getRoleById(@PathVariable long id) {
        Order order = orderService.getOrderById(id);
        return order;
    }

    /**
     * delete order by id
     */
    @DeleteMapping("/{OrderId}")
    @CacheEvict(value = "order", key = "#OrderId")
    public String deleteProduct(@PathVariable long  OrderId) {
        orderService.deleteOrderById(OrderId);
        return "xoá thành công id" + OrderId;
    }

}