package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.service.UserService;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.UserInsertDTO;
import br.com.parkingprojectapi.web.dto.UserPasswordDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contains all the operations to inset, retrieve and update a user")
@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user on the database",
                responses = {
                @ApiResponse(responseCode = "201", description = "User created successfully",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "409", description = "Username already in use",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                @ApiResponse(responseCode = "422", description = "Invalid input data, resources not created",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserInsertDTO obj){
        User user = userService.insert(UserMapper.toUser(obj));
        return ResponseEntity.status(201).body(UserMapper.toDTO(user));
    }

    @Operation(summary = "Find a user by their id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDTO(user));
    }


    @Operation(summary = "Update the password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "400", description = "The passwords are different",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Incorrect or invalid camps values",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO obj){
        User user = userService.updatePassword(id, obj.getCurrentPassword(), obj.getNewPassword(), obj.getConfirmPassword());
        return ResponseEntity.ok().body(UserMapper.toDTO(user));
    }

    @Operation(summary = "Return all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
            })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toListDTO(users));
    }
}
