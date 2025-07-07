package ecommerceproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerceproject.entitymodel.Order;
import ecommerceproject.entitymodel.User;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
