package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.jwt.JwtToken;
import br.com.parkingprojectapi.jwt.JwtUserDetailsService;
import br.com.parkingprojectapi.web.controller.exceptions.FailedAuthenticationException;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.UserLoginDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Authentication", description = "resources to authenticate a user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;


    @Operation(summary = "Authenticate a User for its username and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Authenticated successfully and returned a bearer token",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JwtToken.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            })
    @PostMapping("/auth")
    public ResponseEntity<JwtToken> authenticate(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        log.info("Login authentication process " + userLoginDTO.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(userLoginDTO.getUsername());
            return ResponseEntity.ok().body(token);
        }
        catch (AuthenticationException e){
            log.warn("Bad credentials from " + userLoginDTO.getUsername());
            throw new FailedAuthenticationException("Bad credentials from " + userLoginDTO.getUsername());
        }
    }
}
