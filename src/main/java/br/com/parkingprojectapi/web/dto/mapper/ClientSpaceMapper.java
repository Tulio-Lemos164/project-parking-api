package br.com.parkingprojectapi.web.dto.mapper;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.web.dto.ParkingInsertDTO;
import br.com.parkingprojectapi.web.dto.ParkingResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSpaceMapper {

    public static ClientSpace toClientSpace(ParkingInsertDTO parkingInsertDTO){
        return new ModelMapper().map(parkingInsertDTO, ClientSpace.class);
    }

    public static ParkingResponseDTO toDTO(ClientSpace clientSpace){
        return new ModelMapper().map(clientSpace, ParkingResponseDTO.class);
    }
}
