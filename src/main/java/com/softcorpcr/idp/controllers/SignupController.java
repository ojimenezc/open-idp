package com.softcorpcr.idp.controllers;

import com.softcorpcr.idp.model.entities.Application;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api")
@CrossOrigin
public class SignupController {

    @Autowired
    JwtUserDetailsService userDetailsService;


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(Application application) throws Exception {
        try {

            return ResponseEntity.ok(application);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}