package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.repository.projection.ClientSpaceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientSpaceRepository extends JpaRepository<ClientSpace, Long> {
    Optional<ClientSpace> findByReceiptAndExitDateIsNull(String receipt);

    long countByClientCpfAndExitDateIsNotNull(String cpf);

    Page<ClientSpaceProjection> findAllByClientCpf(String cpf, Pageable pageable);
}
