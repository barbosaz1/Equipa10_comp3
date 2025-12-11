package gestaoeventos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    // Bean que fornece um PasswordEncoder.
    // Usamos BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt default strength is 10
        return new BCryptPasswordEncoder();
    }
}