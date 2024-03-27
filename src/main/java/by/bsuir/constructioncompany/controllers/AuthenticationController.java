package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.*;
import by.bsuir.constructioncompany.responses.AuthenticationResponse;
import by.bsuir.constructioncompany.responses.TokenValidationResponse;
import by.bsuir.constructioncompany.responses.UserResponse;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.UserService;
import by.bsuir.constructioncompany.utils.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    // TODO logout

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid SignInRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid SignUpRequest request
    ) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-admin-foreman")
    public ResponseEntity<?> registerAdminOrForeman(
            @RequestBody @Valid SignUpRequest request
    ) {
        userService.registerAdminOrForeman(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-builder")
    public ResponseEntity<?> registerBuilder(
            @RequestBody @Valid SignUpBuilderRequest signUpBuilderRequest
            ) {
        userService.registerBuilder(signUpBuilderRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody RefreshJwtTokensRequest request
    ){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/is-access-token-valid")
    public ResponseEntity<TokenValidationResponse> checkAccessTokenValid(
            @RequestBody TokenValidationRequest tokenValidationRequest
    ){
        return ResponseEntity.ok(authenticationService.isAccessTokenValid(tokenValidationRequest.getToken()));
    }

    @PutMapping("/blocking-user/{id}")
    public ResponseEntity<String> blockingUser(@PathVariable("id") Long id,Principal principal){
        userService.blockingUser(id, authenticationService.getUserByPrincipal(principal));
        return ResponseEntity.ok("Операция успешно выполнена");
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getApplicationsByUser(Principal principal){
        return ResponseEntity.ok(UserMapper.mapToResponse(authenticationService.getUserByPrincipal(principal)));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(){
        return ResponseEntity.ok(authenticationService.getUsers());
    }


}
