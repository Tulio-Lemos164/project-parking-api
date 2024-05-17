package br.com.parkingprojectapi.web.controller.exceptions;

public class FailedAuthenticationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public FailedAuthenticationException(String msg) {
        super(msg);
    }
}