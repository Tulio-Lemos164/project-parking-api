package br.com.parkingprojectapi.web.controller;


import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.jwt.JwtUserDetails;
import br.com.parkingprojectapi.repository.projection.ClientProjection;
import br.com.parkingprojectapi.service.ClientService;
import br.com.parkingprojectapi.service.UserService;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.ClientInsertDTO;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import br.com.parkingprojectapi.web.dto.PageableDTO;
import br.com.parkingprojectapi.web.dto.mapper.ClientMapper;
import br.com.parkingprojectapi.web.dto.mapper.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clients", description = "Contains all the operations to inset, retrieve and update a client")
@RestController
@RequestMapping(value = "api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;


    @Operation(summary = "Create a new client on the database", description = "Creates a new client linked to a User with CLIENT role",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Username already in use",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid input data, resources not created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not available for ADMIN role",
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

    @Operation(summary = "Find a client by their id", description = "Access restricted to ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not available for CLIENT role",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id){
        Client client = clientService.findById(id);
        return ResponseEntity.ok().body(ClientMapper.toDTO(client));
    }

    @Operation(summary = "Return all clients", description = "Access fully restricted to ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Represents the page being returned"),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Represents the total quantity of elements on a page"),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            content = @Content(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Represents the ordering of results. Multiple sort parameters are supported"),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "user without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> findAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"name"}) Pageable pageable){
        Page<ClientProjection> clients = clientService.findAll(pageable);
        return ResponseEntity.ok().body(PageableMapper.toDto(clients));
    }

    @Operation(summary = "Return the details about the logged Client", description = "Access fully restricted to CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client data retrieved successfully",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "user without permission to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDTO> findDetails(@AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = clientService.findDetailsFromUserId(userDetails.getId());
        return ResponseEntity.ok().body(ClientMapper.toDTO(client));
    }
}
