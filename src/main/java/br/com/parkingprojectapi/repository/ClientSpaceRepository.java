package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.ClientSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientSpaceRepository extends JpaRepository<ClientSpace, Long> {
    Optional<ClientSpace> findByReceiptAndExitDateIsNull(String receipt);
}
