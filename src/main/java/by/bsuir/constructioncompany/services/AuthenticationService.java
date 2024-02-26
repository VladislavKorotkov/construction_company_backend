package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, UserService userService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }


}
