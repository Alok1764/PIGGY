package com.example.MoneyManager.Services;

import com.example.MoneyManager.DTO.Request.ProfileCreateDTO;
import com.example.MoneyManager.DTO.Response.ProfileDTO;
import com.example.MoneyManager.Entities.Profiles;
import com.example.MoneyManager.Repositories.ProfilesRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfilesServices {

    private final ProfilesRepo profilesRepo;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public ProfileDTO registerProfile(ProfileCreateDTO profileCreateDTO) throws RuntimeException {
        boolean isEmailExist=profilesRepo.existsByEmail(profileCreateDTO.getEmail());
        if (isEmailExist) {
            throw new RuntimeException("You are already registered. Just login.");
        }
        Profiles profiles=Profiles.builder()
                .fullName(profileCreateDTO.getFullName())
                .email(profileCreateDTO.getEmail())
                .password(profileCreateDTO.getPassword())
                .activationToken(UUID.randomUUID().toString())
                .build();
        Profiles savedProfile=profilesRepo.save(profiles);

        //send email after register
        String activationLink="http://localhost:8080/api/v1.0/activate?token="+savedProfile.getActivationToken();
        String subject="Activate your Piggy account";
        String body="Click to the given link to activate your account: "+activationLink;
        emailService.sendEmail(savedProfile.getEmail(),subject ,body);
            return modelMapper.map(savedProfile,ProfileDTO.class);

    }

    public boolean activateProfile(String token) {
        return profilesRepo.findByActivationToken(token)
                .map(profiles -> {
                    profiles.setIsActive(true);
                    profilesRepo.save(profiles);
                    return true;
                })
                .orElse(false);
    }
}
