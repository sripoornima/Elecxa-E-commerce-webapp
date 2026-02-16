package com.elecxa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elecxa.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
