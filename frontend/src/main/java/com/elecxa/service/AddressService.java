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

import com.elecxa.dto.AddressDTO;

@Service
public class AddressService {

	private final RestTemplate restTemplate;

	public AddressService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public AddressDTO getUserAddress(long id, String token) {
		String url = "http://localhost:8080/api/addresses/user/{id}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<AddressDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, AddressDTO.class, id);
		
		return response.getBody();
	}

	public void updateUserAddress(AddressDTO userAddress, long id, String token) {
		String BACKEND_URL = "http://localhost:8080/api/addresses";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);

		HttpEntity<AddressDTO> entity = new HttpEntity<>(userAddress, headers);

		restTemplate.exchange(BACKEND_URL + "/update/{id}", HttpMethod.PUT, entity, Void.class, id);
	}
}
