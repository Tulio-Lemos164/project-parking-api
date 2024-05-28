package br.com.parkingprojectapi.service.exceptions;

public class CodeUniqueViolationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CodeUniqueViolationException(String msg) {
        super(msg);
    }
}
