package com.elecxa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elecxa.model.RevokedToken;
import com.elecxa.model.Role;
import com.elecxa.model.RoleType;
import com.elecxa.model.User;
import com.elecxa.repository.RevokedTokenRepository;
import com.elecxa.repository.UserRepository;
import com.elecxa.security.JwtUtil;
import com.elecxa.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin("*")
public class AuthController {

	private JwtUtil jwtUtil;
	private UserRepository userRepository;
	private RevokedTokenRepository revokedTokenRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRevokedTokenRepository(RevokedTokenRepository revokedTokenRepository) {
		this.revokedTokenRepository = revokedTokenRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	UserService userService;

	@GetMapping("/auth/login/{credential}")
	public ResponseEntity<?> isUserExists(@PathVariable String credential) {
		try {

			User user = userService.getUserByEmail(credential);
			return ResponseEntity
					.ok(Map.of("accessToken", jwtUtil.generateToken(credential, user.getRole().getRoleName().name()),
							"refreshToken", jwtUtil.generateRefreshToken(credential)));
		} catch (EntityNotFoundException exByEmail) {
			try {

				User user = userService.getUserByPhoneNumber(credential);
				return ResponseEntity.ok(Map.of(
	                    "accessToken", jwtUtil.generateToken(credential, user.getRole().getRoleName().name()),
	                    "refreshToken", jwtUtil.generateRefreshToken(credential)
	            )); 

			} catch (EntityNotFoundException exByPhone) {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("User not found with provided email or phone number: " + credential);
			}
		}
	}

	@PostMapping("/auth/createuser")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		Role role = new Role();
		role.setRoleId(2L);
		role.setRoleName(RoleType.CUSTOMER);
		user.setRole(role);

		String encodedPassword = passwordEncoder.encode(user.getPassword()); // ✅ Hashing here
		user.setPassword(encodedPassword);
		return ResponseEntity.ok(userService.createUser(user));
	}

	@GetMapping("/auth/password/{credential}/{passwordInput}")
	public ResponseEntity<?> isValidPassword(@PathVariable String credential, @PathVariable String passwordInput) {
		try {

			User user = userService.getUserByEmail(credential);

			if (passwordEncoder.matches(passwordInput, user.getPassword())) {
				return ResponseEntity.ok(Map.of(
	                    "accessToken", jwtUtil.generateToken(credential, user.getRole().getRoleName().name()),
	                    "refreshToken", jwtUtil.generateRefreshToken(credential)
	            )); 
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password doesn't match");
			}
		} catch (EntityNotFoundException exByEmail) {
			User user = userService.getUserByPhoneNumber(credential);
			System.out.println(user.getPassword());
			if (passwordEncoder.matches(passwordInput, user.getPassword())) {
				return ResponseEntity.ok(Map.of(
	                    "accessToken", jwtUtil.generateToken(credential, user.getRole().getRoleName().name()),
	                    "refreshToken", jwtUtil.generateRefreshToken(credential)
	            )); 
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password doesn't match");
			}

		}
	}
	
	
    @PostMapping("auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token,
                                         @RequestBody Map<String, String> request) {
        token = token.substring(7);
        
        revokedTokenRepository.save(new RevokedToken(token));

        String refreshToken = request.get("refreshToken");
        if (refreshToken != null) {
            revokedTokenRepository.save(new RevokedToken(refreshToken));
        }
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }


}
