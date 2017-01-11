package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.AuthorizationDTO;
import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.controller.response.MessageResponse;
import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.security.AuthenticationFilter;
import com.bdzjn.poretti.service.AuthorizationService;
import com.bdzjn.poretti.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthenticationController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Transactional
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Username or email taken."), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.register(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        final User user = userService.login(loginDTO);
        final AuthorizationDTO authorization = authorizationService.createFor(user);
        return new ResponseEntity<>(authorization, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}/verify")
    public ResponseEntity verify(@PathVariable long id) {
        userService.verify(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader(name = AuthenticationFilter.AUTHORIZATION) String token) {
        authorizationService.deleteByToken(token);
        return new ResponseEntity(HttpStatus.OK);
    }


}
