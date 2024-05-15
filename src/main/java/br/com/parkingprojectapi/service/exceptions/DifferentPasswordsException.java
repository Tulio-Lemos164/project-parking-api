package br.com.parkingprojectapi.service.exceptions;

public class DifferentPasswordsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DifferentPasswordsException(String msg) {
        super(msg);
    }
}
