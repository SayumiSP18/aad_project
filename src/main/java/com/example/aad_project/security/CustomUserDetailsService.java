package com.example.aad_project.security;

import com.example.aad_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.aad_project.entity.User> optionalUser = userRepository.findByUserName(username);

        if(optionalUser.isEmpty())
            throw new RuntimeException("Sorry no user");


        return User.builder()
                .username(optionalUser.get().getUserName())
                .password(optionalUser.get().getPassword())
                .roles(optionalUser.get().getUserRoles())
                .build();
    }

}
