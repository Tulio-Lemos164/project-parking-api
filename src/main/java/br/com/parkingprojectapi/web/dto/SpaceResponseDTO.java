package br.com.parkingprojectapi.web.dto;

import br.com.parkingprojectapi.entity.enums.SpaceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SpaceResponseDTO {

    private Long id;
    private String code;
    private SpaceStatus status;
}
