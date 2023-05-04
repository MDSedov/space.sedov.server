package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.User;
import space.sedov.server.form.SigninForm;
import space.sedov.server.form.SignupForm;
import space.sedov.server.service.authentication.AuthenticationService;
import space.sedov.server.service.authentication.ResponseService;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/api/auth/signin")
    @ResponseBody
    public ResponseService signin(@RequestBody SigninForm form) {
        return authenticationService.signin(form);
    }

    @GetMapping("/api/auth/signout")
    @ResponseBody
    public ResponseService signout() {
        return authenticationService.signout();
    }

    @PostMapping("/api/auth/signup")
    @ResponseBody
    public ResponseService signup(@RequestBody SignupForm form) {
        return authenticationService.signup(form);
    }
}