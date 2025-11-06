package com.example.MoneyManager.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCreateDTO {
    @NotBlank(message = "fullName is required")
    @Size(max=100)
    private String fullName;

    @NotBlank(message = "email is required")
    @Size(max=100)
    private String email;

    @NotBlank(message = "password is required")
    @Size(max=20,min =8,message = "Password length should be min 8 characters")
    private String password;


}
