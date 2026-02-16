package com.elecxa.service;

import com.elecxa.model.Address;
import com.elecxa.model.Cart;
import com.elecxa.model.Role;
import com.elecxa.model.User;
import com.elecxa.repository.AddressRepository;
import com.elecxa.repository.CartRepository;
import com.elecxa.repository.RoleRepository;
import com.elecxa.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;



    public UserService(UserRepository userRepository, RoleRepository roleRepository , AddressRepository addressRepository ,CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.cartRepository  = cartRepository;
    }

    public User createUser(User user) {
    	User users = userRepository.save(user);
    	Address address = new Address();
    	address.setUser(users);
    	addressRepository.save(address);
    	Cart cart = new Cart();
    	cart.setUser(users);
    	cartRepository.save(cart);
    	
        return users;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User not found with phone number: " + phoneNumber));
    }

    public User changeUserRole(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId));
        user.setRole(role);
        return userRepository.save(user);
    }

    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRole_RoleName(roleName);
    }

    public List<User> getUsersCreatedAfter(String dateTime) {
        LocalDateTime time = LocalDateTime.parse(dateTime);
        return userRepository.findByAccountCreationDateAfter(time);
    }

	public boolean isUserExists(String credential) {
		// TODO Auto-generated method stub
		return false;
	}
}
