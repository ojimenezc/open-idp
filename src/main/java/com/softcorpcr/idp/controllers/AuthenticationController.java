package com.softcorpcr.idp.controllers;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Request authenticationRequest) throws Exception {
        UserDetails userDetails = null;

        switch (authenticationRequest.getGrant_type()) {
            case "client_credentials":
                if (authenticationRequest.getClient_id().isEmpty() || authenticationRequest.getClient_secret().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client secret or client ID empty");
                }

                authenticate(authenticationRequest.getClient_id(), authenticationRequest.getClient_secret());

                userDetails = userDetailsService
                        .loadUserBySecretAndId(authenticationRequest.getClient_id(), authenticationRequest.getClient_secret());
                break;
            case "password":
                if (authenticationRequest.getUserName().isEmpty() || authenticationRequest.getPassword().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Password empty");
                }

                authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

                userDetails = userDetailsService
                        .loadUserByUsernameAndPassword(authenticationRequest.getUserName(), authenticationRequest.getPassword());
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid grant type " + authenticationRequest.getGrant_type() + ". Valid values are client_credentials or password.");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new Response(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}