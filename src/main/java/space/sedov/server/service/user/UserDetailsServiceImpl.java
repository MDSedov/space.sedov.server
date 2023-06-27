package space.sedov.server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import space.sedov.server.configuration.AuthenticationProviderImpl;
import space.sedov.server.entity.Token;
import space.sedov.server.entity.User;
import space.sedov.server.repository.TokenRepository;
import space.sedov.server.repository.UserRepository;
import space.sedov.server.service.email.EmailServiceImpl;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;
import space.sedov.server.service.token.TokenServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenServiceImpl tokenService;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    AuthenticationProviderImpl authenticationProvider;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    public ResponseService getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            return new ResponseService(HttpStatus.OK, MessageService.OK, user);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.OK, MessageService.UNKNOWN_PROBLEM);
        }
    }

    public ResponseService findUserByEmail(UserRequest form) {
        try {
            Optional<User> optional = userRepository.findUserByEmail(form.getEmail());
            if (optional.isEmpty()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.USER_NOT_FOUND, MessageService.USER_NOT_FOUND.toString());
            }
            return new ResponseService(HttpStatus.OK, MessageService.USER_FOUND, MessageService.USER_FOUND.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService signin(UserRequest form) {
        String requestEmail = form.getEmail();
        String requestPassword = form.getPassword();
        UsernamePasswordAuthenticationToken authenticationTokenRequest = new
                UsernamePasswordAuthenticationToken(requestEmail, requestPassword);
        try {
            Authentication authentication = this.authenticationProvider.authenticate(authenticationTokenRequest);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            if ( !user.getEnabled() ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.EMAIL_IS_NOT_CONFIRM, MessageService.EMAIL_IS_NOT_CONFIRM.toString());
            }
            return new ResponseService(HttpStatus.OK, MessageService.SIGNIN_SUCCESS, user);

        } catch (BadCredentialsException e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.SIGNIN_FAILED, MessageService.SIGNIN_FAILED.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService signup(UserRequest form) {
        String requestEmail = form.getEmail();
        String requestPassword = form.getPassword();
        String requestConfirmationPassword = form.getConfirmationPassword();
        try {
            //Проверка совпадения пароля и его подтверждения
            if ( !requestPassword.equals(requestConfirmationPassword) ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.PASSWORDS_MISMATCHED, MessageService.PASSWORDS_MISMATCHED.toString());
            }
            //Проверка есть ли в БД пользователь с таким адресом электронной почты
            Optional<User> optionalUser = userRepository.findUserByEmail(requestEmail);
            if ( optionalUser.isPresent() && optionalUser.get().getEnabled() ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.EMAIL_IS_ALREADY_USE, MessageService.EMAIL_IS_ALREADY_USE.toString());
            }
            //Удаляем пользователя если электронная почта не подтверждена и срок действия токена закончился
            if ( optionalUser.isPresent() && !optionalUser.get().getEnabled() ) {
                Optional<Token> optionalToken = tokenRepository.findByUserId(optionalUser.get().getId());
                if (optionalToken.get().getExpirationDate().compareTo(ZonedDateTime.now()) < 0) {
                    userRepository.delete(optionalUser.get());
                } else {
                    return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.EMAIL_IS_NOT_CONFIRM, MessageService.EMAIL_IS_NOT_CONFIRM.toString());
                }
            }
            //Сохраняем пользователя и токен подтверждения адреса электроной почты
            String encodedRequestPassword = new BCryptPasswordEncoder().encode(requestPassword);
            User user = new User(requestEmail, encodedRequestPassword);
            userRepository.save(user);
            Token token = new Token( user.getId(), user.getEmail(), "Signup",  tokenService.generateToken() );
            tokenRepository.save(token);
            //Отправка письма для подтверждения адреса электронной почты
            String confirmationLink = "http://sedov.space/user/auth/email/confirmation/" + token.getToken();
            emailService.sendEmail(user.getEmail(), "Завершение регистрации", confirmationLink);
            return new ResponseService(HttpStatus.OK, MessageService.SIGNUP_SUCCESS, MessageService.SIGNUP_SUCCESS.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService signout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            new SecurityContextLogoutHandler().logout(
                    httpServletRequest,
                    httpServletResponse,
                    authentication);
            return new ResponseService(HttpStatus.OK, MessageService.OK, user);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.OK, MessageService.UNKNOWN_PROBLEM);
        }
    }

    public ResponseService changePersonalData(UserRequest form) {
        String firstName = form.getFirstName();
        String lastName = form.getLastName();
        String patronymicName = form.getPatronymicName();
        try {
            //Получаем данные текущего пользователя прощедшего аутентикацию
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            //Сохраняем новые персональные данные пользователя
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPatronymicName(patronymicName);
            userRepository.save(user);
            return new ResponseService(HttpStatus.OK, MessageService.PERSONAL_DATA_UPDATED, user);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService changePassword(UserRequest form) {
        String requestPassword = form.getPassword();
        String requestConfirmationPassword = form.getConfirmationPassword();
        //Проверяем совпадает ли адрес электронной почты и его подтверждение в запросе от пользователя
        if ( !requestPassword.equals(requestConfirmationPassword) ) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.PASSWORDS_MISMATCHED, MessageService.PASSWORDS_MISMATCHED.toString());
        }
        try {
            //Получаем данные текущего пользователя прощедшего аутентикацию
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            //Проверяем что новый пароль не совпадает с текущим
            if ( user.getPassword().equals(requestPassword) ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.NEW_PASSWORD_IS_THE_SAME, MessageService.NEW_PASSWORD_IS_THE_SAME.toString());
            }
            //Устанавливаем новый пароль для текущего пользователя
            String encodedRequestPassword = new BCryptPasswordEncoder().encode(requestPassword);
            user.setPassword(encodedRequestPassword);
            userRepository.save(user);
            return new ResponseService(HttpStatus.OK, MessageService.PASSWORD_CHANGED, MessageService.PASSWORD_CHANGED.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService changeEmail(UserRequest form) {
        String requestEmail = form.getEmail();
        try {
            //Получаем данные текущего пользователя прощедшего аутентикацию
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            //Проверяем что новый адрес электронной почты не совпадает с текущим
            if ( user.getEmail().equals(form.getEmail()) ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.NEW_EMAIL_IS_THE_SAME, MessageService.NEW_EMAIL_IS_THE_SAME.toString());
            }
            //Проверяем не используется ли данный адрес электронной почты другим пользователем
            //Если адрес используется другим пользователем, то проверяем подтвердил ли он его
            Optional<User> optionalAnotherUser = userRepository.findUserByEmail(requestEmail);
            if ( optionalAnotherUser.isPresent() && !optionalAnotherUser.get().getEnabled() ) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.EMAIL_IS_ALREADY_USE, MessageService.EMAIL_IS_ALREADY_USE.toString());
            }
            //Создаем токен для подтверждения нового адреса электронной почты
            Token token = new Token( user.getId(), form.getEmail(), "ChangeEmail", tokenService.generateToken() );
            tokenRepository.save(token);
            String confirmationLink = "http://sedov.space/user/confirmation/" + token.getToken();
            //Отправляем письмо
            emailService.sendEmail(form.getEmail(), "Изменение адреса электронной почты", confirmationLink);
            return new ResponseService(HttpStatus.OK, MessageService.EMAIL_SENT_SUCCESSFULLY, user);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService sendConfirmationEmail(UserRequest form) {
        String requestEmail = form.getEmail();
        try {
            Optional<User> optional = userRepository.findUserByEmail(requestEmail);
            if (optional.isEmpty()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.USER_NOT_FOUND, MessageService.USER_NOT_FOUND.toString());
            }
            User user = optional.get();
            Token token = new Token( user.getId(), form.getEmail(), "ConfirmationEmail", tokenService.generateToken() );
            tokenRepository.save(token);
            String confirmationLink = "http://sedov.space/user/confirmation/" + token.getToken();
            //Отправляем письмо
            emailService.sendEmail(form.getEmail(), "Подтверждение адреса электронной почты", confirmationLink);
            return new ResponseService(HttpStatus.OK, MessageService.EMAIL_SENT_SUCCESSFULLY, MessageService.EMAIL_SENT_SUCCESSFULLY.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    public ResponseService confirmationEmail(String requestToken) {
        try {
            Optional<Token> optionalToken = tokenRepository.findByToken(requestToken);
            if (!optionalToken.isPresent()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TOKEN_NOT_FOUND, MessageService.TOKEN_NOT_FOUND.toString());
            }
            Token token = optionalToken.get();
            if (!token.isValid()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TOKEN_INVALID, MessageService.TOKEN_INVALID.toString());
            }
            if (token.getExpirationDate().compareTo(ZonedDateTime.now()) < 0) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TOKEN_EXPIRED, MessageService.TOKEN_EXPIRED.toString());
            }
            User user = userRepository.findUserById(token.getUserId()).get();
            user.setEmail(token.getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            token.setValid(false);
            tokenRepository.save(token);
            return new ResponseService(HttpStatus.OK, MessageService.EMAIL_CONFIRMED, user);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, MessageService.UNKNOWN_PROBLEM.toString());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
