package com.softcorpcr.idp.services;

import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.model.entities.ClientsEntity;
import com.softcorpcr.idp.repositories.ApplicationsRepository;
import com.softcorpcr.idp.repositories.ClientsRepository;
import com.softcorpcr.idp.security.encription.Encrypter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        Encrypter encrypter = new Encrypter();
        ApplicationEntity app = applicationsRepository.getByUsernameOrClientId(encrypter.encrypt(username));
        if (null == app) {
            ClientsEntity client = clientsRepository.getByUsernameOrClientId(encrypter.encrypt(username));
            client.setUsername(encrypter.decrypt(client.getUsername()));
            if (null == client) {
                throw new UsernameNotFoundException(String.format("'%s' not found.", username));
            }
            return new User(client.getUsername(), client.getPassword(), new ArrayList<>());
        }
        return new User(encrypter.decrypt(app.getClientId()), app.getClientSecret(), new ArrayList<>());
    }
}
