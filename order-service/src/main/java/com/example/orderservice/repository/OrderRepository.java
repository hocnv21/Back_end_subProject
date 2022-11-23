package com.example.orderservice.repository;


import com.example.orderservice.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}