package com.elecxa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.elecxa.model.RevokedToken;

@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {
    boolean existsByToken(String token);
}
