package com.vendavaultecommerceproject.service.impl.category;


import com.vendavaultecommerceproject.dto.category.CategoryDto;
import com.vendavaultecommerceproject.entities.category.CategoryEntity;
import com.vendavaultecommerceproject.repository.category.CategoryRepository;
import com.vendavaultecommerceproject.service.main.category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryDto.getName())
                .build();
        categoryRepository.save(categoryEntity);
        return "Category Added Successfully";
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getByName(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryRepository.findByName(categoryDto.getName());
        if (Objects.nonNull(categoryEntity)){
            return categoryEntity;
        }
        return null;
    }
}
