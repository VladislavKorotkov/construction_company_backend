package by.bsuir.constructioncompany.controllers;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.UserRepository;
import by.bsuir.constructioncompany.requests.SignInRequest;
import by.bsuir.constructioncompany.requests.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class AuthControllersTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
    }

    @Test
    public void handlePostCreatingANewAccountWithAnExistingUsername_ReturnsConflictStatus() throws Exception {
        createAccount("vlad@mail.ru", "1234", "ROLE_USER", "Vova", "Vovkin", "+375-29-290-12-12");
         SignUpRequest signUpRequest = SignUpRequest.builder()
                 .username("vlad@mail.ru")
                 .password("123q")
                 .name("Vova")
                 .surname("Vovkin")
                 .phoneNumber("+375-29-290-32-12")
                 .build();
        mockMvc.perform(
                        post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void handlePostCreatingANewAccount_ReturnsOkStatus() throws Exception{
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("vladik@mail.ru")
                .password("123q")
                .name("Vova")
                .surname("Vovkin")
                .phoneNumber("+375-29-290-32-12")
                .build();
        mockMvc.perform(
                        post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void handlePostLoginAccount_ReturnsOkStatus() throws Exception{
        createAccount("vlad@mail.ru", "1234", "ROLE_USER", "Vova", "Vovkin", "+375-29-290-12-12");
        SignInRequest signInRequest = new SignInRequest("vlad@mail.ru", "1234");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(signInRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void handlePostLoginAccountWithWrongPassword_ReturnUnauthorizedStatus() throws Exception{
        createAccount("vlad@mail.ru", "1234", "ROLE_USER", "Vova", "Vovkin", "+375-29-290-12-12");
        SignInRequest signInRequest = new SignInRequest("vlad@mail.ru", "1231234");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(signInRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    User createAccount(String username, String password, String role, String name, String surname, String phoneNumber) throws Exception {
        User user = User.builder()
                .username("vlad@mail.ru")
                .password(passwordEncoder.encode(password))
                .name("Vova")
                .surname("Vovkin")
                .phoneNumber("+375-29-290-32-12")
                .build();
        return userRepository.save(user);
    }
}