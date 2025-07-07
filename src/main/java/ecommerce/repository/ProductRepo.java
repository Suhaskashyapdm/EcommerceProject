package ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingOrCategoryContaining(String name, String category, Pageable pageable);
}
