package ecommerce.controller;

import ecommerce.entity.CartItem;
import ecommerce.entity.Product;
import ecommerce.entity.User;
import ecommerce.repository.CartItemRepo;
import ecommerce.repository.ProductRepo;
import ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemRepo cartItemRepository;
    private final ProductRepo productRepository;
    private final UserRepo userRepository;

    //GET: get cart items
    @GetMapping
    public List<CartItem> getCart(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        return cartItemRepository.findByUser(user);
    }

    //POST: add items to the cart
    @PostMapping("/addcart")
    public ResponseEntity<String> addToCart(
            Authentication auth,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem item = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();

        cartItemRepository.save(item);
        return ResponseEntity.ok("Product added to the cart");
    }

   // PUT:  User can update quantity
    @PutMapping("/updatecart")
    public ResponseEntity<String> updateCartItem(
            Authentication auth,
            @RequestParam Long cartItemId,
            @RequestParam int quantity
    ) {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow();
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return ResponseEntity.ok("Cart Product is updated");
    }

    //DELETE: user can delete the item from cart
    @DeleteMapping("/removecart/{id}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return ResponseEntity.ok("Product removed from the cart");
    }
}
