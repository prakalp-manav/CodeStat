package com.ratings.rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ratings.rating.entity.Profiles;

public interface ProfilesRepository extends JpaRepository<Profiles, String> {
    Optional<Profiles> findByUsername(String username);
}
