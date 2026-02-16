package com.elecxa.service;

import com.elecxa.dto.OrderDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/api/orders";

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<OrderDTO> getAllOrders(String token) {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
            BASE_URL,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<OrderDTO>>() {}
        );
        return response.getBody();
    }

    public OrderDTO getOrderById(Long orderId , String token) {
        String url = BASE_URL + "/{id}";
        
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		
        ResponseEntity<OrderDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<OrderDTO>() {},
            orderId
        );
        return response.getBody();
    }

    public List<OrderDTO> getOrdersByCustomerId(Long customerId ,String token) {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_URL + "/customer/{customerId}";
        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<OrderDTO>>() {},
            customerId
        );
        return response.getBody();
    }

    public OrderDTO placeOrder(OrderDTO orderDTO , String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<OrderDTO> entity = new HttpEntity<>(orderDTO, headers);

        ResponseEntity<OrderDTO> response = restTemplate.exchange(
        	BASE_URL + "/place",
            HttpMethod.POST,
            entity,
            OrderDTO.class
        );
        return response.getBody();
    }


    public void deleteOrder(Long orderId , String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(
            BASE_URL + "/{id}",
            HttpMethod.DELETE,
            requestEntity,
            Void.class,
            orderId
        );
    }


    public BigDecimal getTotalRevenue(String token) {
        String url = BASE_URL + "/total-revenue";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<BigDecimal> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            BigDecimal.class
        );
        return response.getBody();
    }

    public Long getTotalOrderCount(String token) {
        String url = BASE_URL + "/total-orders";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Long> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Long.class
        );
        return response.getBody();
    }

    public List<OrderDTO> getRecentOrders(String token) {
        String url = BASE_URL + "/recent";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<OrderDTO>>() {}
        );
        return response.getBody();
    }

    public List<Double> getRevenueChartData(String token) {
        String url = BASE_URL + "/revenue-data";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Double>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<Double>>() {}
        );
        return response.getBody();
    }

    public List<OrderDTO> getOrdersByStatus(String status ,  String token) {

        String url = BASE_URL + "/status/{status}";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<OrderDTO>>() {},
            status
        );
        return response.getBody();
    }

    public void updateOrderStatus(Long orderId, String status , String token) {
        String url = BASE_URL + "/{id}/status?status={status}";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Void.class,
                orderId,
                status
            );    
        }
}
