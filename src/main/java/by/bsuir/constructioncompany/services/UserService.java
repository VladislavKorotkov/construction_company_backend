package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.UserAlreadyExistsException;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.requests.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignUpRequest signUpRequest){
        if(userRepository.existsByUsername(signUpRequest.getUsername()))
            throw new UserAlreadyExistsException("Пользователь уже зарегестрирован с данным email");
        userRepository.save(
                User.builder()
                        .username(signUpRequest.getUsername())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .name(signUpRequest.getName())
                        .surname(signUpRequest.getSurname())
                        .phoneNumber(signUpRequest.getPhoneNumber())
                        .build()
        );
    }
}
