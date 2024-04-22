package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.JwtRefreshToken;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.JwtRefreshTokenRepository;
import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.requests.RefreshJwtTokensRequest;
import by.bsuir.constructioncompany.requests.SignInRequest;
import by.bsuir.constructioncompany.responses.AuthenticationResponse;
import by.bsuir.constructioncompany.responses.TokenValidationResponse;
import by.bsuir.constructioncompany.responses.UserResponse;
import by.bsuir.constructioncompany.security.JwtService;
import by.bsuir.constructioncompany.security.TokenType;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.utils.UserMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, JwtRefreshTokenRepository jwtRefreshTokenRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
    }
    @Override
    @Transactional
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
        saveUserRefreshToken(user, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    @Transactional
    public void saveUserRefreshToken(User user, String jwtToken) {
        JwtRefreshToken jwtRefreshToken;
        Optional<JwtRefreshToken> jwtRefreshTokenOptional = jwtRefreshTokenRepository.findByUser(user);
        if(jwtRefreshTokenOptional.isPresent()){
            jwtRefreshToken = jwtRefreshTokenOptional.get();
            jwtRefreshToken.setToken(jwtToken);
        }
        else
            jwtRefreshToken =  JwtRefreshToken.builder()
                    .user(user)
                    .token(jwtToken)
                    .build();
        jwtRefreshTokenRepository.save(jwtRefreshToken);
    }
    @Override
    public User isValidTokenAndRetrieveUser(String token, TokenType tokenType){
        if(token == null)
            throw new IllegalArgumentException("Недопустимый заголовок авторизации");
        final String userEmail = jwtService.extractSubject(token);
        if (userEmail == null) {
            throw new IllegalArgumentException("Неверный адрес электронной почты пользователя в токене");
        }
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("Токен содержит неверный email"));
        if (!jwtService.isTokenValid(token, user, tokenType)) {
            throw new IllegalArgumentException("Неверный токен");
        }
        return user;
    }
    @Override
    public void isRefreshTokenRepresented(String refreshToken){
        Optional<JwtRefreshToken> jwtRefreshTokenOptional = jwtRefreshTokenRepository.findByToken(refreshToken);
        if(jwtRefreshTokenOptional.isEmpty())
            throw new ObjectNotFoundException("Refresh token не валиден");
    }
    @Override
    public AuthenticationResponse refreshToken(
            RefreshJwtTokensRequest refreshJwtTokensRequest
    ){
        final String refreshToken = refreshJwtTokensRequest.getJwtRefreshToken();
        User user = isValidTokenAndRetrieveUser(refreshToken, TokenType.REFRESH);
        isRefreshTokenRepresented(refreshToken);
        String accessToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    public TokenValidationResponse  isAccessTokenValid(String accessToken) {
        boolean isValid = true;
        try{
            isValidTokenAndRetrieveUser(accessToken, TokenType.ACCESS);
        }
        catch (IllegalArgumentException | ObjectNotFoundException | ExpiredJwtException e) {
            isValid = false;
        }
        return TokenValidationResponse.builder()
                .isAccessTokenValid(isValid)
                .build();
    }
    @Override
    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(()-> new ObjectNotFoundException("Пользователь не найден"));
    }
    @Override
    public List<UserResponse> getUsers(){
        return UserMapper.mapToResponseList(userRepository.findAll());
    }

}
