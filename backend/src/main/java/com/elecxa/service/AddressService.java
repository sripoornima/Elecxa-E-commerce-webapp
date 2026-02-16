package com.elecxa.service;

import com.elecxa.model.Address;
import com.elecxa.model.User;
import com.elecxa.repository.AddressRepository;
import com.elecxa.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public List<Address> getAddressesByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address updatedAddress, long id) {
    	 User user = userRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("User not found"));

         // Find the existing address
         Address existingAddress = addressRepository.findByUser(user).get(0);
         // Update fields
         existingAddress.setCity(updatedAddress.getCity());
         existingAddress.setPincode(updatedAddress.getPincode());
         existingAddress.setDoorNoStreetName(updatedAddress.getDoorNoStreetName());
         existingAddress.setLandmark(updatedAddress.getLandmark());
         existingAddress.setDistrict(updatedAddress.getDistrict());
         existingAddress.setState(updatedAddress.getState());

         // Save updated address
        return addressRepository.save(existingAddress);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
