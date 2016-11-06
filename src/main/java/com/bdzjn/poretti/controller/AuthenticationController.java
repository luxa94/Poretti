package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthenticationController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }
}
