package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address createAddress(String city, String street, String numberHouse){
        Optional<Address> addressOptional = addressRepository.findByCityAndStreetAndNumberHouse(city, street,numberHouse);
        return addressOptional.orElseGet(() -> addressRepository.save(new Address(city, street, numberHouse)));
    }
    @Transactional
    public Address createAddress(Address address){
        Optional<Address> addressOptional = addressRepository.findByCityAndStreetAndNumberHouse(address.getCity(), address.getStreet(), address.getNumberHouse());
        return addressOptional.orElseGet(() -> addressRepository.save(address));
    }
}
