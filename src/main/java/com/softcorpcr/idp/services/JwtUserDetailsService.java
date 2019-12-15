package com.softcorpcr.idp.services;

import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.model.entities.ClientsEntity;
import com.softcorpcr.idp.repositories.ApplicationsRepository;
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

    private final ApplicationsRepository applicationsRepository;
    private final ClientsRepository clientsRepository;

    public JwtUserDetailsService(ApplicationsRepository userRepository, ClientsRepository clientsRepository) {
        this.applicationsRepository = userRepository;
        this.clientsRepository = clientsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        ApplicationEntity app = applicationsRepository.getByUsernameOrClientId(username);
        if (null == app) {
            ClientsEntity client = clientsRepository.getByUsernameOrClientId(username);
            if (null == client) {
                throw new UsernameNotFoundException(String.format("'%s' not found.", username));
            }
        }
        return new User(app.getClientId(), app.getClientSecret(), new ArrayList<>());
    }
}
