package com.onlinestore.repositories;

import com.onlinestore.entities.Category;
import com.onlinestore.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    public static Specification<Product> withNameLike(String s){
        // root este tipul obiectului nostru, in cazul de fata "Product"
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + s + "%");
    }

    public static Specification<Product> withMaxPrice(Double price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lt(root.get("price"), price);
    }

    public static Specification<Product> withMinPrice(Double price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.gt(root.get("price"), price);
    }

    public static Specification<Product> withCategory(Category category){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Product> withCategoryId(Long id){
        return (root, query, criterBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criterBuilder.equal(categoryJoin.get("id"), id);
        };
    }

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
