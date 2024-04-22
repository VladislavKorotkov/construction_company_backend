package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.RefreshJwtTokensRequest;
import by.bsuir.constructioncompany.requests.SignInRequest;
import by.bsuir.constructioncompany.responses.AuthenticationResponse;
import by.bsuir.constructioncompany.responses.TokenValidationResponse;
import by.bsuir.constructioncompany.responses.UserResponse;
import by.bsuir.constructioncompany.security.TokenType;

import java.security.Principal;
import java.util.List;

public interface AuthenticationService {
    AuthenticationResponse authenticate(SignInRequest request);
    void saveUserRefreshToken(User user, String jwtToken);
    User isValidTokenAndRetrieveUser(String token, TokenType tokenType);
    void isRefreshTokenRepresented(String refreshToken);
    AuthenticationResponse refreshToken(RefreshJwtTokensRequest refreshJwtTokensRequest);
    TokenValidationResponse isAccessTokenValid(String accessToken);
    User getUserByPrincipal(Principal principal);
    List<UserResponse> getUsers();
}
