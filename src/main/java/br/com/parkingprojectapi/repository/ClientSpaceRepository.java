package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.ClientSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientSpaceRepository extends JpaRepository<ClientSpace, Long> {
}
