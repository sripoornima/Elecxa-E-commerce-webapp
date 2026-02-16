package com.elecxa.controller;

import com.elecxa.model.User;
import com.elecxa.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 2. Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getAllUserCount() {
        return ResponseEntity.ok(userService.getAllUsers().size());
    }

    // 3. Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 4. Update user by ID
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // 5. Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // 6. Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // 7. Get user by phone number
    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phone));
    }

    // 8. Change user's role
    @PutMapping("/{id}/role/{roleId}")
    public ResponseEntity<User> changeUserRole(@PathVariable Long id, @PathVariable Long roleId) {
        return ResponseEntity.ok(userService.changeUserRole(id, roleId));
    }

    // 9. Get users by role name
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.getUsersByRole(roleName));
    }

    // 10. Get users created after a specific date
    @GetMapping("/created-after")
    public ResponseEntity<List<User>> getUsersCreatedAfter(@RequestParam String dateTime) {
        return ResponseEntity.ok(userService.getUsersCreatedAfter(dateTime));
    }
}
