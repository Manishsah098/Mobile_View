package com.atlantis.backend.controller;

import com.atlantis.backend.model.UserGuest;
import com.atlantis.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserGuest> registerGuest(@RequestBody UserGuest guest) {
        if (guest.getAvatarUrl() == null || guest.getAvatarUrl().isEmpty()) {
            guest.setAvatarUrl("app/src/main/res/drawable/guest_avatar.jpg");
        }
        if (guest.getTitle() == null || guest.getTitle().isEmpty()) {
            guest.setTitle("Mr.");
        }
        if (guest.getNotificationCount() == null) {
            guest.setNotificationCount(3);
        }
        UserGuest saved = userRepository.save(guest);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginGuest(@RequestBody UserGuest loginReq) {
        Optional<UserGuest> found = Optional.empty();
        if (loginReq.getPhoneNumber() != null && !loginReq.getPhoneNumber().isEmpty()) {
            found = userRepository.findByPhoneNumber(loginReq.getPhoneNumber());
        } else if (loginReq.getRoomNumber() != null && !loginReq.getRoomNumber().isEmpty()) {
            found = userRepository.findByRoomNumber(loginReq.getRoomNumber());
        }

        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            // Return first user or default fallback
            List<UserGuest> all = userRepository.findAll();
            if (!all.isEmpty()) {
                return ResponseEntity.ok(all.get(0));
            }
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserGuest> getUserProfile(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    List<UserGuest> all = userRepository.findAll();
                    if (!all.isEmpty()) return ResponseEntity.ok(all.get(0));
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/guests")
    public ResponseEntity<List<UserGuest>> getAllGuests() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
