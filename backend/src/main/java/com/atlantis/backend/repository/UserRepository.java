package com.atlantis.backend.repository;

import com.atlantis.backend.model.UserGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserGuest, Long> {
    Optional<UserGuest> findByPhoneNumber(String phoneNumber);
    Optional<UserGuest> findByRoomNumber(String roomNumber);
}
