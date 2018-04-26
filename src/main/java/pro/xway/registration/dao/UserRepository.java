package pro.xway.registration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.xway.registration.model.MyUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String email);
}
