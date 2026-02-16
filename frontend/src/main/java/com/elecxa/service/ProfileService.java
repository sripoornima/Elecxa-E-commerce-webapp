package com.elecxa.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.elecxa.dto.UserDTO;

@Service
public class ProfileService {

    private final RestTemplate restTemplate;

    // Replace with your backend base URL

    @Autowired
    public ProfileService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public UserDTO getUserProfile(long id, String token) {
        String BACKEND_URL = "http://localhost:8080/api/user/{id}";
        HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                BACKEND_URL,
                HttpMethod.GET,
                entity,
                UserDTO.class,
                id
        );
        return response.getBody();
    }

    public void updateUserProfile(UserDTO userProfile , long id, String token) {
        String BACKEND_URL = "http://localhost:8080/api/user";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UserDTO> entity = new HttpEntity<>(userProfile, headers);

        restTemplate.exchange(
                BACKEND_URL + "/update/{id}",
                HttpMethod.PUT,
                entity,
                Void.class,
                id
        );
    }
}
