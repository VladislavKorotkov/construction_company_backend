package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.*;
import by.bsuir.constructioncompany.responses.AuthenticationResponse;
import by.bsuir.constructioncompany.responses.TokenValidationResponse;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid SignInRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<?> register(
            @RequestBody @Valid SignUpRequest request
    ) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-admin-foreman")
    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<?> registerAdminOrForeman(
            @RequestBody @Valid SignUpRequest request
    ) {
        userService.registerAdminOrForeman(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-builder")
    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
    public ResponseEntity<?> registerBuilder(
            @RequestBody @Valid SignUpBuilderRequest signUpBuilderRequest
            ) {
        userService.registerBuilder(signUpBuilderRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody RefreshJwtTokensRequest request
    ){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @GetMapping("/is-access-token-valid")
    public ResponseEntity<TokenValidationResponse> checkAccessTokenValid(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        return ResponseEntity.ok(authenticationService.isAccessTokenValid(authorizationHeader));
    }

    @PutMapping("/blocking-user/{id}")
    public ResponseEntity<String> blockingUser(@PathVariable("id") Long id, @RequestBody BlockingUserRequest blockingUserRequest,Principal principal){
        userService.blockingUser(id, blockingUserRequest, authenticationService.getUserByPrincipal(principal));
        return ResponseEntity.ok("Операция успешно выполнена");
    }

}
