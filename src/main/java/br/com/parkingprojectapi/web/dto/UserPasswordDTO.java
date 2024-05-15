package br.com.parkingprojectapi.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
