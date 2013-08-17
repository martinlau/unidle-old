package org.unidle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unidle.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
