package com.example.schdulemanagementproject.category.service;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.category.repository.entitiy.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> findCategory(String email);
    String createCategory(CategoryRequest categoryRequest, String email);

    String updateCategory(CategoryRequest category,CategoryRequest change, String email);

    String deleteCategory(String email,String categoryName);
}
