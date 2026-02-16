package com.elecxa.service;

import com.elecxa.dto.UserDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String BASE_URL = "http://localhost:8080/api/user";

    public List<UserDTO> getAllUsers(String token) {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
            BASE_URL,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<UserDTO>>() {}
        );
        return response.getBody();
    }

    public UserDTO getUserById(Long id , String token) {
        String url = BASE_URL + "/{id}";
        ResponseEntity<UserDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<UserDTO>() {},
            id
        );
        return response.getBody();
    }

    public UserDTO createUser(UserDTO userDTO) {
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(
            BASE_URL,
            userDTO,
            UserDTO.class
        );
        return response.getBody();
    }

    public UserDTO updateUser(Long id, UserDTO userDTO , String token) {
        String url = BASE_URL + "/update/{id}";
        
        HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	

        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO , headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<UserDTO>() {},
            id
        );
        return response.getBody();
    }

    public void deleteUser(Long id) {
        String url = BASE_URL + "/{id}";
        restTemplate.delete(url, id);
    }

    public UserDTO getUserByEmail(String email) {
        String url = BASE_URL + "/email/{email}";
        ResponseEntity<UserDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<UserDTO>() {},
            email
        );
        return response.getBody();
    }

    public UserDTO getUserByPhone(String phone) {
        String url = BASE_URL + "/phone/{phone}";
        ResponseEntity<UserDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<UserDTO>() {},
            phone
        );
        return response.getBody();
    }

    public UserDTO changeUserRole(Long userId, Long roleId) {
        String url = BASE_URL + "/{id}/role/{roleId}";
        ResponseEntity<UserDTO> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            null,
            new ParameterizedTypeReference<UserDTO>() {},
            userId,
            roleId
        );
        return response.getBody();
    }

    public List<UserDTO> getUsersByRole(String roleName , String token) {
        String url = BASE_URL + "/role/{roleName}";
        HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	
    	
    	HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<UserDTO>>() {},
            roleName
        );
        return response.getBody();
    }

    public List<UserDTO> getUsersCreatedAfter(String dateTime) {
        String url = BASE_URL + "/created-after?dateTime={dateTime}";
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<UserDTO>>() {},
            dateTime
        );
        return response.getBody();
    }
    public Integer getTotalCustomerCount(String token) {
        String url = BASE_URL + "/count";  
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
}
