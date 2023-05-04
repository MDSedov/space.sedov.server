package space.sedov.server.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import space.sedov.server.configuration.AuthenticationProvider;
import space.sedov.server.entity.User;
import space.sedov.server.form.SigninForm;
import space.sedov.server.form.SignupForm;
import space.sedov.server.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    public ResponseService signin(SigninForm form) {
        UsernamePasswordAuthenticationToken authenticationTokenRequest = new
                UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
        try {
            Authentication authentication = this.authenticationProvider.authenticate(authenticationTokenRequest);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            System.out.println("Logged in user: " + user);
            return new ResponseService(HttpStatus.OK, MessageResponseService.OK, user.getUsername());

        } catch (BadCredentialsException e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageResponseService.NO_USER_WITH_USERNAME);
        }
    }

    public ResponseService signup(SignupForm form) {

        ResponseService response = new ResponseService();

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            response.setResponseMessage(MessageResponseService.NEW_PASSWORD_MISMATCHED);
            response.setResponseCode(HttpStatus.BAD_REQUEST);
            return response;
        }

        if (userRepository.findUserByUsername(form.getUsername()).isPresent()) {
            response.setResponseMessage(MessageResponseService.USER_USERNAME_ALREADY_EXIST);
            response.setResponseCode(HttpStatus.BAD_REQUEST);
            return response;
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());

        try {
            userRepository.save(user);
            response.setSuccessResponse();
            response.setResponseObject(user.getUsername());
        } catch (UnexpectedRollbackException e) {
            e.printStackTrace();
            response.setInternalServerErrorResponse();
        } catch (Exception e) {
            response.setInternalServerErrorResponse();
        }
        return response;
    }

    public ResponseService signout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            new SecurityContextLogoutHandler().logout(
                    httpServletRequest,
                    httpServletResponse,
                    authentication);
            return new ResponseService(HttpStatus.OK, MessageResponseService.OK, user.getUsername());
        }
        return new ResponseService(HttpStatus.OK, MessageResponseService.ERROR);
    }
}
