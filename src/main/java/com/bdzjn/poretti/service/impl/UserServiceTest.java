package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.exception.AuthenticationException;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.Role;
import com.bdzjn.poretti.repository.CompanyRepository;
import com.bdzjn.poretti.repository.UserRepository;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceTest implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceTest(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
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
    public User register(RegisterDTO registerDTO) {
        final User user = createUserWithRole(registerDTO, Role.USER);

        companyRepository.findById(registerDTO.getCompanyId()).ifPresent(company -> {
            final Membership membership = new Membership();
            membership.setCompany(company);
            membership.setMember(user);
            user.addMembership(membership);
        });
        return userRepository.save(user);
    }

    @Override
    public User createUser(RegisterDTO registerDTO) {
        final User user = createUserWithRole(registerDTO, Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User createVerifier(RegisterDTO registerDTO) {
        final User user = createUserWithRole(registerDTO, Role.VERIFIER);
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(RegisterDTO registerDTO) {
        final User user = createUserWithRole(registerDTO, Role.SYSTEM_ADMIN);
        return userRepository.save(user);
    }

    private User createUserWithRole(RegisterDTO registerDTO, Role role) {
        final User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setName(registerDTO.getName());
        user.setImageUrl(registerDTO.getImageUrl());
        user.setPhoneNumbers(registerDTO.getPhoneNumbers());
        user.setContactEmails(registerDTO.getContactEmails());
        user.setRole(role);
        user.setPermissions(role.getPermissions()); // TODO: Set permissions when email is confirmed.
        return user;
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