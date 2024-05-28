package br.com.parkingprojectapi.web.controller.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter @NoArgsConstructor
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public StandardError(Instant timestamp, Integer status, String error, String message, String path, BindingResult result) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        addErrors(result);
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setError(String error) {
        this.error = error;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setPath(String path) {
        this.path = path;
    }


    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
