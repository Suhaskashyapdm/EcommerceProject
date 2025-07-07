package ecommerceproject.controller;

import ecommerceproject.entitymodel.CartItem;
import ecommerceproject.entitymodel.Order;
import ecommerceproject.entitymodel.OrderItem;
import ecommerceproject.entitymodel.User;
import ecommerceproject.repository.CartItemRepo;
import ecommerceproject.repository.OrderRepo;
import ecommerceproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepo orderRepository;
    private final CartItemRepo cartItemRepository;
    private final UserRepo userRepository;
//Create order
    @PostMapping("/ordercreate")
    @Transactional
    public ResponseEntity<String> createOrder(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        List<OrderItem> orderItems = cartItems.stream()
                .map(ci -> OrderItem.builder()
                        .product(ci.getProduct())
                        .quantity(ci.getQuantity())
                        .build())
                .toList();

        Order order = Order.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .totalAmount(total)
                .items(orderItems)
                .build();

        orderRepository.save(order);
        cartItemRepository.deleteByUser(user);

        return ResponseEntity.ok("Order placed..");
    }

    //order list
    @GetMapping
    public List<Order> getOrders(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        return orderRepository.findByUser(user);
    }
}
