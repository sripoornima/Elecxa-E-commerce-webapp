package com.elecxa.service;

import com.elecxa.model.Product;
import com.elecxa.model.User;
import com.elecxa.model.Wishlist;
import com.elecxa.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> getWishlistByUser(User user) {
        return wishlistRepository.findByUser(user);
    }

    public Optional<Wishlist> getWishlistItem(User user, Product product) {
        return wishlistRepository.findByUserAndProduct(user, product);
    }

    public boolean isProductInWishlist(User user, Product product) {
        return wishlistRepository.existsByUserAndProduct(user, product);
    }

    public Wishlist addToWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(User user, Product product) {
        wishlistRepository.deleteByUserAndProduct(user, product);
    }

    public void deleteWishlistItem(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }

    public List<Wishlist> getAllWishlistItems() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> getWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }
}
