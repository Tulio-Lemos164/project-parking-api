package br.com.parkingprojectapi.repository;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    Role findRoleByUsername(String username);
}
