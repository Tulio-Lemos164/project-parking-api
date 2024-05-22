package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.service.ParkingService;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import br.com.parkingprojectapi.web.dto.ParkingInsertDTO;
import br.com.parkingprojectapi.web.dto.ParkingResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.ClientSpaceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @Operation(summary = "check in operation", description = "Resource to register a vehicle in the parking lot",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Check in done successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL to access the created resource"),
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Either the client's is not registered or there were no empty spaces",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid input data, resources not created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not available for CLIENT role",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
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
