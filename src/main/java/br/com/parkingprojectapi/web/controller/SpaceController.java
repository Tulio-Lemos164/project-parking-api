package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.service.SpaceService;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.SpaceInsertDTO;
import br.com.parkingprojectapi.web.dto.SpaceResponseDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.SpaceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Parking Spaces", description = "Contains the operations related to the parking spaces")
@RestController
@RequestMapping(value = "/api/v1/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Operation(summary = "Create a new space on the database", description = "Access restricted to ADMIN",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Space created successfully")),
                    @ApiResponse(responseCode = "409", description = "Code already in use",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid input data, resources not created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> insert(@RequestBody @Valid SpaceInsertDTO obj){
        Space space = SpaceMapper.toSpace(obj);
        spaceService.insert(space);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(space.getCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find a space", description = "Access restricted to ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            })
    @GetMapping(value = "/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpaceResponseDTO> findByCode(@PathVariable String code){
        Space space = spaceService.findByCode(code);
        return ResponseEntity.ok().body(SpaceMapper.toDTO(space));
    }
}
