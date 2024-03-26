package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.responses.TaskResponse;
import by.bsuir.constructioncompany.responses.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static List<UserResponse> mapToResponseList(List<User> users){
        return users.stream()
                .map(UserMapper::mapToResponse)
                .collect(Collectors.toList());
    }
    public static UserResponse mapToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .isBlocked(user.getIsBlocked())
                .role(user.getRole().getValue())
                .build();
    }
}
