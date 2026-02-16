
package com.elecxa.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import com.elecxa.dto.CategoryDTO;
import com.elecxa.dto.SubCategoryDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    private final RestTemplate restTemplate;

    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CategoryDTO> getAllCategories(String token) {
    	String url = "http://localhost:8080/api/categories";

    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	HttpEntity<String> entity = new HttpEntity<>(headers);

    	ResponseEntity<CategoryDTO[]> response = restTemplate.exchange(
    	    url,
    	    HttpMethod.GET,
    	    entity,
    	    CategoryDTO[].class
    	);

    	List<CategoryDTO> categories = Arrays.asList(response.getBody());
    	return categories;

    }

	public CategoryDTO getCategoryByName(String categoryName, String token) {
		String url = "http://localhost:8080/api/categories/categoryName/{categoryName}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<CategoryDTO> response = restTemplate.exchange(
		    url,
		    HttpMethod.GET,
		    entity,
		    CategoryDTO.class,
		    categoryName
		);

		CategoryDTO categories = response.getBody();
		return categories;

		
	}

	public SubCategoryDTO getCategoryBySubCategory(Integer id, String token) {
		String url = "http://localhost:8080/api/subcategories/category/categoryId/{id}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<SubCategoryDTO> response = restTemplate.exchange(
		    url,
		    HttpMethod.GET,
		    entity,
		    SubCategoryDTO.class,
		    id
		);

		SubCategoryDTO categories = response.getBody();
		return categories;

		
	}
}
