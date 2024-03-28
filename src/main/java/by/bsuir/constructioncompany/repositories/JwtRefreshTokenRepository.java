package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.JwtRefreshToken;
import by.bsuir.constructioncompany.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {
    void deleteIfExistsByUser(User user);
    Optional<JwtRefreshToken> findByToken(String token);
    Optional<JwtRefreshToken> findByUser(User user);
}
