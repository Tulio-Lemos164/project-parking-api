package br.com.parkingprojectapi.web.controller;


import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.jwt.JwtUserDetails;
import br.com.parkingprojectapi.service.ClientService;
import br.com.parkingprojectapi.service.UserService;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.ClientInsertDTO;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;


    @Operation(summary = "Create a new client on the database", description = "Creates a new client linked to a User with CLIENT role",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Username already in use",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid input data, resources not created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not available for ADMIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDTO> insert(@RequestBody @Valid ClientInsertDTO obj, @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(obj);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.insert(client);
        return ResponseEntity.status(201).body(ClientMapper.toDTO(client));
    }
}
