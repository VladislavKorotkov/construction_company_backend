package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
