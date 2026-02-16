package com.elecxa.controller;

import com.elecxa.model.FiltersDTO;
import com.elecxa.model.Product;
import com.elecxa.model.ProductAttribute;
import com.elecxa.service.ProductAttributeService;
import com.elecxa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    ProductAttributeService productAttributeService;

    // 1. Create product
    @PostMapping("/add")
    public ResponseEntity<Product> create(@RequestBody Product product) {
       return ResponseEntity.ok(productService.create(product));
    }
    
    @PostMapping("/add/attributes/{productId}")
    public ResponseEntity<List<ProductAttribute>> createAttributes(@PathVariable long productId , @RequestBody List<ProductAttribute> productAttribute) {
       return ResponseEntity.ok(productAttributeService.createAttributes(productId , productAttribute));
    }

    // 2. Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    // 3. Get product by ID
    @GetMapping("/info/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getProductCount() {
        return ResponseEntity.ok(productService.getAll().size());
    }

    // 4. Update product
    @PostMapping("/update/{productId}")
    public ResponseEntity<Product> update(@PathVariable Long productId, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(productId, product));
    }

    // 5. Delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Get products by subcategory ID
    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<List<Product>> getBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(productService.getBySubCategory(subCategoryId));
    }

    // 7. Search product by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchByName(keyword));
    }

    
    @GetMapping("/popularproducts")
    public ResponseEntity<List<Product>> getPopularProducts() {
        return ResponseEntity.ok(productService.getPopularProducts());
    }

    // 9. Get products by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(productService.getByBrand(brand));
    }

    // 10. Bulk add products
    @PostMapping("/bulk")
    public ResponseEntity<List<Product>> bulkAdd(@RequestBody List<Product> products) {
        return ResponseEntity.ok(productService.bulkAdd(products));
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/category/categoryName/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/subcategory/subcategoryName/{subcategoryName}")
    public ResponseEntity<List<Product>> getProductBySubCategory(@PathVariable String subcategoryName) {
        List<Product> products = productService.getProductBySubCategory(subcategoryName);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping("/filter/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductByFilters(@PathVariable String categoryName , @RequestBody FiltersDTO filters) {
    	System.out.println(filters);
        List<Product> products = productService.getProductByFilters(categoryName , filters);
        return ResponseEntity.ok(products);
    }
}
