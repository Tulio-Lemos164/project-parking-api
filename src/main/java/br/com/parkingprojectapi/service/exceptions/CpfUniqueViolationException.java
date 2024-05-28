package br.com.parkingprojectapi.service.exceptions;

public class CpfUniqueViolationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CpfUniqueViolationException(String msg) {
        super(msg);
    }
}
