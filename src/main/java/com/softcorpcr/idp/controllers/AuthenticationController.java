package com.softcorpcr.idp.controllers;

import com.softcorpcr.idp.AuthManager;
import com.softcorpcr.idp.model.Request;
import com.softcorpcr.idp.model.Response;
import com.softcorpcr.idp.model.entities.TokensEntity;
import com.softcorpcr.idp.repositories.TokensRepository;
import com.softcorpcr.idp.security.TokenUtil;
import com.softcorpcr.idp.security.encription.Encrypter;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private TokensRepository tokensRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Request authenticationRequest) throws Exception {
        UserDetails userDetails = null;
        Authentication authentication = null;
        TokensEntity entity = new TokensEntity();
        TokensEntity existing = null;
        ResponseEntity<?> response;
        entity.setGrantType(authenticationRequest.getGrant_type());

        switch (authenticationRequest.getGrant_type()) {
            case "client_credentials":
                if (authenticationRequest.getClient_id().isEmpty() || authenticationRequest.getClient_secret().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client secret or client ID empty");
                }

                response = checkIfTokenExists(authenticationRequest.getClient_id());
                if (null != response) {
                    entity = null;
                    return response;
                }

                authentication = authenticate(authenticationRequest.getClient_id(), authenticationRequest.getClient_secret());
                entity.setClientCredentials(authenticationRequest.getClient_id());
                break;
            case "password":
                if (authenticationRequest.getUserName().isEmpty() || authenticationRequest.getPassword().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Password empty");
                }
                response = checkIfTokenExists(authenticationRequest.getClient_id());
                if (null != response) {
                    entity = null;
                    return response;
                }

                authentication = authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
                entity.setClientCredentials(authenticationRequest.getUserName());
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid grant type " + authenticationRequest.getGrant_type() + ". Valid values are client_credentials or password.");
        }

        userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("gty", authenticationRequest.getGrant_type());
        final String token = tokenUtil.generateToken(userDetails, claims);

        entity.setCreationDate(Instant.now());
        entity.setExpiryDate(tokenUtil.getExpirationDateFromToken(token).toInstant());
        entity.setToken(token);
        tokensRepository.save(entity);

        return ResponseEntity.ok(new Response(token));
    }

    private ResponseEntity<?> checkIfTokenExists(String clientCredentials) {

        TokensEntity existing = tokensRepository.getCurrentToken(clientCredentials);
        if (null != existing) {
            return ResponseEntity.ok(new Response(existing.getToken()));
        } else {
            return null;
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}