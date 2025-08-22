package uz.pdp.spring_boot_demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.util.InMemoryResource;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import uz.pdp.spring_boot_demo.dto.AuthErrorDTO;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper jacksonObjectMapper) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login").permitAll()
                        .anyRequest().fullyAuthenticated()
                )
                .httpBasic((httpBasicConfigurer) -> {
                })
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(((request, response, authException) -> {
                            authException.printStackTrace();
                            System.out.println("****************************************");
                            String errorPath = request.getRequestURI();
                            String errorMessage = authException.getMessage() + ". You are not authorized to access this resource";
                            AuthErrorDTO authErrorDTO = new AuthErrorDTO(errorMessage, errorPath, 401);
                            System.out.println("****************************************2");
                            response.sendError(401);
                            ServletOutputStream outputStream = response.getOutputStream();
                            System.out.println("****************************************3");
                            objectMapper.writeValue(outputStream, authErrorDTO);
                            System.out.println("****************************************4");
                        }))
                )

        ;

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            authException.printStackTrace();
            System.out.println("****************************************");
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage() + ". You are not authorized to access this resource";
            AuthErrorDTO authErrorDTO = new AuthErrorDTO(errorMessage, errorPath, 401);
            System.out.println("****************************************2");
            response.sendError(401);
            ServletOutputStream outputStream = response.getOutputStream();
            System.out.println("****************************************3");
            objectMapper.writeValue(outputStream, authErrorDTO);
            System.out.println("****************************************4");
//                            outputStream.flush();
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("admin")
                .password("123")
                .roles("ADMIN", "MENTOR")
                .build();
        UserDetails mentor = User.builder()
                .username("mentor")
                .password("123")
                .roles("MENTOR")
                .build();
        UserDetails student = User.builder()
                .username("student")
                .password("123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, mentor, student);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // don't use in production
    }

}
