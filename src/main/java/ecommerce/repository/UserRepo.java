package ecommerce.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.*;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}






