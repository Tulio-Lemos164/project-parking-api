package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.service.SpaceService;
import br.com.parkingprojectapi.web.dto.SpaceInsertDTO;
import br.com.parkingprojectapi.web.dto.SpaceResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.SpaceMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

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

    @GetMapping(value = "/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpaceResponseDTO> findByCode(@PathVariable String code){
        Space space = spaceService.findByCode(code);
        return ResponseEntity.ok().body(SpaceMapper.toDTO(space));
    }
}
