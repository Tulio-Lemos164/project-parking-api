package br.com.parkingprojectapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientCreateDTO {

    @NotBlank(message = "The name must be not blank")
    @Size(min = 3, max = 200)
    private String name;

    @NotBlank(message = "The cpf must be not blank")
    @Size(min = 11, max = 11, message = "The cpf must have 11 digits")
    @CPF
    private String cpf;
}
