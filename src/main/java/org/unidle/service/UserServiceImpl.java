package org.unidle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(final String email, final String firstName, final String lastName) {
        final User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(final String email) {
        return userRepository.findOne(email) != null;
    }

}
