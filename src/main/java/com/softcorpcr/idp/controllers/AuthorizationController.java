package com.softcorpcr.idp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcorpcr.idp.model.InvalidTokenResponse;
import com.softcorpcr.idp.security.TokenUtil;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AuthorizationController {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ResponseEntity<?> authorize(String access_token) throws Exception {
        try {
            if (tokenUtil.validateToken(access_token)) {
                return ResponseEntity.ok(new User(tokenUtil.getUsernameFromToken(access_token), "", new ArrayList<>()));
            } else {
                InvalidTokenResponse response = new InvalidTokenResponse();
                response.setAccess_token(access_token);
                response.setMessage("Invalid Token");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, new ObjectMapper().writeValueAsString(response));
            }
        } catch (Exception e) {
            InvalidTokenResponse response = new InvalidTokenResponse();
            response.setAccess_token(access_token);
            response.setMessage("Invalid Token");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, new ObjectMapper().writeValueAsString(response));
        }
    }
}