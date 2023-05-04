package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.sedov.server.entity.User;
import space.sedov.server.service.user.UserDetailsServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/api/user")
    public String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        System.out.println(user.toString());
        return user.toString();
    }
}
