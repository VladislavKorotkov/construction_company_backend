package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.ImpossibleToBlockTheUserException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.exceptions.UserAlreadyExistsException;
import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.Specialization;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.models.enums.Role;
import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.requests.BlockingUserRequest;
import by.bsuir.constructioncompany.requests.SignUpBuilderRequest;
import by.bsuir.constructioncompany.requests.SignUpRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpecializationService specializationService;
    private final BuilderService builderService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SpecializationService specializationService, BuilderService builderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.specializationService = specializationService;
        this.builderService = builderService;
    }

    @Transactional
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

    @Transactional
    public void registerAdminOrForeman(SignUpRequest signUpRequest){
        if(userRepository.existsByUsername(signUpRequest.getUsername()))
            throw new UserAlreadyExistsException("Пользователь уже зарегестрирован с данным email");
        userRepository.save(
                User.builder()
                        .username(signUpRequest.getUsername())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .name(signUpRequest.getName())
                        .surname(signUpRequest.getSurname())
                        .phoneNumber(signUpRequest.getPhoneNumber())
                        .role(signUpRequest.getRole())
                        .build()
        );
    }

    @Transactional
    public void registerBuilder(SignUpBuilderRequest signUpBuilderRequest){
        if(userRepository.existsByUsername(signUpBuilderRequest.getUsername()))
            throw new UserAlreadyExistsException("Пользователь уже зарегестрирован с данным email");
        User user = userRepository.save(
                User.builder()
                        .username(signUpBuilderRequest.getUsername())
                        .password(passwordEncoder.encode(signUpBuilderRequest.getPassword()))
                        .name(signUpBuilderRequest.getName())
                        .surname(signUpBuilderRequest.getSurname())
                        .phoneNumber(signUpBuilderRequest.getPhoneNumber())
                        .role(Role.ROLE_BUILDER)
                        .build()
        );
        Specialization specialization = specializationService.getSpecializationById(signUpBuilderRequest.getSpecialization_id());
        Builder builder = Builder.builder()
                .user(user)
                .specialization(specialization)
                .build();
        builderService.createBuilder(builder);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Пользователь не найден"));
    }

    @Transactional
    public void blockingUser(Long id, BlockingUserRequest blockingUserRequest, User admin){
        User user = getUserById(id);
        if(user.getId().equals(admin.getId()))
            throw new ImpossibleToBlockTheUserException("Невозможно заблокировать собственный аккаунт");
        user.setIsBlocked(!user.getIsBlocked());
        userRepository.save(user);
    }
}
