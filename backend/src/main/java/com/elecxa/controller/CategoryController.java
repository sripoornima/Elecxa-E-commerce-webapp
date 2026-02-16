package com.elecxa.controller;

import com.elecxa.model.Category;
import com.elecxa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/categories") 
public class CategoryController {

    @Autowired 
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
    	return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }
    
    @GetMapping("categoryName/{categoryName}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryService.getByName(categoryName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    // 5. Delete category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.existsById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(categoryService.count());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(categoryService.searchByName(keyword));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Category>> bulkAdd(@RequestBody List<Category> categories) {
        return ResponseEntity.ok(categories.stream().map(categoryService::create).toList());
    }

    @GetMapping("/limit/{count}")
    public ResponseEntity<List<Category>> getFirstN(@PathVariable int count) {
        List<Category> all = categoryService.getAll();
        return ResponseEntity.ok(all.stream().limit(count).toList());
    }
}
