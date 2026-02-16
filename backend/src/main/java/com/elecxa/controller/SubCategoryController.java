package com.elecxa.controller;

import com.elecxa.model.Category;
import com.elecxa.model.SubCategory;
import com.elecxa.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories") // ✅ Just "api" base
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    // 1. Create a subcategory
    @PostMapping
    public ResponseEntity<SubCategory> create(@RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(subCategoryService.create(subCategory));
    }

    // 2. Get all subcategories
    @GetMapping
    public ResponseEntity<List<SubCategory>> getAll() {
        return ResponseEntity.ok(subCategoryService.getAll());
    }

    // 3. Get subcategory by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.getById(id));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SubCategory>> getById(@PathVariable String category) {
        return ResponseEntity.ok(subCategoryService.getSubCategoryByCategory(category));
    }
    
    @GetMapping("/category/categoryId/{id}")
    public ResponseEntity<SubCategory> getById(@PathVariable long id) {
        return ResponseEntity.ok(subCategoryService.getCategoryBySubcategoryId(id));
    }
    
    @GetMapping("/subcategory/{subcategoryName}")
    public ResponseEntity<SubCategory> getBySubcategoryName(@PathVariable String subcategoryName) {
        return ResponseEntity.ok(subCategoryService.searchByName(subcategoryName));
    }

    // 4. Update subcategory
    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> update(@PathVariable Long id, @RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(subCategoryService.update(id, subCategory));
    }

    // 5. Delete subcategory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Check if subcategory exists
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.existsById(id));
    }

    // 7. Count all subcategories
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(subCategoryService.count());
    }

    // 8. Search subcategories by name
    @GetMapping("/search")
    public ResponseEntity<SubCategory> searchByName(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(subCategoryService.searchByName(keyword));
    }

 

    // 10. Add multiple subcategories at once
    @PostMapping("/bulk")
    public ResponseEntity<List<SubCategory>> bulkAdd(@RequestBody List<SubCategory> subCategories) {
        return ResponseEntity.ok(subCategoryService.bulkAdd(subCategories));
    }
}
