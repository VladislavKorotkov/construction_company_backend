package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCityAndStreetAndNumberHouse(String city, String street, String numberHouse);
}
