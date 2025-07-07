package ecommerce.entity;

import java.time.LocalDateTime;
//import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Order {
    @Id 
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;

    private double totalAmount;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
