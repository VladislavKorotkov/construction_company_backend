package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.repositories.AddressRepository;
import by.bsuir.constructioncompany.services.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address createAddress(String city, String street, String numberHouse){
        Optional<Address> addressOptional = addressRepository.findByCityAndStreetAndNumberHouse(city, street,numberHouse);
        return addressOptional.orElseGet(() -> addressRepository.save(new Address(city, street, numberHouse)));
    }
    @Override
    @Transactional
    public Address createAddress(Address address){
        Optional<Address> addressOptional = addressRepository.findByCityAndStreetAndNumberHouse(address.getCity(), address.getStreet(), address.getNumberHouse());
        return addressOptional.orElseGet(() -> addressRepository.save(address));
    }
}
