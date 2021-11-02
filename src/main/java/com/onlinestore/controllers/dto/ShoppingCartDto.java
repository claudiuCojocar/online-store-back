package com.onlinestore.controllers.dto;

import com.onlinestore.entities.Product;
import com.onlinestore.entities.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartDto {

    private List<ProductDto> productDtos;

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }

    public static ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        List<ProductDto> productDtoList =
                shoppingCart
                        .getProductsInCart()
                        .stream()
                        .map(productInCart -> ProductDto.toProductDto(productInCart))
                        .collect(Collectors.toList());
        shoppingCartDto.setProductDtos(productDtoList);
        return shoppingCartDto;
    }
}
