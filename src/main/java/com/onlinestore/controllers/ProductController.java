package com.onlinestore.controllers;

import com.onlinestore.controllers.dto.ProductDto;
import com.onlinestore.entities.Category;
import com.onlinestore.entities.Product;
import com.onlinestore.services.CategoryService;
import com.onlinestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public Product save(@RequestBody ProductDto productDto) {
        Product product = ProductDto.toProduct(productDto, categoryService);
        return productService.save(product);
    }

    // http://localhost:8080/users/1 -- path variable (care este mandatory)
    // http://localhost:8080/products?name=someValue
    // http://localhost:8080/products?name=someValue&
    // http://localhost:8080/products


    @GetMapping
    public Page<Product> search(@RequestParam Map<String, String> params){
        return productService.search(params);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus delete(@PathVariable Long id){
        productService.delete(id);
        return HttpStatus.OK;
    }

    @PutMapping(path = "/{id}")
    public Product update(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {
        Product product = productService.findById(productId);
        Product updatedProduct = updateProduct(product, productDto, categoryService);
        return productService.save(updatedProduct);
    }

    private Product updateProduct(Product product, ProductDto newProduct, CategoryService categoryService) {
        product.setStock(newProduct.getStock());
        product.setPrice(newProduct.getPrice());
        product.setName(newProduct.getName());
        product.setImageURL(newProduct.getImageURL());
        product.setDescription(newProduct.getDescription());
        Category category = categoryService.findById(newProduct.getCategoryId());
        product.setCategory(category);
        return product;
    }

    @GetMapping("/categories/{categoryId}")
    public List<Product> findAllByCategory(@PathVariable("categoryId") Long categoryId){
        Category category = categoryService.findById(categoryId);
        return productService.findAllByCategory(category);
    }
}
