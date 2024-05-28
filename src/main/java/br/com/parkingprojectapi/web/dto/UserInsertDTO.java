package br.com.parkingprojectapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserInsertDTO {

    @NotBlank(message = "The e-mail must be not blank")
    @Email(message = "This e-mail format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank(message = "The password must be not blank")
    @Size(min = 6, max = 6, message = "The password must have 6 digits")
    private String password;
}
