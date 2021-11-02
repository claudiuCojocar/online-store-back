package com.onlinestore.exceptions;

public class CategoryHasAssociatedProductsException extends RuntimeException{

    public CategoryHasAssociatedProductsException(String msg){
        super(msg);
    }

}
