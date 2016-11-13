package com.bdzjn.poretti.repository;

public interface UserRepositoryCustom {
    boolean areUsernameOrEmailTaken(String username, String email);
}
