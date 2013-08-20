package org.unidle.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.unidle.domain.User;

import java.util.UUID;

@Service
public class AuditorAwareImpl implements AuditorAware<User> {

    @Autowired
    private final UserRepository userRepository;

    public AuditorAwareImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            return null;
        }

        final String name = authentication.getName();
        return name == null
               ? null
               : userRepository.findOne(UUID.fromString(name));
    }

}
