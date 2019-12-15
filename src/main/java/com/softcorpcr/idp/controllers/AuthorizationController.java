package com.softcorpcr.idp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcorpcr.idp.model.InvalidTokenResponse;
import com.softcorpcr.idp.model.Request;
import com.softcorpcr.idp.model.Response;
import com.softcorpcr.idp.security.JwtTokenUtil;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationToken(String access_token) throws Exception {
        try {
            if (jwtTokenUtil.validateToken(access_token)) {
                return ResponseEntity.ok(new User(jwtTokenUtil.getUsernameFromToken(access_token), "", new ArrayList<>()));
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