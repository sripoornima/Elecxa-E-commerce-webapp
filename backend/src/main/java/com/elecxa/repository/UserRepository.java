package com.elecxa.repository;

import com.elecxa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole_RoleName(String roleName);

    List<User> findByAccountCreationDateAfter(LocalDateTime dateTime);

	Optional<User> findByFirstName(String username);
}
