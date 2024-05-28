package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.entity.enums.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByCode(String code);

    Optional<Space> findFirstByStatus(SpaceStatus spaceStatus);
}
