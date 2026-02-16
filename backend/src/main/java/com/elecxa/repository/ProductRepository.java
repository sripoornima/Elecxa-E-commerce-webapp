package com.elecxa.repository;

import com.elecxa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findBySubcategory_SubcategoryId(Long subcategoryId);
    List<Product> findByStockQuantityLessThan(Integer threshold);
    List<Product> findByBrandIgnoreCase(String brand);
	List<Product> findBySubcategory_Category_CategoryId(Long categoryId);
	List<Product> findBySubcategory_Category_Name(String category);
	List<Product> findBySubcategory_Name(String subcategoryName);
	
	@Query("SELECT p FROM Product p " +
		       "WHERE (:categoryName IS NULL OR p.subcategory.category.name = :categoryName) " +
		       "AND (:minValue IS NULL OR (p.price-((p.price * p.discount)/100)) >= :minValue) " +
		       "AND (:maxValue IS NULL OR (p.price-((p.price * p.discount)/100)) <= :maxValue) " +
		       "AND (:brand IS NULL OR p.brand IN (:brand)) " +
		       "AND (:rating IS NULL OR p.rating >= :rating)")
		List<Product> findProductsByFilters(
		    @Param("categoryName") String categoryName,
		    @Param("minValue") Double minValue,
		    @Param("maxValue") Double maxValue,
		    @Param("brand") List<String> brand,
		    @Param("rating") Double rating
		);

	}
