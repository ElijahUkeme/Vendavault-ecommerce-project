package com.vendavaultecommerceproject.cotroller.category;


import com.vendavaultecommerceproject.dto.category.CategoryDto;
import com.vendavaultecommerceproject.entities.category.CategoryEntity;
import com.vendavaultecommerceproject.service.main.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/category/create")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/category/all/show")
    public ResponseEntity<List<CategoryEntity>> getAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }
        @PostMapping("/category/get/by/name")
        public ResponseEntity<CategoryEntity> getByName(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.getByName(categoryDto),HttpStatus.OK);
        }
}
