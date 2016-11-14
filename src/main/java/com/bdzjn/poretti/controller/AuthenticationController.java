package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.AuthorizationService;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.register(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        final User user = userService.login(loginDTO);
        final Authorization authorization = authorizationService.createFor(user);
        return new ResponseEntity<>(authorization.getToken(), HttpStatus.OK);
    }

}
