package com.onlinestore.controllers;

import com.onlinestore.controllers.dto.AddressDto;
import com.onlinestore.entities.Address;
import com.onlinestore.services.AddressService;
import com.onlinestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/addresses")
public class AddressController {

    private AddressService addressService;
    private UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService){
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping
    public AddressDto save(@RequestBody AddressDto addressDto){
        Address address = AddressDto.toAddress(addressDto, userService);
        Address addressDb = addressService.save(address);
        return AddressDto.toAddressDto(addressDb);
    }

    // 1,2,3 | 4
    @DeleteMapping(path = "/{id}")
    public HttpStatus delete(@PathVariable Long id){
        addressService.delete(id);
        return HttpStatus.OK;
    }






}
