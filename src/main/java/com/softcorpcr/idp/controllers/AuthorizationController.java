package com.softcorpcr.idp.controllers;

import com.softcorpcr.idp.model.Request;
import com.softcorpcr.idp.model.Response;
import com.softcorpcr.idp.security.JwtTokenUtil;
import com.softcorpcr.idp.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(String token) throws Exception {
        return ResponseEntity.ok(jwtTokenUtil.validateToken(token));
    }
}