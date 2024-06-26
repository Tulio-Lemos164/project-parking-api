package br.com.parkingprojectapi.web.controller.exceptions;

import br.com.parkingprojectapi.service.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DifferentPasswordsException.class)
    public ResponseEntity<StandardError> DifferentPasswords(DifferentPasswordsException e, HttpServletRequest request){
        String error = "Different passwords";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult result){
        String error = "Method argument not valid";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = new StandardError(Instant.now(), status.value(), error, "Invalid field(s)", request.getRequestURI(), result);
        log.error("API Error - " + e);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<StandardError> usernameUniqueViolation(UsernameUniqueViolationException e, HttpServletRequest request){
        String error = "Username unique violation";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("API Error - " + e);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(FailedAuthenticationException.class)
    public ResponseEntity<StandardError> failedAuthenticationException(FailedAuthenticationException e, HttpServletRequest request){
        String error = "Login authentication error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> accessDenied(AccessDeniedException e, HttpServletRequest request){
        String error = "Access Denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CpfUniqueViolationException.class)
    public ResponseEntity<StandardError> cpfUniqueViolation(CpfUniqueViolationException e, HttpServletRequest request){
        String error = "CPF unique violation";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("API Error - " + e);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CodeUniqueViolationException.class)
    public ResponseEntity<StandardError> codeUniqueViolation(CodeUniqueViolationException e, HttpServletRequest request){
        String error = "Code unique violation";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("API Error - " + e);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> internalServerErrorException(Exception e, HttpServletRequest request){
        String error = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        log.error("Internal server error - " + e.getCause());
        return ResponseEntity.status(status).body(err);
    }
}
