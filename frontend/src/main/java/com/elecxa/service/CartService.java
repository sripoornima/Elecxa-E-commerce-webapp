package com.elecxa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import com.elecxa.dto.CartDTO;
import com.elecxa.dto.CartItemDTO;

@Service
public class CartService {

    private static final String BASE_URL = "http://localhost:8080/api/cart/"; 
    
    @Autowired
    private RestTemplate restTemplate;

    // Get the current cart
    public CartDTO getCart(long id, String token) {
        // Make GET request to fetch cart details
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<CartDTO> entity = new HttpEntity<>(headers);

        ResponseEntity<CartDTO> response = restTemplate.exchange(
                BASE_URL + "{id}", // Endpoint to fetch cart data
                HttpMethod.GET,
                entity,
                CartDTO.class,
                id
        );
        return response.getBody();
    }

    // Add item to the cart
    public void addItemToCart(CartItemDTO cartItem , String token) {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);

        HttpEntity<CartItemDTO> entity = new HttpEntity<>(cartItem, headers);
        restTemplate.exchange(
                BASE_URL + "/add", // Endpoint to add item to cart
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    // Update the quantity of an item in the cart
    public void updateItemQuantity(Long itemId, String action, String token) {
        // Prepare the request body and URL
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/update")
                .queryParam("itemId", itemId)
                .queryParam("action", action)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make PUT request to update item quantity
        restTemplate.exchange(
                url, // Endpoint to update item quantity
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

    // Remove an item from the cart
    public void removeItem(Long itemId, String token) {
        // Prepare the request URL
        String url = BASE_URL + "/remove/{itemId}";
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make DELETE request to remove item from cart
        restTemplate.exchange(
                url, // Endpoint to remove item from cart
                HttpMethod.DELETE,
                entity,
                Void.class,
                itemId
        );
    }

	public List<CartItemDTO> getCartItems(Long cartId, String token) {
		
	    String url = "http://localhost:8080/api/cart/cartitem/";
	    
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

	    // Use RestTemplate.exchange() with the appropriate type
	    ResponseEntity<List<CartItemDTO>> response = restTemplate.exchange(
	            url + "{cartId}",  
	            HttpMethod.GET,
	            entity,                   
	            new ParameterizedTypeReference<List<CartItemDTO>>() {},          
	            cartId                 
	    );
	    
	    return response.getBody(); 
	}

	public void updateCart(Long productId, Long userId, String token) {
		
        String url = BASE_URL + "/add/{productId}/{userId}" ;
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

	        restTemplate.exchange(
	                url,
	                HttpMethod.POST,
	                entity,
	                Void.class,
	                productId,
	                userId
	        );
	}
}
