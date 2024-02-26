package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.JwtRefreshToken;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.JwtRefreshTokenRepository;
import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.requests.RefreshJwtTokensRequest;
import by.bsuir.constructioncompany.requests.SignInRequest;
import by.bsuir.constructioncompany.responses.AuthenticationResponse;
import by.bsuir.constructioncompany.responses.TokenValidationResponse;
import by.bsuir.constructioncompany.security.JwtService;
import by.bsuir.constructioncompany.security.TokenType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.Principal;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, JwtRefreshTokenRepository jwtRefreshTokenRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
    }

    public AuthenticationResponse authenticate(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeUserJwtRefreshToken(user);
        saveUserRefreshToken(user, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeUserJwtRefreshToken(User user){
        jwtRefreshTokenRepository.deleteIfExistsByUser(user);
    }

    private void saveUserRefreshToken(User user, String jwtToken) {
        var token = JwtRefreshToken.builder()
                .user(user)
                .token(jwtToken)
                .build();

        jwtRefreshTokenRepository.save(token);
    }

    private User isValidTokenAndRetrieveUser(String refreshToken){
        if(refreshToken == null)
            throw new IllegalArgumentException("Недопустимый заголовок авторизации");
        final String userEmail = jwtService.extractSubject(refreshToken);
        if (userEmail == null) {
            throw new IllegalArgumentException("Неверный адрес электронной почты пользователя в токене");
        }
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("Токен содержит неверный email"));
        if (!jwtService.isTokenValid(refreshToken, user, TokenType.REFRESH)) {
            throw new IllegalArgumentException("Неверный токен");
        }
        return user;
    }

    public AuthenticationResponse refreshToken(
            RefreshJwtTokensRequest refreshJwtTokensRequest
    ){
        final String refreshToken = refreshJwtTokensRequest.getJwtRefreshToken();
        User user = isValidTokenAndRetrieveUser(refreshToken);
        String accessToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenValidationResponse isAccessTokenValid(String authorizationHeader) {
        String accessToken = jwtService.extractAccessToken(authorizationHeader);
        isValidTokenAndRetrieveUser(accessToken);
        return TokenValidationResponse.builder()
                .isAccessTokenValid(true)
                .build();
    }

}
