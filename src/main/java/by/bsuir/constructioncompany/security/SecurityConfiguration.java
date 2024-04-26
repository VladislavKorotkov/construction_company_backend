package by.bsuir.constructioncompany.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    public static final String[] WHITE_LIST_URL = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh-token",
            "/api/auth/is-access-token-valid",
            "/api/reviews/**"
    };
    public static final String[] ADMIN_LIST_URL = {
            "/api/auth/blocking-user/**",
            "/api/auth/users",
            "/api/auth/admin/register"
    };

    public static final String[] FOREMAN_LIST_URL = {
            "/api/auth/register-builder",
            "/api/specializations/**",
            "/api/builders/**",
            "/api/projects/foreman",
            "/api/materials/**",
            "/api/works/**"
    };

    public static final String[] BUILDER_LIST_URL = {
            "/api/tasks/builder",
    };

    public static final String[] BUILDER_FOREMAN_ADMIN_URL = {
            "/api/projects",
    };


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final EmailAuthenticationProvider emailAuthenticationProvider;
//    private final LogoutHandler logoutHandler;

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(FOREMAN_LIST_URL).hasAnyRole("FOREMAN", "ADMIN")
                                .requestMatchers(ADMIN_LIST_URL).hasRole("ADMIN")
                                .requestMatchers(BUILDER_LIST_URL).hasRole("BUILDER")
                                .requestMatchers(BUILDER_FOREMAN_ADMIN_URL).hasAnyRole("FOREMAN", "ADMIN", "BUILDER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(emailAuthenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        //    .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")//TODO сделать страницу logout
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
        ;
        return http.build();
    }
}
