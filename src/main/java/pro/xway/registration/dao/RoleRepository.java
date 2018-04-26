package pro.xway.registration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.xway.registration.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String name);
}
