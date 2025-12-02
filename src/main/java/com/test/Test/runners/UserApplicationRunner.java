package com.test.Test.runners;
import com.test.Test.models.User;
import com.test.Test.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("----- Running UserApplicationRunner -----");

        createUser("user1@gmail.com", "password123", "USER");
        createUser("admin@gmail.com", "admin123", "ADMIN");
        createUser("test@gmail.com", "test123", "USER");

        System.out.println("----- UserApplicationRunner Finished -----");
    }

    private void createUser(String email, String rawPassword, String role) {
        if (userRepo.findByEmail(email).isEmpty()) {

            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);

            userRepo.save(user);

            System.out.println("Created user: " + email);
        } else {
            System.out.println("User already exists: " + email);
        }
    }
}

