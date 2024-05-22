package br.com.parkingprojectapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Clients_Has_Spaces")
@EntityListeners(AuditingEntityListener.class)
public class ClientSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "receipt_number", unique = true, nullable = false, length = 15)
    private String receipt;

    @Column(name = "license_plate", nullable = false, length = 8)
    private String licensePlate;

    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "exit_date")
    private LocalDateTime exitDate;

    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_space", nullable = false)
    private Space space;


    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSpace that = (ClientSpace) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
