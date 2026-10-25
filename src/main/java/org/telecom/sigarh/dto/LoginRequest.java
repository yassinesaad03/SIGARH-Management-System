package org.telecom.sigarh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Le matricule  est obligatoire")
    private Long matriculeAdmin;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

}
