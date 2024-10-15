package com.example.schdulemanagementproject.category.controller;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.category.repository.entitiy.dto.CategoryRequest;
import com.example.schdulemanagementproject.category.service.CategoryService;
import com.example.schdulemanagementproject.schedule.repository.entity.Schedule;
import com.example.schdulemanagementproject.schedule.service.ScheduleService;
import com.example.schdulemanagementproject.schedule.service.Scheduleimpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ScheduleService scheduleService;

    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, String>>> getSchedules(HttpSession session) {
        String email = session.getAttribute("loginEmail").toString();
        List<Category> categories = categoryService.findCategory(email);
        List<Map<String,String>> Categories = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            String category_name = categories.get(i).getCategory_name();
            String category_color = categories.get(i).getCategory_color();

            Map<String, String> addCategory = new HashMap<>();
            addCategory.put("category_name", category_name);
            addCategory.put("category_color", category_color);


            Categories.add(addCategory);
        }
        return ResponseEntity.ok(Categories);
    }
    @GetMapping("/categories/count")
    public ResponseEntity<List<Map<String,String>>> getCategoriesCount(HttpSession session) {
        String email = session.getAttribute("loginEmail").toString();
        List<Map<String,String>> getCategory = getSchedules(session).getBody();

        if(getCategory!=null) {
            // Initialize "count" to 0 for each category in getCategory
            for (Map<String, String> category : getCategory) {
                category.put("count", "0");
            }
            List<Schedule> schedules = scheduleService.findSchedules(email);
            // Iterate through schedules to find matching category_name in getCategory
            for (Schedule schedule : schedules) {
                String scheduleCategoryName = schedule.getCategoryName().getCategory_name(); // Assuming getCategory_name maps to getCategoryName in Schedule class

                // Iterate through getCategory to find matching category_name
                for (Map<String, String> category : getCategory) {
                    String categoryName = category.get("category_name");

                    if (scheduleCategoryName.equals(categoryName)) {
                        // Increment count in the found category map
                        int count = Integer.parseInt(category.getOrDefault("count", "0")); // Default to 0 if "count" key is not present
                        count++;
                        category.put("count", String.valueOf(count));
                    }
                }
            }
        }
        return ResponseEntity.ok(getCategory);

    }
    @PostMapping("/categories/create")
    public ResponseEntity<String> createCategory(HttpSession session, @RequestBody CategoryRequest category) {
       String result = categoryService.createCategory(category,session.getAttribute("loginEmail").toString());

       if(Objects.equals(result, "success"))
       {
           return ResponseEntity.ok("success");
       }
       return ResponseEntity.ok("fail");
    }

    @PutMapping("/category/update")
    public ResponseEntity<String> updateCategory(HttpSession session,  @RequestBody Map<String, CategoryRequest> payload) {
        CategoryRequest category = payload.get("category");
        CategoryRequest updateCategory = payload.get("updateCategory");
        String result = categoryService.updateCategory(category,updateCategory,session.getAttribute("loginEmail").toString());

        if(Objects.equals(result, "success"))
        {
            return  ResponseEntity.ok("success");
        }
        return  ResponseEntity.ok("fail");

    }

    @DeleteMapping(value = "/category/delete/{email}/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String email,@PathVariable String name) {
        String result = categoryService.deleteCategory(email,name);
        return  ResponseEntity.ok(result);
    }
}
