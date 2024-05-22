package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}
