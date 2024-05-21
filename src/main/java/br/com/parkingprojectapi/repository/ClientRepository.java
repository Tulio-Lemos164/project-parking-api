package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
