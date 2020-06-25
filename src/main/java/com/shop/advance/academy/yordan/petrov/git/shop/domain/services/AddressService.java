package com.shop.advance.academy.yordan.petrov.git.shop.domain.services;

import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.AddressServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.AddressServiceViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    AddressServiceModel createAddress(AddressServiceModel addressServiceModel);

    void updateAddress(AddressServiceModel addressServiceModel);

    AddressServiceViewModel getAddressById(long id);

    List<AddressServiceViewModel> getAllAddresses();

    void deleteAddressById(long id);


}
