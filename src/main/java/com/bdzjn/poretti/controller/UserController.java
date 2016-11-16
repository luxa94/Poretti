package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/createSysAdmin")
    @PreAuthorize("hasAuthority('CREATE_SYSTEM_ADMIN')")
    public ResponseEntity crateAdmin(@RequestBody RegisterDTO registerDTO){
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())){
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.createAdmin(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/createVerifier")
    @PreAuthorize("hasAuthority('CREATE_VERIFIER')")
    public ResponseEntity crateVerifier(@RequestBody RegisterDTO registerDTO){
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())){
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.createVerifier(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }
}
