package com.elecxa.service;

import com.elecxa.model.FiltersDTO;
import com.elecxa.model.Product;
import com.elecxa.model.ProductAttribute;
import com.elecxa.repository.ProductAttributeRepository;
import com.elecxa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    ProductAttributeRepository productAttributeRepository;

    public Product create(Product product) {
    	Product prod = productRepository.save(product);
    	System.out.println(product.getAttributes());
        return prod;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product update(Long id, Product product) {
        Product existing = getById(id);
        existing.setName(product.getName());
        existing.setBrand(product.getBrand());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        if(product.getImageUrl() != "") {
        	existing.setImageUrl(product.getImageUrl());
        }

        existing.setStockQuantity(product.getStockQuantity());
        existing.setDiscount(product.getDiscount());
        existing.setWarranty(product.getWarranty());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getBySubCategory(Long subCategoryId) {
        return productRepository.findBySubcategory_SubcategoryId(subCategoryId);
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> getPopularProducts() {
        return productRepository.findProductsByFilters(null, 50.0, 5000.0, null, 4.8);
    }

    public List<Product> getByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand);
    }

    public List<Product> bulkAdd(List<Product> products) {
        return productRepository.saveAll(products);
    }

	public List<Product> getProductsByCategory(Long categoryId) {
		List<Product> products = productRepository.findBySubcategory_Category_CategoryId(categoryId);
        return products;
	}

	public List<Product> getProductByCategory(String category) {
		List<Product> products = productRepository.findBySubcategory_Category_Name(category);
        return products;
	}

	public List<Product> getProductBySubCategory(String subcategoryName) {
		List<Product> products = productRepository.findBySubcategory_Name(subcategoryName);
        return products;
	}

	public List<Product> getProductByFilters(String categoryName , FiltersDTO filters) {
		
		return productRepository.findProductsByFilters(categoryName , filters.getMin() , filters.getMax(),filters.getBrands().size() ==0 ?null : filters.getBrands() ,filters.getRating() );
	}
}
