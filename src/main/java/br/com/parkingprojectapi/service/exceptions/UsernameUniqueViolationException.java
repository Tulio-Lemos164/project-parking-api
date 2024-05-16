package br.com.parkingprojectapi.service.exceptions;

public class UsernameUniqueViolationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameUniqueViolationException(String msg) {
        super(msg);
    }
}
