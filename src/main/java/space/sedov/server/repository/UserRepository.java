package space.sedov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(int userId);
}
