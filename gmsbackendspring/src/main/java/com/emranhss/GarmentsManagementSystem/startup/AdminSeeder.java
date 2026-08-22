package com.emranhss.GarmentsManagementSystem.startup;

import com.emranhss.GarmentsManagementSystem.entity.User;
import com.emranhss.GarmentsManagementSystem.enums.Role;
import com.emranhss.GarmentsManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        User admin = new User();

        admin.setName("System Administrator");
        admin.setEmail("admin@gmail.com");
        admin.setPhone("01700000000");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        userRepository.save(admin);

        System.out.println("======================================");
        System.out.println(" Default ADMIN account created");
        System.out.println(" Email    : admin@gmail.com");
        System.out.println(" Password : admin123");
        System.out.println("======================================");

    }

}
