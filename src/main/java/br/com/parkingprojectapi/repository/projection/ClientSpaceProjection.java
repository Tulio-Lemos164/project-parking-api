package br.com.parkingprojectapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientSpaceProjection {

    String getLicensePlate();
    String getBrand();
    String getModel();
    String getColor();
    String getClientCpf();
    String getReceipt();
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime getEntryDate();
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime getExitDate();
    String getSpaceCode();
    BigDecimal getValue();
    BigDecimal getDiscount();
}
