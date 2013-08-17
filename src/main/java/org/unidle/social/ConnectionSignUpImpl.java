package org.unidle.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

@Component
public class ConnectionSignUpImpl implements ConnectionSignUp {

    private final UserRepository userRepository;

    @Autowired
    public ConnectionSignUpImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String execute(final Connection<?> connection) {
        final UserProfile userProfile = connection.fetchUserProfile();

        final User user = new User();

        user.setEmail(userProfile.getEmail());
        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());
        user.setName(userProfile.getName());
        user.setUsername(userProfile.getUsername());

        userRepository.save(user);

        return String.valueOf(user.getId());
    }

}
