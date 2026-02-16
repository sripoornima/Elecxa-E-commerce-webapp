package com.elecxa.repository;

import com.elecxa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

	Category findByName(String category);
}
