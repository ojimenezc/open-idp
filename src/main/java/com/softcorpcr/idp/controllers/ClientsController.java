package com.softcorpcr.idp.controllers;

import com.softcorpcr.idp.model.PasswordUpdateRequest;
import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.model.entities.ClientsEntity;
import com.softcorpcr.idp.repositories.ApplicationsRepository;
import com.softcorpcr.idp.repositories.ClientsRepository;
import com.softcorpcr.idp.security.encription.Encrypter;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class ClientsController {

    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<ClientsEntity> create(@RequestBody ClientsEntity customersEntity) {
        Encrypter encrypter = new Encrypter();
        String username = encrypter.encrypt(customersEntity.getEmail());

        ClientsEntity existing = clientsRepository.getByUsernameOrClientId(username);
        if (null != existing) {
            return new ResponseEntity("The email " + customersEntity.getEmail() + " is already registered in the IDP", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        customersEntity.setEmail("N/A");
        customersEntity.setPassword(passwordEncoder.encode(customersEntity.getPassword()));
        customersEntity.setUsername(username);
        customersEntity.setJoiningDate(Instant.now());
        customersEntity.setLastUpdate(Instant.now());

        clientsRepository.save(customersEntity);
        customersEntity.setPassword(null);
        customersEntity.setBiometric(null);
        return ResponseEntity.ok(customersEntity);
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<ClientsEntity> passwordUpdate(@RequestBody PasswordUpdateRequest request) {

        Encrypter encrypter = new Encrypter();
        String username = encrypter.encrypt(request.getUsername());

        ClientsEntity existing = clientsRepository.getByUsernameOrClientId(username);
        if (null == existing) {
            return new ResponseEntity("The email " + request.getUsername() + " was not found in the IDP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        existing.setPassword(passwordEncoder.encode(request.getPassword()));
        clientsRepository.save(existing);

        return ResponseEntity.ok(existing);
    }
}