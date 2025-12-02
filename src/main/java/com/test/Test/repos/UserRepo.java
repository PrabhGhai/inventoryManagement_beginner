package com.test.Test.repos;

import com.test.Test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User>findByEmail(String email);
}
