package com.mono.order.service;

import com.mono.common.exception.ResourceNotFoundException;
import com.mono.order.dto.OrderDTO;
import com.mono.order.model.Order;
import com.mono.order.repository.OrderRepository;
import com.mono.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Validate user via User Module API
        if (!userService.isUserActive(orderDTO.getUserId())) {
            throw new IllegalStateException("Cannot create order for inactive user");
        }

        log.info("Creating order for user: {}", orderDTO.getUserId());

        Order order = toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        return toDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return toDTO(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        // Verify user exists via User Module API
        userService.getUserById(userId);

        return orderRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        order.setStatus(status);
        return toDTO(orderRepository.save(order));
    }

    private OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderDate()
        );
    }

    private Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setProductName(dto.getProductName());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        return order;
    }
}