package com.elecxa.repository;

import com.elecxa.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByNameContainingIgnoreCase(String keyword);
    List<SubCategory> findByCategory_CategoryId(Long categoryId);
	List<SubCategory> findByCategory_Name(String category);
	Optional<SubCategory> findByName(String keyword);
}
