package com.example.schdulemanagementproject.category.repository;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT s FROM Category s WHERE s.user_id.email = :email")
    List<Category> findByUserEmail(@Param("email") String email);
//
//    @Query("SELECT c FROM Category c WHERE c.category_name = :categoryName")
//    Category findByCategoryName(@Param("categoryName") String categoryName,String email);

}
