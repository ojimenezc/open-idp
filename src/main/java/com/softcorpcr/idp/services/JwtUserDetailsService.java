package com.softcorpcr.idp.services;

import com.softcorpcr.idp.model.entities.ClientsEntity;
import com.softcorpcr.idp.repositories.ClientsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final ClientsRepository clientsRepository;

    public JwtUserDetailsService(ClientsRepository userRepository) {
        this.clientsRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        ClientsEntity client = clientsRepository.getByUsernameOrClientId(username);
        if (null == client) {
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
        }

        return new User(client.getUsername(), client.getPassword(), new ArrayList<>());
    }
}
