package com.elecxa.controller;

import com.elecxa.model.ProductAttribute;
import com.elecxa.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/attributes")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    // 1. Create a product attribute
    @PostMapping
    public ResponseEntity<ProductAttribute> create(@RequestBody ProductAttribute attribute) {
        return ResponseEntity.ok(productAttributeService.create(attribute));
    }

    // 2. Get all attributes
    @GetMapping
    public ResponseEntity<List<ProductAttribute>> getAll() {
        return ResponseEntity.ok(productAttributeService.getAll());
    }

    // 3. Get attribute by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductAttribute> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productAttributeService.getById(id));
    }

    // 4. Update attribute
    @PostMapping("/update/attributes/{productId}")
    public ResponseEntity<String> update(@PathVariable Long productId, @RequestBody List<ProductAttribute> attribute) {
    	productAttributeService.update(productId, attribute);
        return ResponseEntity.ok("Successfully Updated");
    }

    // 5. Delete attribute
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productAttributeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Get all attributes by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductAttribute>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productAttributeService.getByProductId(productId));
    }

    // 7. Search attribute by name
    @GetMapping("/search/name")
    public ResponseEntity<List<ProductAttribute>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productAttributeService.searchByName(name));
    }

    // 8. Search attribute by value
    @GetMapping("/search/value")
    public ResponseEntity<List<ProductAttribute>> searchByValue(@RequestParam String value) {
        return ResponseEntity.ok(productAttributeService.searchByValue(value));
    }

    // 9. Bulk add attributes
    @PostMapping("/bulk")
    public ResponseEntity<List<ProductAttribute>> bulkAdd(@RequestBody List<ProductAttribute> attributes) {
        return ResponseEntity.ok(productAttributeService.bulkAdd(attributes));
    }

    // 10. Count total attributes
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(productAttributeService.count());
    }
}
