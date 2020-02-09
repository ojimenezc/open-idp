package com.softcorpcr.idp.services;

import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.model.entities.ClientsEntity;
import com.softcorpcr.idp.repositories.ApplicationsRepository;
import com.softcorpcr.idp.repositories.ClientsRepository;
import com.softcorpcr.idp.security.encription.Encrypter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

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
        String[] usernameAndGrantType = username.split("&");
        if (usernameAndGrantType.length == 0) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and client credentials combination");
        }
        switch (usernameAndGrantType[1]) {
            case "client_credentials":
                ApplicationEntity app = applicationsRepository.getByUsernameOrClientId(encrypter.encrypt(usernameAndGrantType[0]));
                if (null == app) {
                    throw new UsernameNotFoundException(String.format("'%s' not found.", usernameAndGrantType[0]));
                }
                return new User(encrypter.decrypt(app.getClientId()), app.getClientSecret(), new ArrayList<>());

            case "password":
                ClientsEntity client = clientsRepository.getByUsernameOrClientId(encrypter.encrypt(usernameAndGrantType[0]));
                if (null == client) {
                    throw new UsernameNotFoundException(String.format("'%s' not found.", usernameAndGrantType[0]));
                }
                return new User(encrypter.decrypt(client.getUsername()), client.getPassword(), new ArrayList<>());
        }

        return null;
    }
}
