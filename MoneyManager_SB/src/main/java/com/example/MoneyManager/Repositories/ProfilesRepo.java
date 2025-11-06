package com.example.MoneyManager.Repositories;

import com.example.MoneyManager.Entities.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilesRepo extends JpaRepository<Profiles,Long>{

    Optional<String> findByEmail(String email);

    Boolean existsByEmail( String email);

    Optional<Profiles> findByActivationToken(String token);
}
