package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> findById(long id) {
        return null;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return null;
    }
}
