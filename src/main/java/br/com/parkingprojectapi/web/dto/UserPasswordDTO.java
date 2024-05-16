package br.com.parkingprojectapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPasswordDTO {

    @NotBlank(message = "The password must be not blank")
    @Size(min = 6, max = 6, message = "The password must have 6 digits")
    private String currentPassword;

    @NotBlank(message = "The password must be not blank")
    @Size(min = 6, max = 6, message = "The password must have 6 digits")
    private String newPassword;

    @NotBlank(message = "The password must be not blank")
    @Size(min = 6, max = 6, message = "The password must have 6 digits")
    private String confirmPassword;
}
