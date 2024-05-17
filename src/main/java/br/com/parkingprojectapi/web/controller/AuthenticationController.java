package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.jwt.JwtToken;
import br.com.parkingprojectapi.jwt.JwtUserDetailsService;
import br.com.parkingprojectapi.web.controller.exceptions.FailedAuthenticationException;
import br.com.parkingprojectapi.web.dto.UserLoginDTO;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

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
