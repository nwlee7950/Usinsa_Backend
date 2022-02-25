package com.spring.usinsa.service;

import com.spring.usinsa.model.product.Category;

import java.util.List;

public interface CategoryService {
    Category save(String title);
    Category findById(long id);
    List<Category> findAll();
}
