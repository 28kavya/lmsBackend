package com.learnhub.service;

import com.learnhub.entity.User;
import com.learnhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Inside loadUserByUsername"+email);


       User user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
        System.out.println("User found: " + user.getEmail());

       return  org.springframework.security.core.userdetails.User.builder()
               .username(user.getEmail())
               .password(user.getPassword())
               .authorities(String.valueOf(user.getRole()))
               .build();
    }


}
