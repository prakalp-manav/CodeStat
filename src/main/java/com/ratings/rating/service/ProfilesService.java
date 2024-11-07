package com.ratings.rating.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratings.rating.entity.Profiles;
import com.ratings.rating.repository.ProfilesRepository;

@Service
public class ProfilesService {
    @Autowired
    private ProfilesRepository ProfilesRepository;

    public Profiles getUserByUsername(String username) {
        Optional<Profiles> profiles = ProfilesRepository.findByUsername(username);
        return profiles.orElse(null);
    }
}
