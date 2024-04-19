package ru.ssau.todolist.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${security.logins}")
    List<String> logins;

    @Value("${security.roles}")
    List<String> roles;

    @Value("${security.passwords}")
    List<String> passwords;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/projects/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/projects", true)
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        for (int i = 0; i < logins.size(); i++) {
            UserDetails user =
                    User.withUsername(logins.get(i))
                            .password(passwordEncoder().encode(passwords.get(i)))
                            .roles(roles.get(i))
                            .build();

            users.add(user);
        }

        return new InMemoryUserDetailsManager(users);
    }
}
