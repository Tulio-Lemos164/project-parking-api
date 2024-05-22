package br.com.parkingprojectapi.web.dto.mapper;

import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.web.dto.SpaceInsertDTO;
import br.com.parkingprojectapi.web.dto.SpaceResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceMapper {

    public static Space toSpace(SpaceInsertDTO spaceInsertDTO){
        return new ModelMapper().map(spaceInsertDTO, Space.class);
    }

    public static SpaceResponseDTO spaceResponseDTO(Space space){
        return new ModelMapper().map(space, SpaceResponseDTO.class);
    }
}
