package com.softcorpcr.idp.controllers;

import com.softcorpcr.idp.model.entities.ApplicationEntity;
import com.softcorpcr.idp.repositories.ApplicationsRepository;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/applications")
@CrossOrigin
public class ApplicationsController {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> saveApplication(@RequestBody ApplicationEntity applicationEntity) throws ResponseStatusException {
        try {
            String secretEncoded = applicationEntity.getClientSecret();
            applicationEntity.setClientSecret(passwordEncoder.encode(secretEncoded));
            ApplicationEntity saved = applicationsRepository.save(applicationEntity);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}