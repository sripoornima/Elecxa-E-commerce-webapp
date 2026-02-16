package com.elecxa.service;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.elecxa.dto.ProductAttributeDTO;
import com.elecxa.dto.ProductDTO;
import com.elecxa.dto.UserDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDTO> getPopularProducts(String token) {
    	String url = "http://localhost:8080/api/products";

    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

    	ResponseEntity<ProductDTO[]> response = restTemplate.exchange(
    	    url,
    	    HttpMethod.GET,
    	    entity,
    	    ProductDTO[].class
    	);

    	List<ProductDTO> products = Arrays.asList(response.getBody());
    	return products;

    }

    public List<ProductDTO> getProductsByCategory(String category , int id, String token) {
        String url = "http://localhost:8080/api/products/category/{id}";
        HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ProductDTO>>() {},
            id
        );

        return response.getBody();
    }

	public List<ProductDTO> getProductsBysubCategory(String subcategory, Integer id , String token) {
		String url = "http://localhost:8080/api/products/subcategory/{id}";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ProductDTO>>() {},
            id
        );

        return response.getBody();
	}
	
	 public ProductDTO getProductsById(long id, String token) {
	        String url = "http://localhost:8080/api/products/{id}";
	        HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setBearerAuth(token);
	    	
	    	
	    	HttpEntity<String> entity = new HttpEntity<>(headers);

	        ResponseEntity<ProductDTO> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            new ParameterizedTypeReference<ProductDTO>() {},
	            id
	        );

	        return response.getBody();
	    }

	public List<ProductAttributeDTO> getProductAttributes(Long id, String token) {
		String url = "http://localhost:8080/api/attributes/product/{productId}";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ProductAttributeDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ProductAttributeDTO>>() {},
            id
        );

        return response.getBody();
	}
	   public Integer getTotalProductCount(String token) {
	        String url = "http://localhost:8080/api/products/count";  
	        
	        HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setBearerAuth(token);
	    	
	    	
	    	HttpEntity<String> entity = new HttpEntity<>(headers);
	    	
	        ResponseEntity<Integer> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            Integer.class
	        );
	        return response.getBody();
	    }

	public List<ProductDTO> getAllProducts(String token) {
		String url = "http://localhost:8080/api/products";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity< List<ProductDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference< List<ProductDTO>>() {}
           
        );

        return response.getBody();
	}
	
	 public List<ProductDTO> getProductByCategory(String category, String token) {
	        String url = "http://localhost:8080/api/products/category/categoryName/{category}";
	        HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setBearerAuth(token);
	    	
	    	
	    	HttpEntity<String> entity = new HttpEntity<>(headers);

	        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            new ParameterizedTypeReference<List<ProductDTO>>() {},
	            category
	        );

	        return response.getBody();
	    }

	public List<ProductDTO> getProductsBySubCategory(String subcategoryName, String token) {
		String url = "http://localhost:8080/api/products/subcategory/subcategoryName/{subcategoryName}";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ProductDTO>>() {},
            subcategoryName
        );

        return response.getBody();
	}

	public ProductDTO addProduct(ProductDTO product, String token) {
		System.out.println(product);
		String url = "http://localhost:8080/api/products/add";

		  HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);
	        HttpEntity<ProductDTO> entity = new HttpEntity<>(product, headers);

	    ResponseEntity<ProductDTO> response = restTemplate.exchange(
		        url,
		        HttpMethod.POST,
		        entity,
		        ProductDTO.class
		);
	    return response.getBody(); 
	}

	public void addProductAttributes(Long productId, List<ProductAttributeDTO> attribute, String token) {
		String url = "http://localhost:8080/api/products/add/attributes/{productId}";

		  HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	        HttpEntity<List<ProductAttributeDTO>> entity = new HttpEntity<>(attribute, headers);

	    ResponseEntity<List<ProductAttributeDTO>> response = restTemplate.exchange(
		        url,
		        HttpMethod.POST,
		        entity,
	            new ParameterizedTypeReference<List<ProductAttributeDTO>>() {},
		        productId
		);
	}

	public void deleteProduct(long productId, String token) {
		String url = "http://localhost:8080/api/products/delete/{productId}";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

	    ResponseEntity<String> response = restTemplate.exchange(
		        url,
		        HttpMethod.DELETE,
		        entity,
		        String.class,
		        productId
		);
	}

	public ProductDTO updateProduct(ProductDTO product , long productId, String token) {
		System.out.println(product);
		String url = "http://localhost:8080/api/products/update/{productId}";

		  HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	        HttpEntity<ProductDTO> entity = new HttpEntity<>(product, headers);

	    ResponseEntity<ProductDTO> response = restTemplate.exchange(
		        url,
		        HttpMethod.POST,
		        entity,
		        ProductDTO.class,
		        productId 
		);
	    return response.getBody();
	}

	public void updateProductAttributes(Long productId, List<ProductAttributeDTO> attribute, String token) {
		String url = "http://localhost:8080/api/attributes/update/attributes/{productId}";
		  HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	        HttpEntity<List<ProductAttributeDTO>> entity = new HttpEntity<>(attribute, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
		        url,
		        HttpMethod.POST,
		        entity,
		        String.class,
		        productId
		);
	}

	public List<ProductDTO> getTopSellingProducts(String token) {
		String url = "http://localhost:8080/api/products/popularproducts";
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	
        ResponseEntity< List<ProductDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference< List<ProductDTO>>() {}
           
        );
        return response.getBody();
	}


}