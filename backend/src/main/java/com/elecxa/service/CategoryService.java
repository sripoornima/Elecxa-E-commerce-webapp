package com.elecxa.service;

import com.elecxa.model.Category;
import com.elecxa.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
    	
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    public Category update(Long id, Category updatedCategory) {
        Category existing = getById(id);
        existing.setName(updatedCategory.getName());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
    
    public Category getByName(String category) {
        return categoryRepository.findByName(category);
    }

    public long count() {
        return categoryRepository.count();
    }

    public List<Category> searchByName(String keyword) {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
