package ecommerceproject.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import ecommerceproject.entitymodel.*;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}






