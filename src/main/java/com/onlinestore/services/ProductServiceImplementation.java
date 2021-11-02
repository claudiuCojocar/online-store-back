package com.onlinestore.services;

import com.onlinestore.entities.Category;
import com.onlinestore.entities.Product;
import com.onlinestore.exceptions.ResourceMissingInDatabase;
import com.onlinestore.repositories.CategoryRepository;
import com.onlinestore.repositories.ProductRepository;
import com.onlinestore.repositories.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> search(Map<String, String> params) {
        Specification<Product> specification = new ProductSpecification();
        if (params.containsKey("name")) { // daca parametrul de nume exista, facem cautarea si dupa el.
            specification = specification.and(ProductSpecification.withNameLike(params.get("name")));
        }
        if (params.containsKey("maxPrice")){
            Double price = Double.valueOf(params.get("maxPrice"));
            specification = specification.and(ProductSpecification.withMaxPrice(price));
        }
        if (params.containsKey("minPrice")){
            Double price = Double.valueOf(params.get("minPrice"));
            specification = specification.and(ProductSpecification.withMinPrice(price));
        }
        if (params.containsKey("categoryId")) {
            Long catID = Long.valueOf(params.get("categoryId"));
            Category category = categoryRepository.findById(catID).get();
            specification = specification.and(ProductSpecification.withCategory(category));
        }
        // &sortByPrice=asc
        Sort sort = Sort.unsorted();
        if (params.containsKey("sortByPrice")) {
            String direction = params.get("sortByPrice");
            if ("asc".equals(direction)) {
                sort = Sort.by("price").ascending();
            } else {
                sort = Sort.by("price").descending();
            }
        }
        Integer page = Integer.valueOf(params.get("pageNumber")); // 0
        Integer pageSize = Integer.valueOf(params.get("pageSize")); // 10
        return productRepository.findAll(specification, PageRequest.of(page, pageSize,sort));
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ResourceMissingInDatabase(String.format("Product with id = %d not found", id));
        }
        return productOptional.get();
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
