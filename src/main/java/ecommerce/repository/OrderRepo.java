package ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.Order;
import ecommerce.entity.User;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
