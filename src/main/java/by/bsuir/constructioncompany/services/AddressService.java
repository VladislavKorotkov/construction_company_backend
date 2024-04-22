package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Address;

public interface AddressService {
     Address createAddress(String city, String street, String numberHouse);
     Address createAddress(Address address);

}
