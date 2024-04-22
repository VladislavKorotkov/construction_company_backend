package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.SignUpRequest;

public interface UserService {
    void registerUser(SignUpRequest signUpRequest);
    void registerForAdmin(SignUpRequest signUpRequest);
    void registerBuilder(User user, Long specializationId);
    User getUserById(Long id);
    void blockingUser(Long id, User admin);
}
