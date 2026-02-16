package com.elecxa.security;

import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Apply CORS Config
        .authorizeHttpRequests(auth -> auth
        		.requestMatchers("/api/user/phone/**").permitAll()
        		.requestMatchers("/api/user/email/**").permitAll()
        		.requestMatchers("/api/cart/**").permitAll()

        		.requestMatchers("/auth/login/**").permitAll()
        		.requestMatchers("/auth/createuser").permitAll()
        		.requestMatchers("/auth/password/**").permitAll()
        		.requestMatchers("/auth/logout").permitAll()
        		.requestMatchers("/api/cart/**").hasAuthority("CUSTOMER")
        		.requestMatchers("/api/addresses/**").hasAuthority("CUSTOMER")
        		.requestMatchers("/api/categories/**").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.requestMatchers("/api/orders/**").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.requestMatchers("/api/customer/otp/send").permitAll()
        		.requestMatchers("/customer/otp/verify").permitAll()
        		.requestMatchers("api/payments/**").hasAuthority("CUSTOMER")
        		.requestMatchers("/api/attributes/**").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.requestMatchers("/api/products/**").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.requestMatchers("/api/subcategories/**").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.requestMatchers("/api/user/**").hasAuthority("CUSTOMER")
        		.requestMatchers("/api/wishlist/**").hasAuthority("CUSTOMER")
        		.requestMatchers("/api/categories").hasAnyAuthority("CUSTOMER" , "ADMIN")
        		.anyRequest().permitAll()

        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8083")); // Allow frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH" ,"OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
