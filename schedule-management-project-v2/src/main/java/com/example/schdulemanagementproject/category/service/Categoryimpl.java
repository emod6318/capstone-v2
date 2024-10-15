package com.example.schdulemanagementproject.category.service;

import com.example.schdulemanagementproject.category.repository.CategoryRepository;
import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.category.repository.entitiy.dto.CategoryRequest;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import com.example.schdulemanagementproject.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Categoryimpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    @Override
    public List<Category> findCategory(String email) {
        return categoryRepository.findByUserEmail(email);
    }

    @Override
    public String createCategory(CategoryRequest categoryRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;
        Category category1 = Category.
                builder().
                user_id(user).
                category_color(categoryRequest.getColor()).
                category_name(categoryRequest.getName()).
                build();
        categoryRepository.save(category1);
        return "success";
    }

    @Override
    public String updateCategory(CategoryRequest category, CategoryRequest change, String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));;
        List<Category> categories = categoryRepository.findByUserEmail(email);
        // 카테고리 찾기
        Category myCategory = categories.stream()
                .filter(category1 -> Objects.equals(category1.getCategory_name(), category.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // 카테고리 정보 업데이트
        myCategory.setCategory_color(change.getColor());
        myCategory.setCategory_name(change.getName());

        // 필요한 경우 사용자 정보를 저장합니다.
        userRepository.save(user);

        return "success";
    }

    @Override
    public String deleteCategory(String email, String categoryName) {
        List<Category> categories = categoryRepository.findByUserEmail(email);
        // 카테고리 찾기
        Category myCategory = null;

        for (Category category : categories) {
            if (Objects.equals(category.getCategory_name(), categoryName)) {
                myCategory = category;
                break; // 카테고리를 찾으면 루프 종료
            }
        }

        if (myCategory == null) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.delete(myCategory);
        return "success";
    }


}
