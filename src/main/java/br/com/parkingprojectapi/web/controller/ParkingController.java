package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.service.ParkingService;
import br.com.parkingprojectapi.web.dto.ParkingInsertDTO;
import br.com.parkingprojectapi.web.dto.ParkingResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.ClientSpaceMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parking-lots")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDTO> checkIn(@RequestBody @Valid ParkingInsertDTO parkingInsertDTO){
        ClientSpace clientSpace = ClientSpaceMapper.toClientSpace(parkingInsertDTO);
        parkingService.checkIn(clientSpace);
        ParkingResponseDTO parkingResponseDTO = ClientSpaceMapper.toDTO(clientSpace);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(clientSpace.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(parkingResponseDTO);
    }
}
