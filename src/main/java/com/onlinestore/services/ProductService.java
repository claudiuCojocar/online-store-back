package com.onlinestore.services;

import com.onlinestore.entities.Category;
import com.onlinestore.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Product save(Product product);
    Page<Product> search(Map<String, String> params);
    Product findById(Long id);
    Product update(Product product);
    List<Product> findAllByCategory(Category category);
    void delete(Long id);

}
