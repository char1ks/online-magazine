package com.example.petProject1.Config;

import com.example.petProject1.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;

    @Autowired
    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().and()
                .authorizeRequests()
                .requestMatchers("/user/login","/user/register","/user/reg").permitAll()
                .requestMatchers("/product/add","/product/{id}/edit").hasRole("SELLER")
                .anyRequest().hasAnyRole("SELLER","BUYER")
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/product", true)//ЗАМЕНИТЬ
                .failureUrl("/user/login?error=true") // Добавлено параметр error
                .permitAll() // Позволяет всем доступ к странице входа
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/user/login?logout=true") // Перенаправление после выхода
                .permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// Используйте NoOpPasswordEncoder для простоты
    }
}
