package com.atlantis.backend.config;

import com.atlantis.backend.model.UserGuest;
import com.atlantis.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                UserGuest defaultGuest = new UserGuest(
                        "Mr.",
                        "Aman Singh",
                        "501234567",
                        "+971",
                        "305",
                        "Deluxe Ocean View",
                        "Jul 26, 2026",
                        "Jul 30, 2026",
                        "app/src/main/res/drawable/guest_avatar.jpg"
                );
                userRepository.save(defaultGuest);
                System.out.println(">>> Initialized default guest: Mr. Aman Singh (Room 305)");
            }
        };
    }
}
