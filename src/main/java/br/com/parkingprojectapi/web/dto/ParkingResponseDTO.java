package br.com.parkingprojectapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingResponseDTO {
    private String licensePlate;
    private String brand;
    private String model;
    private String color;
    private String clientCpf;
    private String receipt;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private String spaceCode;
    private BigDecimal value;
    private BigDecimal discount;
}
