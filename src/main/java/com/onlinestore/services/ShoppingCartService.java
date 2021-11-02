package com.onlinestore.services;

import com.onlinestore.entities.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addProductToCart(Long productId);
    ShoppingCart removeProductFromCart(Long productId);
    ShoppingCart getShoppingCartOfLoggedUser();
    ShoppingCart resetShoppingCart();

}
