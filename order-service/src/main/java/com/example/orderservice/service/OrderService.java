package com.example.orderservice.service;


import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.modal.Order;
import com.example.orderservice.modal.OrderLineItems;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    /**
     * order
     * @param orderRequest
     */
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> nameProduct = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getName)
                .toList();
        // Call Inventory Service, and place order if product is in
        // stock
        ProductResponse[] productResponsArray = webClient.get()
                .uri("http://localhost:8082/api/product/isStock",
                        uriBuilder -> uriBuilder.queryParam("name", nameProduct).build())
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(productResponsArray)
                .allMatch(ProductResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
    /**
     * get order theo id
     */

    public Order getOrderById(long id) {
        Order order= orderRepository.findById(id).get();
        return order;
    }
    /**
     * delete order by id
     */

    public String deleteOrderById(long orderId) {
        orderRepository.deleteById(orderId);
        return "xoa thanh cong";
    }


    /**
     * get all order
     * @return
     */
    public List<Order> getAllOrder() {
        List<Order> products = orderRepository.findAll();
        return products.stream().map(this::mapToOrderResponse).toList();
    }



    private OrderLineItems mapToDto(OrderLineItems orderLineItem1) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItem1.getPrice());
        orderLineItems.setQuantity(orderLineItem1.getQuantity());
        orderLineItems.setName(orderLineItem1.getName());
        return orderLineItems;
    }
    private Order mapToOrderResponse(Order order) {

        return Order.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(order.getOrderLineItemsList())
                .build();
    }
}