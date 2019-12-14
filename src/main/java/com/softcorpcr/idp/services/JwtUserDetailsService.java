package com.softcorpcr.idp.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

   /* private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) {
       /* Person person = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));*/
        return new User(username, "$2a$10$xOr/AjDilstl6uLYSAkhoeq9Pcff6BN7ho21ZRQnfmwOQoHjNLcLC", new ArrayList<>());
    }

}
