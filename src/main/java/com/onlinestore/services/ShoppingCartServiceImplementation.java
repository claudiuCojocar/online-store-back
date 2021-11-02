package com.onlinestore.services;

import com.onlinestore.entities.Product;
import com.onlinestore.entities.ShoppingCart;
import com.onlinestore.entities.User;
import com.onlinestore.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImplementation implements ShoppingCartService{

    private ShoppingCartRepository shoppingCartRepository;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public ShoppingCartServiceImplementation(ShoppingCartRepository shoppingCartRepository,
                                             ProductService productService,
                                             UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public ShoppingCart addProductToCart(Long productId) {
        ShoppingCart shoppingCart = getShoppingCartOfLoggedUser();
        List<Product> productsAlreadyInCart = shoppingCart.getProductsInCart(); // luam lista exista de produse din cos
        Product product = productService.findById(productId); // identificam noul produs de adaugat in cos
        productsAlreadyInCart.add(product); // la lista veche adaugam noul produs
        shoppingCart.setProductsInCart(productsAlreadyInCart); // updatam lista de produse din shopping cart
        return shoppingCartRepository.save(shoppingCart); // salvam noua lista de produse din shopping cart in db
    }

    @Override
    public ShoppingCart removeProductFromCart(Long productId) {
        ShoppingCart shoppingCart = getShoppingCartOfLoggedUser();
        List<Product> productsAlreadyInCart = shoppingCart.getProductsInCart(); // luam lista exista de produse din cos
        Product product = productService.findById(productId);
        productsAlreadyInCart.remove(product);
        shoppingCart.setProductsInCart(productsAlreadyInCart);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart getShoppingCartOfLoggedUser() {
        User user =  userService.getLoggedUser();
        if (user.getShoppingCart() != null) {
            return user.getShoppingCart();
        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);
            userService.update(user);
        }
        return user.getShoppingCart();
    }

    @Override
    public ShoppingCart resetShoppingCart() {
        ShoppingCart shoppingCart = getShoppingCartOfLoggedUser();
        shoppingCart.setProductsInCart(new ArrayList<>());
        return shoppingCartRepository.save(shoppingCart);
    }
}
