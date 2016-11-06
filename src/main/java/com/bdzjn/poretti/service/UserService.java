package com.bdzjn.poretti.service;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByEmailAndPassword(String email, String password);

}
