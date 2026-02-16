package com.elecxa.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.elecxa.dto.CategoryDTO;
import com.elecxa.dto.SubCategoryDTO;

@Service
public class SubCategoryService {
	 private final RestTemplate restTemplate;

	    public SubCategoryService(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    public List<SubCategoryDTO> getAllSubCategories(String token) {
	    	String url = "http://localhost:8080/api/subcategories";

	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	    	HttpEntity<String> entity = new HttpEntity<>(headers);

	    	ResponseEntity<SubCategoryDTO[]> response = restTemplate.exchange(
	    	    url,
	    	    HttpMethod.GET,
	    	    entity,
	    	    SubCategoryDTO[].class
	    	);

	    	List<SubCategoryDTO> subcategories = Arrays.asList(response.getBody());
	    	return subcategories;
	    }
	    
	    public List<SubCategoryDTO> getSubCategoriesByCategory(String category, String token) {
	    	String url = "http://localhost:8080/api/subcategories/category/{category}";

	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	    	HttpEntity<String> entity = new HttpEntity<>(headers);

	    	ResponseEntity<SubCategoryDTO[]> response = restTemplate.exchange(
	    	    url,
	    	    HttpMethod.GET,
	    	    entity,
	    	    SubCategoryDTO[].class,
	    	    category
	    	);

	    	List<SubCategoryDTO> subcategories = Arrays.asList(response.getBody());
	    	return subcategories;

	    }

		public SubCategoryDTO getSubCategoryByName(String subcategoryName, String token) {
			String url = "http://localhost:8080/api/subcategories/subcategory/{subcategoryName}";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<SubCategoryDTO> response = restTemplate.exchange(
			    url,
			    HttpMethod.GET,
			    entity,
			    SubCategoryDTO.class,
			    subcategoryName
			);

			SubCategoryDTO subcategories = response.getBody();
			return subcategories;

		}
}
