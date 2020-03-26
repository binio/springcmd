package com.binio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.binio.model.Order;
import com.binio.model.OrderApi;
import com.binio.model.OrderCreateApi;
import com.binio.model.Product;
import com.binio.repository.OrderRepository;
import com.binio.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Optional<OrderApi> addProduct(final Long productId, final Long orderId) {
        if(productRepository.existsById(productId) && orderRepository.existsById(orderId)) {
            Product product = productRepository.findById(productId).get();
            Order order = orderRepository.findById(orderId).get();
            order.addProduct(product);
            order = orderRepository.save(order);
            return Optional.of(convert(order));

        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<OrderApi> addOrder(final OrderCreateApi orderCreateApi) throws OrderApiException {
        if(orderCreateApi == null){
            throw new OrderApiException();
        } else {
            Order order = Order.builder()
                    .order_email(orderCreateApi.getOrder_email())
                    .products(new HashSet<>())
                    .build();
            return Optional.of(convert(orderRepository.save(order)));
        }

    }

    @Override
    public Optional<OrderApi> getOrderSummary(final Long orderId) {
        if(orderRepository.existsById(orderId)) {
            return Optional.of(convert((orderRepository.findById(orderId).get())));
        } else {
            return Optional.empty();
        }
    }

    private OrderApi convert(Order order) {
        List<String> productList = order.getProducts().stream().map(
                p -> p.getProduct_name()).collect(Collectors.toList());

        return OrderApi.builder()
                .order_email(order.getOrder_email())
                .order_id(order.getOrder_id())
                .order_total(getTotal(order))
                .products(productList)
                .build();
    }

    private double getTotal(Order order) {
        return order.getProducts().stream()
                .map( p -> p.getProduct_price())
                .reduce(0.00, (a, b) -> a + b);
    }
}
