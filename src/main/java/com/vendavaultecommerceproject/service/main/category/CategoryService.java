package com.vendavaultecommerceproject.service.main.category;

import com.vendavaultecommerceproject.dto.category.CategoryDto;
import com.vendavaultecommerceproject.entities.category.CategoryEntity;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryDto categoryDto);
    List<CategoryEntity> getAllCategory();
    CategoryEntity getByName(CategoryDto categoryDto);
}
