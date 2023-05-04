package space.sedov.server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.User;
import space.sedov.server.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findUserByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optional.get();
    }
}
