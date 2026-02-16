package com.elecxa.repository;

import com.elecxa.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> findByProduct_ProductId(Long productId);
    List<ProductAttribute> findByAttributeNameContainingIgnoreCase(String attributeName);
    List<ProductAttribute> findByAttributeValueContainingIgnoreCase(String attributeValue);
}
