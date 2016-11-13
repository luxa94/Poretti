package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.exception.AuthenticationException;
import com.bdzjn.poretti.model.Image;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.Role;
import com.bdzjn.poretti.repository.ImageRepository;
import com.bdzjn.poretti.repository.UserRepository;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImageRepository imageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(long id) {
        return null;
    }

    @Override
    public boolean areUsernameOrEmailTaken(String username, String email) {
        return userRepository.areUsernameOrEmailTaken(username, email);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        final User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setName(registerDTO.getName());
        user.setImage(imageRepository.findById(registerDTO.getImageId()).orElse(new Image("/images/defaultUser.jpg")));
        user.setPhoneNumbers(registerDTO.getPhoneNumbers());
        user.setContactEmails(registerDTO.getContactEmails());
        user.setRole(Role.USER);
        user.setPermissions(Role.USER.getPermissions()); // TODO: Set permissions when email is confirmed.
        user.setLocation(registerDTO.getLocation());

        return create(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        final User user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(AuthenticationException::new);

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException();
        }

        return user;
    }
}
