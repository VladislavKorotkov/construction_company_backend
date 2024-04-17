package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.ImpossibleToBlockTheUserException;
import by.bsuir.constructioncompany.exceptions.IncorrectDataException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.exceptions.UserAlreadyExistsException;
import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.Specialization;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.models.enums.Role;
import by.bsuir.constructioncompany.repositories.UserRepository;
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
    public void registerForAdmin(SignUpRequest signUpRequest){
        if(userRepository.existsByUsername(signUpRequest.getUsername()))
            throw new UserAlreadyExistsException("Пользователь уже зарегестрирован с данным email");
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .surname(signUpRequest.getSurname())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .role(signUpRequest.getRole())
                .build();
        userRepository.save(user);
        if(signUpRequest.getRole().equals(Role.ROLE_BUILDER)){
            if(signUpRequest.getSpecializationId()!=null){
                registerBuilder(user, signUpRequest.getSpecializationId());
            }
            else throw new IncorrectDataException("Id специальности не может быть пустым");
        }

    }

    @Transactional
    public void registerBuilder(User user, Long specializationId){
        Specialization specialization = specializationService.getSpecializationById(specializationId);
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
    public void blockingUser(Long id, User admin){
        User user = getUserById(id);
        if(user.getId().equals(admin.getId()))
            throw new ImpossibleToBlockTheUserException("Невозможно заблокировать собственный аккаунт");
        user.setIsBlocked(!user.getIsBlocked());
        userRepository.save(user);
    }
}
