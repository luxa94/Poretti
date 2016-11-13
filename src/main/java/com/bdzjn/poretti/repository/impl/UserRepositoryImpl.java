package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.QUser;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class UserRepositoryImpl extends QueryDslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public boolean areUsernameOrEmailTaken(String username, String email) {
        final QUser user = QUser.user;

        return from(user)
                .select(user)
                .where(user.username.eq(username).or(user.email.eq(email)))
                .fetchCount() > 0;
    }
}
