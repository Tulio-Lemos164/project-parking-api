package br.com.parkingprojectapi.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserInsertDTO {

    private String username;
    private String password;
}
