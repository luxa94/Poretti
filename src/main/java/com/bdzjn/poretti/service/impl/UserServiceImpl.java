package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.controller.exception.AuthenticationException;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.Role;
import com.bdzjn.poretti.repository.CompanyRepository;
import com.bdzjn.poretti.repository.UserRepository;
import com.bdzjn.poretti.service.EmailService;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(EmailService emailService, UserRepository userRepository,
                           CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setImageUrl(userDTO.getImageUrl());
        user.setPhoneNumbers(userDTO.getPhoneNumbers());
        user.setContactEmails(userDTO.getContactEmails());

        return userRepository.save(user);
    }

    @Override
    public boolean areUsernameOrEmailTaken(String username, String email) {
        return userRepository.areUsernameOrEmailTaken(username, email);
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        final User user = createUser(registerDTO);

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
        return createUserWithRole(registerDTO, Role.USER);
    }

    @Override
    public User createVerifier(RegisterDTO registerDTO) {
        return createUserWithRole(registerDTO, Role.VERIFIER);
    }

    @Override
    public User createAdmin(RegisterDTO registerDTO) {
        return createUserWithRole(registerDTO, Role.SYSTEM_ADMIN);
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
        user.setPermissions(role.getPermissions());

        userRepository.save(user);
        emailService.sendTo(user);

        return user;
    }

    @Override
    public User login(LoginDTO loginDTO) {
        final User user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(AuthenticationException::new);

        if (!user.isRegistrationConfirmed()) {
            throw new AuthenticationException("Please verify your account with the link in the email.");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException();
        }

        return user;
    }

    @Override
    public void verify(long id) {
        final User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        if (user.isRegistrationConfirmed()) {
            throw new ForbiddenException("User is already verified.");
        }

        user.setRegistrationConfirmed(true);
        userRepository.save(user);
    }
}
