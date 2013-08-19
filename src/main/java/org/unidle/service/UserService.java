package org.unidle.service;

import org.unidle.domain.User;

public interface UserService {

    User createUser(final String email, final String firstName,
                    final String lastName);

    boolean exists(final String email);

}
