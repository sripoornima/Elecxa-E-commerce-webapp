package com.elecxa.controller;

import com.elecxa.model.Product;
import com.elecxa.model.User;
import com.elecxa.model.Wishlist;
import com.elecxa.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin("*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/user/{userId}")
    public List<Wishlist> getWishlistByUser(@PathVariable("userId") User user) {
        return wishlistService.getWishlistByUser(user);
    }

    @GetMapping("/{id}")
    public Optional<Wishlist> getWishlistItemById(@PathVariable Long id) {
        return wishlistService.getWishlistById(id);
    }

    @PostMapping
    public Wishlist addToWishlist(@RequestBody Wishlist wishlist) {
        return wishlistService.addToWishlist(wishlist);
    }

    @DeleteMapping("/user/{userId}/product/{productId}")
    public void removeFromWishlist(@PathVariable("userId") User user,
                                   @PathVariable("productId") Product product) {
        wishlistService.removeFromWishlist(user, product);
    }

    @DeleteMapping("/{wishlistId}")
    public void deleteWishlistItem(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistItem(wishlistId);
    }

    @GetMapping("/all")
    public List<Wishlist> getAllWishlistItems() {
        return wishlistService.getAllWishlistItems();
    }

    @GetMapping("/exists/user/{userId}/product/{productId}")
    public boolean checkProductInWishlist(@PathVariable("userId") User user,
                                          @PathVariable("productId") Product product) {
        return wishlistService.isProductInWishlist(user, product);
    }
}
