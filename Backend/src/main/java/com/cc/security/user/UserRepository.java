package com.cc.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailOrUsername(String email, String username);

    User findByEmail(String email);
}
