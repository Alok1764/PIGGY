package com.example.MoneyManager.Controller;

import com.example.MoneyManager.DTO.Request.ProfileCreateDTO;
import com.example.MoneyManager.DTO.Response.ProfileDTO;
import com.example.MoneyManager.Services.ProfilesServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProfilesController {

    private final ProfilesServices profilesServices;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@Valid @RequestBody ProfileCreateDTO profileCreateDTO){
        log.info("POST /api/v1.0/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(profilesServices.registerProfile(profileCreateDTO));

    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token){
        log.info("GET /api/v1.0/activate?token={}",token);
        boolean isActivated=profilesServices.activateProfile(token);
        if(isActivated) return ResponseEntity.ok("Profile activated successfully");
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or already used");
    }

}
