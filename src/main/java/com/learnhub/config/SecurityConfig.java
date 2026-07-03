package com.learnhub.config;

import com.learnhub.service.JWTFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(req ->req
                        //AUTHCONTROLLER
                        .requestMatchers(HttpMethod.POST,"/api/auth/register","/api/auth/login").permitAll()

                        //ADMIN
                        .requestMatchers(HttpMethod.POST,"/api/course/**","/api/auth/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/course/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/course/**").hasAuthority("ADMIN")
                        //Admin +student
                        .requestMatchers(HttpMethod.GET,"/api/certificate/my","/api/course/**").hasAnyAuthority("ADMIN","STUDENT")
                        //Student+Instructor
                        .requestMatchers(HttpMethod.GET,"/api/lesson/**","/api/quiz/**","/api/questions/**").hasAnyAuthority("INSTRUCTOR","STUDENT")
                        //Student POST
                        .requestMatchers(HttpMethod.POST,"/api/enroll/**","/api/quizanswer/**", "/api/certificate/generate/**").hasAuthority("STUDENT")
                        //Instructor POST
                        .requestMatchers(HttpMethod.POST,"/api/lesson/**","/api/quiz/**","/api/questions/**").hasAuthority("INSTRUCTOR")
                        //ALL
                        .requestMatchers(HttpMethod.GET,"/api/progress/**").hasAnyAuthority("ADMIN","INSTRUCTOR","STUDENT")

                                .anyRequest().authenticated())
                        //disable below line and enable above three line to active security
//                        .anyRequest().permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) {
        return authconfig.getAuthenticationManager();
    }

}
