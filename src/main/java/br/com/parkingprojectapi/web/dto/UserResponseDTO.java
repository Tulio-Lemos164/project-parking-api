package br.com.parkingprojectapi.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;
}
