package com.bdzjn.poretti.service;


import com.bdzjn.poretti.model.User;

public interface EmailService {

    /**
     * Sends email to the user using his email.
     *
     * @param user {@link User} for whom email will be sent to
     */
    void sendTo(User user);

}
