package br.com.parkingprojectapi.web.dto.mapper;

import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.web.dto.ClientInsertDTO;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientInsertDTO clientCreateDTO){
        return new ModelMapper().map(clientCreateDTO, Client.class);
    }

    public static ClientResponseDTO toDTO(Client client){
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }
}
