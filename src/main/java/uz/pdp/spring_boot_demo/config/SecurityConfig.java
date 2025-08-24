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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.pdp.spring_boot_demo.dto.AuthErrorDTO;

import java.util.List;

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
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().fullyAuthenticated()
                )
                .httpBasic((httpBasicConfigurer) -> {
                })
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )

        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.addAllowedOriginPattern(List.of("localhost:3000","http://localhost:3000","http://localhost:8080"));
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", configuration);


//        CorsConfiguration configuration2 = new CorsConfiguration();
//        //configuration.addAllowedOriginPattern(List.of("localhost:3000","http://localhost:3000","http://localhost:8080"));
//        configuration.addAllowedOriginPattern("*");
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH"));
//        source.registerCorsConfiguration("/api/v2/**", configuration2);
        return source;
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, authException) -> {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage() + ". You do not have permission or role to access this resource";
            AuthErrorDTO authErrorDTO = new AuthErrorDTO(errorMessage, errorPath, 403);
            response.setStatus(403);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, authErrorDTO);
        });
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, authException) -> {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage() + ". You are not authorized to access this resource";
            AuthErrorDTO authErrorDTO = new AuthErrorDTO(errorMessage, errorPath, 401);
            response.setStatus(401);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, authErrorDTO);
        });
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
