package com.elecxa.service;

import com.elecxa.model.Product;
import com.elecxa.model.ProductAttribute;
import com.elecxa.repository.ProductAttributeRepository;
import com.elecxa.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAttributeService {

	@Autowired
	private ProductAttributeRepository repository;

	@Autowired
	private ProductRepository productRepository;

	public ProductAttribute create(ProductAttribute attribute) {
		return repository.save(attribute);
	}

	public List<ProductAttribute> getAll() {
		return repository.findAll();
	}

	public ProductAttribute getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Attribute not found"));
	}

	public List<ProductAttribute> update(Long id, List<ProductAttribute> attribute) {
		List<ProductAttribute> existing = repository.findByProduct_ProductId(id);
		Product product = productRepository.findById(id).get();
		List<ProductAttribute> newAttributes = new ArrayList<>();

		System.out.println("bcb cc c"+existing);
		int i=0;
		for(i=0;i<attribute.size();i++) {
			if(i<existing.size()) {
				existing.get(i).setAttributeName(attribute.get(i).getAttributeName());
				existing.get(i).setAttributeValue(attribute.get(i).getAttributeValue());
				
			}
			else {
				ProductAttribute obj = new ProductAttribute();
				obj.setAttributeName(attribute.get(i).getAttributeName());
				obj.setAttributeValue(attribute.get(i).getAttributeValue());
				obj.setProduct(product);
				newAttributes.add(obj);
			}
		}
		while(i<existing.size()) {
			repository.deleteById(existing.get(i).getAttributeId());
			i++;
		}
		
		repository.saveAll(existing);
		return repository.saveAll(newAttributes);
		
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public List<ProductAttribute> getByProductId(Long productId) {
		return repository.findByProduct_ProductId(productId);
	}

	public List<ProductAttribute> searchByName(String name) {
		return repository.findByAttributeNameContainingIgnoreCase(name);
	}

	public List<ProductAttribute> searchByValue(String value) {
		return repository.findByAttributeValueContainingIgnoreCase(value);
	}

	public List<ProductAttribute> bulkAdd(List<ProductAttribute> attributes) {
		return repository.saveAll(attributes);
	}

	public Long count() {
		return repository.count();
	}

	public List<ProductAttribute> createAttributes(long prodId, List<ProductAttribute> productAttribute) {
		Product product = productRepository.findById(prodId).get();
		for (ProductAttribute prod : productAttribute) {
			prod.setProduct(product);
		}
		return repository.saveAll(productAttribute);
	}
}
