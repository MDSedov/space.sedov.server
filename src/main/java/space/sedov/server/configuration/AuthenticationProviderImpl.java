package space.sedov.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import space.sedov.server.entity.User;
import space.sedov.server.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Ищем пользователя с указанным адресом электронной почты
        String requestEmail = authentication.getName();
        Optional<User> optional = userRepository.findUserByEmail(requestEmail);
        if (optional.isEmpty()) {
            throw new BadCredentialsException("Пользователь с таким адресом электронной почты не найден");
        }

        //Сравниваем пароли из запроса и пользователя из БД
        User user = optional.get();
        String requestPassword = authentication.getCredentials().toString();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if ( !bCryptPasswordEncoder.matches(requestPassword, user.getPassword()) ) {
            throw new BadCredentialsException("Пароль не подходит к учетной записи пользователя");
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
