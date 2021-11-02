package com.onlinestore.services;

import com.onlinestore.entities.Address;

public interface AddressService {

    Address save(Address address);
    void delete(Long id);
    Address update(Address address);

}
