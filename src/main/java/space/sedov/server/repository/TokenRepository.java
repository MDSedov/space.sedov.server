package space.sedov.server.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.Token;
import space.sedov.server.entity.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findById(int id);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUserId(int userId);
}
