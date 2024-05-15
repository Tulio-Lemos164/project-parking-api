package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
