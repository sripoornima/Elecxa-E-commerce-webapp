package com.elecxa.repository;

import com.elecxa.model.Address;
import com.elecxa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

	Address save(Address address);
}
